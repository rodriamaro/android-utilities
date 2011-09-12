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

package co.bitcode.android.app;

import android.widget.ListView;

import co.bitcode.android.R;
import co.bitcode.android.widget.ProgressListView;

/**
 * An activity that displays a list of items using a {@link ProgressListView}
 * <p>
 * The progress indicator is hidden by default.
 * </p>
 * 
 * @since 0.0.1
 * @author Lorenzo Villani
 */
public abstract class ProgressListActivity extends RefreshableListActivity
{
        private ProgressListView mProgressListView;

        /**
         * Get ListView.
         * 
         * @since 0.0.1
         */
        @Override
        public ListView getListView ()
        {
                return mProgressListView.getListView ();
        }

        /**
         * Get {@link ProgressListView}.
         * 
         * @return the {@link ProgressListView}
         * @since 0.0.1
         */
        public ProgressListView getProgressListView ()
        {
                return mProgressListView;
        }

        /**
         * Hide the progress indicator.
         * 
         * @since 0.0.1
         */
        public void hideIndicator ()
        {
                mProgressListView.hideIndicator ();
        }

        /**
         * Show the progress indicator.
         * 
         * @since 0.0.1
         */
        public void showIndicator ()
        {
                mProgressListView.showIndicator ();
        }

        /**
         * Makes sure that subclasses have defined progresslist.
         */
        @Override
        protected void onStart ()
        {
                super.onStart ();

                mProgressListView = ( ProgressListView ) findViewById ( R.id.progresslist );

                if ( mProgressListView == null )
                {
                        throw new RuntimeException ( "Your content must have a ProgressListView whose id "
                                        + "attribute is 'co.bitcode.android.R.id.progresslist'" );
                }
        }
}
