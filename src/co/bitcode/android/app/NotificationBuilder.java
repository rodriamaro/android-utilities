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

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Builds a {@link Notification} object.
 * 
 * @since 0.0.1
 * @author Lorenzo Villani
 */
public class NotificationBuilder {
    private static final int DEFAULT_FLAG = PendingIntent.FLAG_ONE_SHOT;

    private CharSequence mTicker;
    private CharSequence mTitle;
    private CharSequence mMessage;
    private Context mContext;
    private Intent mIntent;
    private int mIconId;
    private int mIntentFlag;

    /**
     * @param context
     * @since 0.0.1
     */
    public NotificationBuilder(Context context) {
        mContext = context;

        mIntentFlag = DEFAULT_FLAG;
    }

    /**
     * Sets icon shown in ticker and notification.
     * 
     * @param resourceId Drawable ID.
     * @return this
     * @since 0.0.1
     */
    public NotificationBuilder setIcon(int resourceId) {
        mIconId = resourceId;

        return this;
    }

    /**
     * Sets the {@link Intent} to launch when users click on our notification.
     * 
     * <p>
     * This {@link Intent} is automatically wrapped in a {@link PendingIntent}.
     * </p>
     * 
     * @param intent Intent to launch.
     * @param title Notification title.
     * @param message Notification message.
     * @return this
     * @since 0.0.1
     */
    public NotificationBuilder setIntent(Intent intent, CharSequence title, CharSequence message) {
        mIntent = intent;

        mMessage = message;

        mTitle = title;

        return this;
    }

    /**
     * Sets ticker message to show in the notification bar.
     * 
     * @param ticker Ticker message.
     * @return this.
     * @since 0.0.1
     */
    public NotificationBuilder setTicker(CharSequence ticker) {
        mTicker = ticker;

        return this;
    }

    /**
     * Builds {@link Notification} object.
     * 
     * @since 0.0.1
     * @return A {@link Notification} object.
     */
    public Notification create() {
        Notification notification;

        notification = new Notification(mIconId, mTicker, System.currentTimeMillis());

        if (mIntent != null) {
            PendingIntent pendingIntent;

            pendingIntent = PendingIntent.getActivity(mContext, 0, mIntent, mIntentFlag);

            notification.setLatestEventInfo(mContext, mTitle, mMessage, pendingIntent);
        }

        return notification;
    }
}
