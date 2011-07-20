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

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * Makes it easier to customize the {@link TabHost} inside a {@link TabActivity}.
 * 
 * @since 0.0.1
 * @author Lorenzo Villani
 */
public class CustomizableTabActivity extends TabActivity {
    private LayoutInflater mLayoutInflater;
    private TabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLayoutInflater = getLayoutInflater();
    }

    /**
     * <strong>Make sure to call this in subclasses onCreate() method like this:<strong>
     * 
     * <pre>
     * setupTabHost(getTabHost());
     * </pre>
     * 
     * @param tabHost
     * @since 0.0.1
     */
    protected void setupTabHost(TabHost tabHost) {
        mTabHost = tabHost;
    }

    /**
     * Adds a text-only tab, automatically wrapping class in an Intent.
     * 
     * @param tag Tag
     * @param textId String resource ID
     * @param layoutId Custom layout resource ID
     * @param wrappedClass Wrapped class
     * @since 0.0.1
     */
    protected void addTab(String tag, int textId, int layoutId, Class<?> wrappedClass) {
        Intent intent;

        intent = new Intent(this, wrappedClass);

        addTab(tag, textId, 0, layoutId, intent);
    }

    /**
     * Add a text-only tab, automatically wrapping class in an Intent.
     * 
     * @param tag Tag
     * @param textId String resource ID
     * @param drawableId Icon resource ID
     * @param layoutId Custom layout resource ID
     * @param wrappedClass Wrapped class
     * @since 0.0.1
     */
    protected void addTab(String tag, int textId, int drawableId, int layoutId,
            Class<?> wrappedClass) {
        Intent intent;

        intent = new Intent(this, wrappedClass);

        addTab(tag, textId, drawableId, layoutId, intent);
    }

    /**
     * Adds a text-only tab.
     * 
     * @param tag Tag
     * @param textId String resource ID
     * @param layoutId Custom layout resource ID
     * @param content Intent
     * @since 0.0.1
     */
    protected void addTab(String tag, int textId, int layoutId, Intent content) {
        addTab(tag, textId, 0, layoutId, content);
    }

    /**
     * Adds a new tab with a custom layout.
     * 
     * @param tag Tag
     * @param textId String resource ID
     * @param drawableId Icon resource ID
     * @param layoutId Custom layout resource ID
     * @param content Intent
     * @since 0.0.1
     */
    protected void addTab(String tag, int textId, int drawableId, int layoutId, Intent content) {
        View view;
        TabHost.TabSpec tabSpec;

        view = mLayoutInflater.inflate(layoutId, null);

        if (drawableId != 0) {
            ImageView imageView;

            imageView = (ImageView) view.findViewById(android.R.id.icon);
            imageView.setImageResource(drawableId);
        }

        if (textId != 0) {
            TextView textView;

            textView = (TextView) view.findViewById(android.R.id.title);
            textView.setText(textId);
        }

        tabSpec = mTabHost.newTabSpec(tag);
        tabSpec.setIndicator(view);
        tabSpec.setContent(content);

        mTabHost.addTab(tabSpec);
    }
}
