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

package co.bitcode.android.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Converts Date objects to their equivalent ISO Date string representation.
 * 
 * @since 0.0.1
 * @author Lorenzo Villani
 */
public class ISODateFormatter {
    /**
     * Pattern used to convert from/to ISO Date format
     */
    public static final DateFormat ISO_FORMAT = new SimpleDateFormat("yyyyMMdd");

    /**
     * Pattern used to convert from/to ISO Date And Time format.
     */
    public static final DateFormat ISO_FULL_FORMAT = new SimpleDateFormat("yyyyMMddhhmmss");

    static {
        ISO_FULL_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * @param date Date to format
     * @return Full ISO Date (date + time)
     * @since 0.0.1
     */
    public static String format(Date date) {
        return ISO_FULL_FORMAT.format(date);
    }

    /**
     * @param date Date to format
     * @return ISO Date (only date)
     * @since 0.0.1
     */
    public static String formatDate(Date date) {
        return ISO_FORMAT.format(date);
    }

    /**
     * Parse an ISO Date from a String.
     * 
     * @param date ISO Date string.
     * @return {@link java.util.Date} object
     * @throws ParseException
     * @since 0.0.1
     */
    public static Date parseISODate(String date) throws ParseException {
        return ISO_FORMAT.parse(date);
    }

    /**
     * Parse ISO Date and Time from a String.
     * 
     * @param dateAndTime ISO Date and Time string
     * @return {@link java.util.Date} object
     * @throws ParseException
     * @since 0.0.1
     */
    public static Date parseISODateAndTime(String dateAndTime) throws ParseException {
        return ISO_FULL_FORMAT.parse(dateAndTime);
    }
}
