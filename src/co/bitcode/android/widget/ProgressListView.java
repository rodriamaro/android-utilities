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
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import co.bitcode.android.R;

/**
 * A {@link ListView} which shows a progress bar while loading data.
 * 
 * @since 0.0.1
 * @author Lorenzo Villani
 */
public class ProgressListView extends RelativeLayout {
    private ListView mListView;
    private TextView mTextEmpty;
    private ViewGroup mProgressIndicator;

    /**
     * @param context
     * @param attrs
     * @since 0.0.1
     */
    public ProgressListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray;

        TextView text;
        ViewGroup root;

        typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressListView);

        root = (ViewGroup) inflate(context, R.layout.progresslistview, (ViewGroup) getRootView());

        text = (TextView) root.findViewById(R.id.progresslistview_text);
        text.setText(typedArray.getString(R.styleable.ProgressListView_text));

        mListView = (ListView) root.findViewById(android.R.id.list);

        mProgressIndicator = (ViewGroup) root.findViewById(R.id.progresslistview_indicator);

        mTextEmpty = (TextView) root.findViewById(android.R.id.empty);
        mTextEmpty.setText(typedArray.getString(R.styleable.ProgressListView_textEmpty));
    }

    /**
     * Gets the {@link ListView} embedded in this {@link ViewGroup}.
     * 
     * @return The {@link ListView} associated with this <code>ProgressListView</code>
     * @since 0.0.1
     */
    public ListView getListView() {
        return mListView;
    }

    /**
     * Hides the progress indicator and shows the {@link ListView}.
     * 
     * @since 0.0.1
     */
    public void hideIndicator() {
        mListView.setVisibility(VISIBLE);

        mProgressIndicator.setVisibility(INVISIBLE);
    }

    /**
     * Shows the progress indicator and hides the {@link ListView}.
     * 
     * @since 0.0.1
     */
    public void showIndicator() {
        mListView.setVisibility(INVISIBLE);

        mProgressIndicator.setVisibility(VISIBLE);
    }
}
