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

package co.bitcode.android.preference;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import co.bitcode.android.R;

/**
 * A {@link DialogPreference} which shows the current persisted value in the summary field.
 * 
 * <p>
 * It provides hooks for subclasses to store new values which will be called at appropriate times.
 * </p>
 * 
 * @since 0.0.1
 * @author Lorenzo Villani
 */
public abstract class PreviewPreference extends DialogPreference {
    public PreviewPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Sub-classes must implement this method in order to show a string representation of persisted
     * data.
     * 
     * @return Persisted value as string.
     * @since 0.0.1
     */
    protected abstract String getPersistedValueAsString();

    /**
     * Set-up this Preference.
     */
    @Override
    protected View onCreateView(ViewGroup parent) {
        refreshSummary();

        return super.onCreateView(parent);
    }

    /**
     * Updates summary with new value.
     */
    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            persistValue();

            refreshSummary();
        }
    }

    /**
     * This method is invoked when the dialog is closed and it's time to persist the preference's
     * value.
     * 
     * @since 0.0.1
     */
    protected abstract void persistValue();

    /**
     * Refreshes summary.
     */
    private void refreshSummary() {
        String value;

        value = getPersistedValueAsString();

        if (value == null) {
            setSummary(R.string.previewpreference_notSet);
        } else {
            setSummary(value);
        }
    }
}
