/*
 * Android Utilities
 * Copyright (C) 2010-2011  Lorenzo Villani
 *
 * This library is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, version 3 of the License.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package co.bitcode.android.os;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import android.os.Handler;
import android.util.Log;

/**
 * A refined and improved version of Android's {@link android.os.AsyncTask}.
 * <p>
 * Just like {@link android.os.AsyncTask} it makes it possible to run a computation in background and publish the
 * results on the UI thread when done.
 * </p>
 * 
 * @author Lorenzo Villani
 * @param <Params> The type of the parameters passed to this computation.
 * @param <Result> The type of the result returned by this computation.
 * @see android.os.AsyncTask
 * @see Callable
 * @since 0.0.1
 */
public abstract class AsyncTask < Params, Result >
{
        // Constants
        private static final String TAG = "AsyncTask";
        private static final int TUNABLE_MAX_POOL_SIZE = 5;

        // Executor
        private static final Executor EXECUTOR = Executors.newFixedThreadPool ( TUNABLE_MAX_POOL_SIZE );

        // Other
        private Handler mHandler;
        private Params[] mParameters;

        /**
         * Wraps AsyncTask's Callable to support all lifecycle enhancements.
         * 
         * @author Lorenzo Villani
         * @param <Result>
         */
        private static class Task < Params, Result > implements Callable < Void >
        {
                private Handler mHandler;
                private Params[] mParameters;
                private AsyncTask < Params, Result > mParent;

                /**
                 * Constructor.
                 * 
                 * @param parent
                 * @param handler
                 * @param parameters
                 */
                public Task ( AsyncTask < Params, Result > parent, Handler handler, Params ... parameters )
                {
                        mHandler = handler;

                        mParent = parent;

                        mParameters = parameters;
                }

                /**
                 * 
                 */
                @Override
                public Void call () throws Exception
                {
                        try
                        {
                                final Result result;

                                // 1 - Start
                                runOnUiThreadAndWait ( new Runnable ()
                                {
                                        @Override
                                        public void run ()
                                        {
                                                mParent.onStart ();
                                        }
                                } );

                                // 2a - Compute
                                result = mParent.doInBackground ( mParameters );

                                // 3 - Finish
                                runOnUiThreadAndWait ( new Runnable ()
                                {
                                        @Override
                                        public void run ()
                                        {
                                                mParent.onFinish ( result );
                                        }
                                } );
                        }
                        catch ( final Exception ex )
                        {
                                // 2b - Catch error
                                runOnUiThreadAndWait ( new Runnable ()
                                {
                                        @Override
                                        public void run ()
                                        {
                                                mParent.onCatchError ( ex );
                                        }
                                } );
                        }
                        finally
                        {
                                // 4 - Finalize
                                runOnUiThreadAndWait ( new Runnable ()
                                {
                                        @Override
                                        public void run ()
                                        {
                                                mParent.onFinalize ();
                                        }
                                } );
                        }

                        return null;
                }

                /**
                 * Runs the computation inside the UI thread and wait for its execution.
                 * 
                 * @param callable
                 * @throws Throwable
                 */
                private void runOnUiThreadAndWait ( final Runnable runnable ) throws Exception
                {
                        final CountDownLatch countDownLatch;
                        final Exception[] throwable = new Exception[1];

                        countDownLatch = new CountDownLatch ( 1 );

                        mHandler.post ( new Runnable ()
                        {
                                @Override
                                public void run ()
                                {
                                        try
                                        {
                                                runnable.run ();
                                        }
                                        catch ( Exception ex )
                                        {
                                                throwable[0] = ex;
                                        }
                                        finally
                                        {
                                                countDownLatch.countDown ();
                                        }
                                }
                        } );

                        countDownLatch.await ();

                        if ( throwable[0] != null )
                        {
                                throw throwable[0];
                        }
                }
        }

        /**
         * Constructor.
         */
        public AsyncTask ()
        {
                mHandler = new Handler ();
        }

        /**
         * @param args
         */
        public void execute ( Params ... params )
        {
                EXECUTOR.execute ( getFutureTask ( params ) );
        }

        /**
         * @param params
         * @return A FutureTask
         */
        public final FutureTask < Void > getFutureTask ( Params ... params )
        {
                return new FutureTask < Void > ( new Task < Params, Result > ( this, mHandler, params ) );
        }

        /**
         * @return Parameters passed to the computation step.
         */
        protected Params[] getParameters ()
        {
                return mParameters;
        }

        /**
         * This callback method is invoked when it's time to perform the background computation.
         * <p>
         * This callback method is always invoked in a separate thread.
         * </p>
         * 
         * @param params Computation parameters.
         * @return
         */
        protected abstract Result doInBackground ( Params ... params ) throws Exception;

        /**
         * Called when an error occurs during the asynchronous computation.
         * <p>
         * This callback method is always invoked in the UI thread.
         * </p>
         * <p>
         * The default implementation prints the error on the LogCat, then re-throws the exception wrapped in a
         * {@link RuntimeException}.
         * </p>
         * 
         * @param t
         * @since 0.0.1
         */
        protected void onCatchError ( Throwable t )
        {
                Log.e ( TAG, t.getMessage (), t );

                throw new RuntimeException ( t );
        }

        /**
         * This callback method is always invoked at the end of the computation, whether it completed successfully or
         * not. It should be used to perform final cleanup and tear-down procedures.
         * <p>
         * This callback method is always invoked in the UI thread.
         * </p>
         * <p>
         * The default implementation of this method does nothing.
         * </p>
         * 
         * @since 0.0.1
         */
        protected void onFinalize ()
        {
        }

        /**
         * This callback method is invoked when the task completed successfully.
         * <p>
         * This callback method is always invoked in the UI thread.
         * </p>
         * <p>
         * The default implementation of this method does nothing.
         * </p>
         * 
         * @param result
         * @since 0.0.1
         */
        protected void onFinish ( Result result )
        {
        }

        /**
         * This callback method is invoked when the computation is about to start.
         * <p>
         * This callback method is always invoked in the UI thread.
         * </p>
         * <p>
         * The default implementation of this method does nothing.
         * </p>
         * 
         * @since 0.0.1
         */
        protected void onStart ()
        {
        }
}
