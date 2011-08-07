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

import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * 
 * @author Lorenzo Villani
 */
public class DifferentialArrayAdapter<T> extends ArrayAdapter<T> {
    /**
     * Constructor.
     * 
     * @param context
     * @param textViewResourceId
     */
    public DifferentialArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    /**
     * Constructor.
     * 
     * @param context
     * @param textViewResourceId
     * @param objects
     */
    public DifferentialArrayAdapter(Context context, int textViewResourceId, T[] objects) {
        super(context, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context
     * @param textViewResourceId
     * @param objects
     */
    public DifferentialArrayAdapter(Context context, int textViewResourceId, List<T> objects) {
        super(context, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context
     * @param resource
     * @param textViewResourceId
     */
    public DifferentialArrayAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    /**
     * Constructor.
     * 
     * @param context
     * @param resource
     * @param textViewResourceId
     * @param objects
     */
    public DifferentialArrayAdapter(Context context, int resource, int textViewResourceId,
            List<T> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    /**
     * Constructor.
     * 
     * @param context
     * @param resource
     * @param textViewResourceId
     * @param objects
     */
    public DifferentialArrayAdapter(Context context, int resource, int textViewResourceId,
            T[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    /**
     * Updates this list view.
     * 
     * <strong>O(n<sup>2</sup>) ahead! You've been warned.</strong>
     * 
     * @param collection
     */
    public void update(Collection<T> collection) {
        for (T element : collection) {
            int position;

            position = find(element);

            if (position >= 0) {
                insert(element, position);
                remove(element);
            } else {
                add(element);
            }
        }
    }

    /**
     * Find element position (if any).
     * 
     * @param what Element to find.
     * @return
     */
    private int find(T what) {
        int size;

        size = getCount();

        for (int i = 0; i < size; i++) {
            if (what.equals(getItem(i))) {
                return i;
            }
        }

        return -1;
    }
}
