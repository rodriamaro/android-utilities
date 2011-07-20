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

package co.bitcode.android.os;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

/**
 * An {@link ObservableTask} task which shows a {@link ProgressDialog} during execution.
 * 
 * <p>
 * In case of errors shows an ErrorDialog.
 * </p>
 * 
 * @since 0.0.1
 * @author Lorenzo Villani
 */
public abstract class ProgressDialogTask<Params, Progress, Result> extends
        ObservableTask<Params, Progress, Result> implements OnCancelListener {
    private Context mContext;
    private ProgressDialog mProgressDialog;

    /**
     * Constructs a new task which shows a {@link ProgressDialog} while executing.
     * 
     * <p>
     * By default the {@link ProgressDialog} is not cancelable.
     * </p>
     * 
     * @param messageId String ID of the message to show in the progress dialog.
     * @param activity Parent activity.
     * @since 0.0.1
     * @see #setCancelable(boolean)
     */
    public ProgressDialogTask(Context context, int messageId) {
        mContext = context;

        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setOnCancelListener(this);
        mProgressDialog.setMessage(context.getResources().getString(messageId));
    }

    /**
     * Invoked when the user cancels tries to dismiss the {@link ProgressDialog}.
     * 
     * <p>
     * By default it will attempt to cancel this <code>ProgressDialogTask</code>
     * </p>
     * 
     * @since 0.0.1
     */
    @Override
    public void onCancel(DialogInterface dialog) {
        cancel(true);
    }

    //
    // Getters and Setters
    //
    public Context getContext() {
        return mContext;
    }

    /**
     * Set this task as user-cancelable. This must be called before executing the task.
     * 
     * @param flag Whether the user is allowed to cancel this task.
     * @since 0.0.1
     */
    public void setCancelable(boolean flag) {
        mProgressDialog.setCancelable(flag);
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);

        mProgressDialog.dismiss();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog.show();
    }
}