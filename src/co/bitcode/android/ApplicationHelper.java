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

package co.bitcode.android;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * Extends {@link android.app.Application} to provide utility methods.
 * 
 * @author Lorenzo Villani
 * @since 0.0.1
 */
public class ApplicationHelper
{
        /**
         * Gets current application version name as specified in <code>AndroidManifest.xml</code>.
         * 
         * @return Application versionName string.
         * @since 0.0.1
         */
        public static String getVersionName ( Context context )
        {
                try
                {
                        ApplicationInfo applicationInfo;
                        PackageManager packageManager;

                        packageManager = context.getPackageManager ();

                        applicationInfo = context.getApplicationInfo ();

                        return packageManager.getPackageInfo ( applicationInfo.packageName, 0 ).versionName;
                }
                catch ( NameNotFoundException ex )
                {
                        throw new RuntimeException ( "Unable to find versionName for this application, "
                                        + "this shouldn't happen!", ex );
                }
        }

        /**
         * Determines if this application is currently being shown to the user.
         * 
         * @return true if we are running in the foreground, false otherwise.
         * @since 0.0.1
         */
        public static boolean isForeground ( Context context )
        {
                ActivityManager activityManager;
                List < RunningAppProcessInfo > runningAppProcesses;

                activityManager = ( ActivityManager ) context.getSystemService ( Context.ACTIVITY_SERVICE );

                runningAppProcesses = activityManager.getRunningAppProcesses ();

                for ( RunningAppProcessInfo processInfo : runningAppProcesses )
                {
                        if ( processInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND )
                        {
                                try
                                {
                                        ApplicationInfo applicationInfo;

                                        applicationInfo = context.getPackageManager ().getApplicationInfo (
                                                        processInfo.processName, PackageManager.GET_META_DATA );

                                        if ( applicationInfo.packageName.equals ( context.getPackageName () ) )
                                        {
                                                return true;
                                        }
                                }
                                catch ( NameNotFoundException ex )
                                {
                                        return false;
                                }
                        }
                }

                return false;
        }
}
