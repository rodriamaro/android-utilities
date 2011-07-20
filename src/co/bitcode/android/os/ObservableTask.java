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

import android.os.AsyncTask;

/**
 * An observable {@link AsyncTask}. Allows subscribers to be notified when the task is about to
 * execute, when it has done running or when an error occurred.
 * 
 * @since 0.0.1
 * @author Lorenzo Villani
 */
public abstract class ObservableTask<Params, Progress, Result> extends
        AsyncTask<Params, Progress, Result> {
    private OnTaskError mOnTaskError;
    private OnTaskFinished<Result> mOnTaskFinished;
    private OnTaskStart mOnTaskStart;

    private Throwable mThrowable;

    //
    // Getters and Setters
    //
    public OnTaskError getOnTaskError() {
        return mOnTaskError;
    }

    public OnTaskFinished<Result> getOnTaskFinished() {
        return mOnTaskFinished;
    }

    public OnTaskStart getOnTaskStart() {
        return mOnTaskStart;
    }

    public void setOnTaskError(OnTaskError onTaskError) {
        mOnTaskError = onTaskError;
    }

    public void setOnTaskFinished(OnTaskFinished<Result> onTaskFinished) {
        mOnTaskFinished = onTaskFinished;
    }

    public void setOnTaskStart(OnTaskStart onTaskStart) {
        mOnTaskStart = onTaskStart;
    }

    /**
     * Override this method to perform computation on a background thread. Every exception raised
     * inside this method is captured and delivered to Observers via
     * {@link OnTaskError#onTaskError(ObservableTask, Throwable)}. If nobody is watching us, any
     * raised exception is wrapped in a {@link java.lang.RuntimeException} and re-thrown.
     * 
     * @param params Task parameters.
     * @return Result Task result.
     * @throws Throwable Any exception thrown within the method body.
     * @since 0.0.1
     * @see android.os.AsyncTask#doInBackground(Object...)
     */
    protected abstract Result doTask(Params... params) throws Throwable;

    @Override
    protected Result doInBackground(Params... params) {
        try {
            return doTask(params);
        } catch (Throwable t) {
            mThrowable = t;

            return null;
        }
    }

    /**
     * Like {@link android.os.AsyncTask} but also calls
     * {@link OnTaskStart#onTaskStart(ObservableTask)}.
     * 
     * <p>
     * <i>Derived classes must call through to the super class's implementation of this method.</i>
     * </p>
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (mOnTaskStart != null) {
            mOnTaskStart.onTaskStart(this);
        }
    }

    /**
     * Like {@link android.os.AsyncTask} but also calls
     * {@link OnTaskFinished#onTaskFinished(ObservableTask, Object)}.
     * 
     * <p>
     * <i>Derived classes must call through to the super class's implementation of this method.</i>
     * </p>
     */
    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);

        if ((mThrowable != null) && (mOnTaskError != null)) {
            mOnTaskError.onTaskError(this, mThrowable);
        } else if ((mThrowable != null) && (mOnTaskError == null)) {
            throw new RuntimeException(mThrowable);
        } else if (mOnTaskFinished != null) {
            mOnTaskFinished.onTaskFinished(this, result);
        }
    }
}
