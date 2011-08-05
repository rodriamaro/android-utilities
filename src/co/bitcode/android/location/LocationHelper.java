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

package co.bitcode.android.location;

import android.location.Location;
import android.location.LocationManager;

public class LocationHelper {
    private static final int TWO_MINUTES = 1000 * 60 * 2;

    /**
     * Retrieves best known location from all location providers available on this system.
     * 
     * @param locationManager A {@link LocationManager}
     * @return Best known location or <code>null</code> if it could not be found.
     */
    public static Location getBestKnownLocation(LocationManager locationManager) {
        Location bestLocation;

        bestLocation = null;

        for (String provider : locationManager.getProviders(false)) {
            Location location;

            location = locationManager.getLastKnownLocation(provider);

            if ((location != null) && isBetterLocation(location, bestLocation)) {
                bestLocation = location;
            }
        }

        return bestLocation;
    }

    /**
     * Determines whether one Location reading is better than the current Location fix
     * 
     * @param location The new Location that you want to evaluate
     * @param currentBestLocation The current Location fix, to which you want to compare the new one
     */
    //
    // This method was copied from Android Developer site. The licensing terms of this stuff are
    // unknown to me. I'm assuming it is "public domain" but if you have a different opinion, please
    // contact me.
    //
    public static boolean isBetterLocation(Location location, Location currentBestLocation) {
        boolean isFromSameProvider;
        boolean isLessAccurate;
        boolean isMoreAccurate;
        boolean isNewer;
        boolean isSignificantlyLessAccurate;
        boolean isSignificantlyNewer;
        boolean isSignificantlyOlder;
        int accuracyDelta;
        long timeDelta;

        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        //
        // Check whether the new location fix is newer or older
        //
        timeDelta = location.getTime() - currentBestLocation.getTime();
        isSignificantlyNewer = timeDelta > TWO_MINUTES;
        isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        isNewer = timeDelta > 0;

        if (isSignificantlyNewer) {
            // If it's been more than two minutes since the current location, use the new location
            // because the user has likely moved

            return true;
        } else if (isSignificantlyOlder) {
            // If the new location is more than two minutes older, it must be worse

            return false;
        }

        //
        // Check whether the new location fix is more or less accurate
        //
        accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        isLessAccurate = accuracyDelta > 0;
        isMoreAccurate = accuracyDelta < 0;
        isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        //
        // Determine location quality using a combination of timeliness and accuracy
        //
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }

        return false;
    }

    /**
     * Checks whether two providers are the same
     * 
     * @return true if providers are the same, false otherwise.
     */
    private static boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }

        return provider1.equals(provider2);
    }
}
