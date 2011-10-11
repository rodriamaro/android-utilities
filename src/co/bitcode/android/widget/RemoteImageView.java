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

package co.bitcode.android.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import co.bitcode.android.os.AsyncTask;

/**
 * An {@link ImageView} which downloads a picture from the network and displays it when done.
 * 
 * @author Lorenzo Villani
 * @since 0.0.1
 */
public abstract class RemoteImageView extends RoundedCornerImageView
{
        // -------------------------------------------------------------------------------------------------------------
        // Protected Constants
        // -------------------------------------------------------------------------------------------------------------

        /**
         * Use this as a return value with {@link #getMissingDrawable()} and {@link #getLoadingDrawable()} in case you
         * don't want to change our current bitmap.
         */
        protected static final int NO_PICTURE = -1;

        // -------------------------------------------------------------------------------------------------------------
        // Public Constructors
        // -------------------------------------------------------------------------------------------------------------

        /**
         * @param context
         * @since 0.0.1
         */
        public RemoteImageView ( Context context )
        {
                super ( context );
        }

        /**
         * @param context
         * @param attrs
         * @since 0.0.1
         */
        public RemoteImageView ( Context context, AttributeSet attrs )
        {
                super ( context, attrs );
        }

        /**
         * @param context
         * @param attrs
         * @param defStyle
         * @since 0.0.1
         */
        public RemoteImageView ( Context context, AttributeSet attrs, int defStyle )
        {
                super ( context, attrs, defStyle );
        }

        // -------------------------------------------------------------------------------------------------------------
        // Public Methods
        // -------------------------------------------------------------------------------------------------------------

        /**
         * Triggers an asynchronous image download.
         * 
         * @see #fetchRemoteImage(Uri)
         * @since 0.0.1
         */
        @Override
        public void setImageURI ( Uri uri )
        {
                new AsyncTask < Uri, Bitmap > ()
                {
                        @Override
                        protected void onStart ()
                        {
                                super.onStart ();

                                int loadingDrawable;

                                loadingDrawable = getLoadingDrawable ();

                                if ( loadingDrawable != NO_PICTURE )
                                {
                                        setImageResource ( loadingDrawable );
                                }
                        }

                        @Override
                        protected Bitmap doInBackground ( Uri ... params ) throws Exception
                        {
                                if ( params != null )
                                {
                                        return fetchRemoteImage ( params[0] );
                                }
                                else
                                {
                                        return null;
                                }
                        }

                        @Override
                        protected void onFinish ( Bitmap result )
                        {
                                super.onFinish ( result );

                                if ( result != null )
                                {
                                        setImageBitmap ( result );
                                }
                                else
                                {
                                        int missingDrawable;

                                        missingDrawable = getMissingDrawable ();

                                        if ( missingDrawable != NO_PICTURE )
                                        {
                                                setImageResource ( missingDrawable );
                                        }
                                }
                        }
                }.execute ( uri );
        }

        // -------------------------------------------------------------------------------------------------------------
        // Protected Methods
        // -------------------------------------------------------------------------------------------------------------

        /**
         * This method is invoked when there's a need to download a picture from the network.
         * <p>
         * Execution of this method is already wrapped inside an {@link AsyncTask} which means that computation doesn't
         * happen inside the UI thread.
         * </p>
         * 
         * @param uri Uri of the image to download.
         * @return A {@link Bitmap} containing the downloaded resource.
         * @since 0.0.1
         */
        protected abstract Bitmap fetchRemoteImage ( Uri uri );

        /**
         * This method is invoked when this {@link ImageView} is downloading a picture from the network.
         * 
         * @return Drawable ID.
         */
        protected abstract int getLoadingDrawable ();

        /**
         * This method is invoked when there's no picture to download from the server, an error occurred or the image
         * could not be found (which is generally indicated by {@link #fetchRemoteImage(Uri)} returning
         * <code>null</code>
         * 
         * @return Drawable ID.
         */
        protected abstract int getMissingDrawable ();
}
