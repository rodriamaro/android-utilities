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

import android.app.ListActivity;
import android.view.Menu;
import android.view.MenuItem;

import co.bitcode.android.R;

/**
 * Makes a {@link ListActivity} user-refreshable.
 * 
 * @since 0.0.1
 * @author Lorenzo Villani
 */
public abstract class RefreshableListActivity extends ListActivity {
    private boolean mFirstTime;
    
    public RefreshableListActivity() {
        super();
        
        mFirstTime = true;
    }
    
    /**
     * Inflates the options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refreshablelistactivity, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handles click on "Refresh" menu entry.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.refreshablelistactivity_refresh:
            refresh(true);

            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Subclasses must implement this method to handle the "Refresh" menu item.
     * 
     * <p>
     * This method is automatically invoked the first time this Activity is shown to the user.
     * </p>
     * 
     * @param forced True if we forced a refresh via the menu item.
     * @since 0.0.1
     */
    public abstract void refresh(boolean forced);
    
    /**
     * Invokes {@link #refresh(boolean)} the first time this Activity is shown.
     */
    @Override
    protected void onResume() {
        super.onResume();
        
        if (mFirstTime) {
            refresh(true);
        }
    }
}