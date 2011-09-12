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

import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import co.bitcode.android.R;
import co.bitcode.android.Refreshable;

/**
 * A user-refreshable {@link ListFragment}
 * 
 * @author Lorenzo Villani
 * @since 0.0.1
 */
public abstract class RefreshableFragment extends ListFragment implements Refreshable
{
        /**
         * Creates an options menu which contains the "Refresh" entry.
         */
        @Override
        public void onCreateOptionsMenu ( Menu menu, MenuInflater inflater )
        {
                super.onCreateOptionsMenu ( menu, inflater );

                inflater.inflate ( R.menu.activity_refreshable, menu );
        }

        /**
         * Handles user-initiated refreshes.
         */
        @Override
        public boolean onOptionsItemSelected ( MenuItem item )
        {
                switch ( item.getItemId () )
                {
                case R.id.fragment_menu_refresh:
                        refresh ( true );

                        return true;
                default:
                        return super.onOptionsItemSelected ( item );
                }
        }

        /**
         * Performs initial refresh.
         */
        @Override
        public void onStart ()
        {
                super.onStart ();

                refresh ( true );
        }
}
