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

import android.content.pm.ApplicationInfo;

import co.bitcode.android.Application;

/**
 * @author Lorenzo Villani
 * @see Application
 */
public class Log
{
        private static final String APPLICATION_NAME;
        private static final boolean DEBUGGABLE;

        // ----------------------------------------------------------------------
        // Types
        // ----------------------------------------------------------------------

        /**
         * @author Lorenzo Villani
         */
        public enum LogLevel
        {
                ASSERT ( android.util.Log.ASSERT ),
                DEBUG ( android.util.Log.DEBUG ),
                ERROR ( android.util.Log.ERROR ),
                INFO ( android.util.Log.INFO ),
                VERBOSE ( android.util.Log.VERBOSE ),
                WARN ( android.util.Log.WARN );

                private int level;

                private LogLevel ( int level )
                {
                        this.level = level;
                }

                public int getLevel ()
                {
                        return level;
                }
        }

        // ----------------------------------------------------------------------
        // Static initialization
        // ----------------------------------------------------------------------

        static
        {
                final ApplicationInfo applicationInfo;

                applicationInfo = Application.getInstance ().getApplicationInfo ();

                APPLICATION_NAME = applicationInfo.name;

                DEBUGGABLE = ( applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE ) != 0 ? true : false;
        }

        // ----------------------------------------------------------------------
        // Public static methods
        // ----------------------------------------------------------------------

        /**
         * Sends a {@link LogLevel#DEBUG} log message.
         * 
         * @param message The message you would like logged.
         * @param objects Additional objects whose string representation is used in the message.
         * @return The amount of bytes written.
         */
        public static final int d ( String message, Object ... objects )
        {
                return println ( LogLevel.DEBUG, message, objects );
        }

        /**
         * Sends a {@link LogLevel#DEBUG} log message and log the exception.
         * 
         * @param message The message you would like logged.
         * @param throwable An exception to log.
         * @param objects Additional objects whose string representation is used in the message.
         * @return The amount of bytes written.
         */
        public static final int d ( String message, Throwable throwable, Object ... objects )
        {
                return println ( LogLevel.DEBUG, message, throwable, objects );
        }

        /**
         * Sends a {@link LogLevel#ERROR} log message.
         * 
         * @param message The message you would like logged.
         * @param objects Additional objects whose string representation is used in the message.
         * @return The amount of bytes written.
         */
        public static final int e ( String message, Object ... objects )
        {
                return println ( LogLevel.ERROR, message, objects );
        }

        /**
         * Sends a {@link LogLevel#ERROR} log message and log the exception.
         * 
         * @param message The message you would like logged.
         * @param throwable An exception to log.
         * @param objects Additional objects whose string representation is used in the message.
         * @return The amount of bytes written.
         */
        public static final int e ( String message, Throwable throwable, Object ... objects )
        {
                return println ( LogLevel.ERROR, message, throwable, objects );
        }

        /**
         * Sends a {@link LogLevel#INFO} log message.
         * 
         * @param message The message you would like logged.
         * @param objects Additional objects whose string representation is used in the message.
         * @return The amount of bytes written.
         */
        public static final int i ( String message, Object ... objects )
        {
                return println ( LogLevel.INFO, message, objects );
        }

        /**
         * Sends a {@link LogLevel#INFO} log message and log the exception.
         * 
         * @param message The message you would like logged.
         * @param throwable An exception to log.
         * @param objects Additional objects whose string representation is used in the message.
         * @return The amount of bytes written.
         */
        public static final int i ( String message, Throwable throwable, Object ... objects )
        {
                return println ( LogLevel.INFO, message, throwable, objects );
        }

        /**
         * Sends a {@link LogLevel#VERBOSE} log message.
         * 
         * @param message The message you would like logged.
         * @param objects Additional objects whose string representation is used in the message.
         * @return The amount of bytes written.
         */
        public static final int v ( String message, Object ... objects )
        {
                return println ( LogLevel.VERBOSE, message, objects );
        }

        /**
         * Sends a {@link LogLevel#VERBOSE} log message and log the exception.
         * 
         * @param message The message you would like logged.
         * @param throwable An exception to log.
         * @param objects Additional objects whose string representation is used in the message.
         * @return The amount of bytes written.
         */
        public static final int v ( String message, Throwable throwable, Object ... objects )
        {
                return println ( LogLevel.VERBOSE, message, throwable, objects );
        }

        // ----------------------------------------------------------------------
        // Private static methods
        // ----------------------------------------------------------------------

        /**
         * Sends a log message.
         * 
         * @param logLevel The {@link LogLevel} to use.
         * @param message The message you would like logged.
         * @param objects Additional objects whose string representation is used in the message.
         * @return The amount of bytes written.
         */
        private static final int println ( LogLevel logLevel, String message, Object ... objects )
        {
                return println ( logLevel, message, null, objects );
        }

        /**
         * Sends a log message and logs the exception.
         * 
         * @param logLevel The {@link LogLevel} to use.
         * @param message The message you would like logged.
         * @param throwable An exception to log.
         * @param objects Additional objects whose string representation is used in the message.
         * @return The amount of bytes written.
         */
        private static final int println ( LogLevel logLevel, String message, Throwable throwable, Object ... objects )
        {
                String formattedMessage;

                //
                // Appends the stack trace at the end of the formatted message
                //
                if ( throwable != null )
                {
                        StringBuffer stringBuffer;

                        stringBuffer = new StringBuffer ();
                        stringBuffer.append ( String.format ( message, objects ) );
                        stringBuffer.append ( '\n' );
                        stringBuffer.append ( android.util.Log.getStackTraceString ( throwable ) );

                        formattedMessage = stringBuffer.toString ();
                }
                else
                {
                        formattedMessage = String.format ( message, objects );
                }

                switch ( logLevel )
                {
                case DEBUG:
                case INFO:
                        if ( DEBUGGABLE )
                        {
                                return android.util.Log.println ( logLevel.getLevel (), APPLICATION_NAME,
                                                formattedMessage );
                        }
                        else
                        {
                                return 0;
                        }
                default:
                        return android.util.Log.println ( logLevel.getLevel (), APPLICATION_NAME, formattedMessage );
                }
        }
}
