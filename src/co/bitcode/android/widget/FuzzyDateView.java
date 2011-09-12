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

import java.text.ParseException;
import java.util.Date;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import co.bitcode.android.R;
import co.bitcode.android.util.ISODateFormatter;

/**
 * Interprets an ISO Date string and shows it as a fuzzy string such as "few moments ago", "one hour ago", etc.
 * 
 * @since 0.0.1
 * @author Lorenzo Villani
 */
public class FuzzyDateView extends TextView
{
        private Date mStartDate;

        private static final int SECOND = 1000;
        private static final int MINUTE = 60 * SECOND;
        private static final int HOUR = 60 * MINUTE;
        private static final int DAY = 24 * HOUR;
        private static final int WEEK = 7 * DAY;

        /**
         * @param context
         * @param attrs
         * @since 0.0.1
         */
        public FuzzyDateView ( Context context, AttributeSet attrs )
        {
                super ( context, attrs );
        }

        /**
         * Refreshes the fuzzy date string.
         * 
         * @since 0.0.1
         */
        public void refresh ()
        {
                if ( mStartDate != null )
                {
                        setText ( mStartDate );
                }
        }

        /**
         * Shows the fuzzy date string.
         * 
         * @param sourceDate Starting date.
         * @since 0.0.1
         */
        public void setText ( Date sourceDate )
        {
                long difference;

                difference = Math.abs ( new Date ().getTime () - sourceDate.getTime () );

                if ( difference < 59 * SECOND )
                {
                        // Moments ago
                        super.setText ( getContext ().getString ( R.string.fuzzydateview_momentsAgoLabel ) );
                }
                else if ( difference < 2 * MINUTE )
                {
                        // One minute ago
                        super.setText ( getContext ().getString ( R.string.fuzzydateview_oneMinuteAgoLabel ) );
                }
                else if ( difference < HOUR )
                {
                        // X minutes ago
                        super.setText ( String.format (
                                        getContext ().getString ( R.string.fuzzydateview_minutesAgoFormat ),
                                        ( int ) difference / MINUTE ) );
                }
                else if ( difference < 2 * HOUR )
                {
                        // One hour ago
                        super.setText ( getContext ().getString ( R.string.fuzzydateview_oneHourAgoLabel ) );
                }
                else if ( difference < DAY )
                {
                        // X hours ago
                        super.setText ( String
                                        .format ( getContext ().getString ( R.string.fuzzydateview_hoursAgoFormat ),
                                                        ( int ) difference / HOUR ) );
                }
                else if ( difference < 2 * DAY )
                {
                        // One day ago
                        super.setText ( getContext ().getString ( R.string.fuzzydateview_oneDayAgoLabel ) );
                }
                else if ( difference < WEEK )
                {
                        // X days ago
                        super.setText ( String.format (
                                        getContext ().getString ( R.string.fuzzydateview_daysAgoFormat ),
                                        ( int ) difference / DAY ) );
                }
                else
                {
                        super.setText ( DateUtils.formatDateTime ( getContext (), sourceDate.getTime (),
                                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME ) );
                }
        }

        /**
         * Shows the fuzzy date string.
         * 
         * @param isoDate ISO Date and Time string.
         * @throws ParseException
         * @since 0.0.1
         */
        public void setText ( String isoDate ) throws ParseException
        {
                setText ( ISODateFormatter.parseISODateAndTime ( isoDate ) );
        }

        public Date getStartDate ()
        {
                return mStartDate;
        }

        public void setStartDate ( Date startDate )
        {
                mStartDate = startDate;
        }
}
