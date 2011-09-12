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

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

/**
 * Maps Enum values to user-friendly strings and shows them inside a drop-down list.
 * 
 * @since 0.0.1
 * @author Lorenzo Villani
 */
public class EnumAdapter < E extends Enum < E >> extends CustomViewArrayAdapter < E >
{
        private String[] mFriendlyStrings;

        /**
         * @see http://www.youtube.com/watch?v=wDBM6wVEO70
         * @author Lorenzo Villani
         */
        private static class ViewHolder
        {
                public TextView textView;
        }

        /**
         * @see http://www.youtube.com/watch?v=wDBM6wVEO70
         * @author Lorenzo Villani
         */
        private static class DropdownViewHolder
        {
                public CheckedTextView checkedTextView;
        }

        /**
         * @param context
         * @param list
         * @since 0.0.1
         */
        public EnumAdapter ( Context context, List < E > list, String[] friendlyStrings )
        {
                super ( context, list );

                assert ( friendlyStrings.length == list.size () );

                mFriendlyStrings = friendlyStrings;
        }

        /**
         * Set-up "idle" view.
         */
        @Override
        public View getView ( int position, View convertView, ViewGroup parent )
        {
                ViewHolder viewHolder;

                //
                // Set-up views if not done before
                //
                if ( convertView == null )
                {
                        convertView = inflate ( android.R.layout.simple_spinner_item );

                        viewHolder = new ViewHolder ();
                        viewHolder.textView = ( TextView ) convertView.findViewById ( android.R.id.text1 );

                        convertView.setTag ( viewHolder );
                }
                else
                {
                        viewHolder = ( ViewHolder ) convertView.getTag ();
                }

                //
                // Otherwise reuse it
                //
                viewHolder.textView.setText ( mFriendlyStrings[position] );

                return convertView;
        }

        /**
         * Set-up "dropdown" view.
         */
        @Override
        public View getDropDownView ( int position, View convertView, ViewGroup parent )
        {
                DropdownViewHolder viewHolder;

                //
                // Set-up views if not done before
                //
                if ( convertView == null )
                {
                        convertView = inflate ( android.R.layout.simple_spinner_dropdown_item );

                        viewHolder = new DropdownViewHolder ();
                        viewHolder.checkedTextView = ( CheckedTextView ) convertView.findViewById ( android.R.id.text1 );

                        convertView.setTag ( viewHolder );
                }
                else
                {
                        viewHolder = ( DropdownViewHolder ) convertView.getTag ();
                }

                //
                // Otherwise reuse it
                //
                viewHolder.checkedTextView.setText ( mFriendlyStrings[position] );

                return convertView;
        }
}
