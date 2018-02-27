//  The MIT License (MIT)

//  Copyright (c) 2018 Intuz Pvt Ltd.

//  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files
//  (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify,
//  merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
//  furnished to do so, subject to the following conditions:

//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
//  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
//  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
//  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

package com.permissionmanagerlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;


public class PermissionManagerInstance {
    private Context mContext;
    private static PermissionManagerListener mPermissionManagerListener;
    private boolean mShowMessageOnRationale = false;
    private boolean mShowMessageBeforeRequest = false;
    private String mMessage = "";
    private String mTitle = "";
    private String mPermissionAsString = "";
    private String positiveButtonText = "";
    private String negativeButtonText = "";

    /**
     * Set PermissionManager Instance with context
     *
     * @param context context
     */

    public PermissionManagerInstance(Context context) {
        mContext = context;
        mMessage = mContext.getString(R.string.str_default_rationale_message);
        mTitle = mContext.getString(R.string.str_rationale_message);
        positiveButtonText = mContext.getString(R.string.str_possitive_button);
        negativeButtonText = mContext.getString(R.string.str_negative_button);
    }

    /**
     * request for permissions ,pass string[] for permission
     * This method will only request for permission those which are yet not granted or once denied
     *
     * @param permissions               String[] of permissions from the object of Manifest.permission
     * @param permissionManagerListener listener will notify once get result of all requests
     */

    public void requestForPermissions(String[] permissions,
                                      PermissionManagerListener permissionManagerListener) {

        mPermissionManagerListener = permissionManagerListener;
        for (String aPermission : permissions) {
            if (!checkPermission(aPermission)) {
                if (mPermissionAsString.length() == 0) {
                    mPermissionAsString = aPermission;
                } else {
                    mPermissionAsString = mPermissionAsString + "," + aPermission;
                }
            }
        }
        Intent intent = new Intent(mContext, PermissionManagerActivity.class);
        intent.putExtra("EXTRA_PERMISSION_LIST", permissions);
        intent.putExtra("EXTRA_PERMISSION_TO_REQUEST", mPermissionAsString.split(","));
        intent.putExtra("EXTRA_MESSAGE", getMessage());
        intent.putExtra("EXTRA_TITLE", getTitle());
        intent.putExtra("EXTRA_IS_SHOW_MESSAGE", isShowMessageOnRationale());
        intent.putExtra("EXTRA_IS_SHOW_MESSAGE_BEFORE", isShowMessageBeforeRequest());
        intent.putExtra("EXTRA_NEGATIVE_BUTTON", getNegativeButtonText());
        intent.putExtra("EXTRA_POSITIVE_BUTTON", getPositiveButtonText());
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(0, 0);
    }

    /**
     * Method to handle result of permission
     *
     * @param permissions  all permissions that are requested
     * @param grantResults result of permission
     * @param allGranted   will be true if all permissions that are requested are allowed by user
     */

    public static void handleResult(String[] permissions, Permission[] grantResults, boolean allGranted) {
        mPermissionManagerListener.permissionCallback(permissions, grantResults, allGranted);
    }

    /**
     * Check for permission whether it is granted or not.
     * It will not check for permanently denial of permission
     *
     * @param permission permission from the object of Manifest.permission
     * @return true if permission is already granted
     */

    public boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Get positive button text for dialog.
     * Default value will be Ok.
     *
     * @return text of positive button
     */

    public String getPositiveButtonText() {
        return positiveButtonText;
    }

    /**
     * Set positive button text for dialog.
     * Default value will be Ok.
     * It will only show when setShowMessageOnRationale is set to true.
     *
     * @param positiveButtonText value to be set as positive button
     */
    public void setPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
    }

    /**
     * Get negative button text for dialog.
     * Default value will be Cancel.
     *
     * @return text of negative button
     */

    public String getNegativeButtonText() {
        return negativeButtonText;
    }

    /**
     * Set negative button text for dialog.
     * Default value will be Cancel.
     * It will only show when setShowMessageOnRationale is set to true.
     *
     * @param negativeButtonText value to be set as negative button
     */

    public void setNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
    }

    /**
     * Is showing message dialog before request or not
     *
     * @return true or false
     */
    public boolean isShowMessageBeforeRequest() {
        return mShowMessageBeforeRequest;
    }

    /**
     * Set this method to true when you want to show dialog before again asking for request.
     * Default value will be false meaning it will show message after denial of request.
     * It will only show when setShowMessageOnRationale is set to true.
     *
     * @param showMessageBeforeRequest true or false
     */

    public void setShowMessageBeforeRequest(boolean showMessageBeforeRequest) {
        this.mShowMessageBeforeRequest = showMessageBeforeRequest;
    }

    /**
     * Get title text for dialog.
     *
     * @return text of title
     */

    public String getTitle() {
        return mTitle;
    }

    /**
     * Set title text for dialog.
     * It will only show when setShowMessageOnRationale is set to true.
     *
     * @param title value to be set as title
     */

    public void setTitle(String title) {
        this.mTitle = title;
    }

    /**
     * Get message text for dialog.
     *
     * @return text of message
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * Set message for dialog.
     * It will only show when setShowMessageOnRationale is set to true.
     *
     * @param message value to be set as message
     */
    public void setMessage(String message) {
        this.mMessage = message;
    }

    /**
     * Is showing rationale dialog or not
     *
     * @return true or false
     */

    public boolean isShowMessageOnRationale() {
        return mShowMessageOnRationale;
    }

    /**
     * Set true if you want to show dialog on denial of any request
     * Default value will be false meaning no dialog will be shown on denial of request
     *
     * @param showMessageOnRationale true or false
     */
    public void setShowMessageOnRationale(boolean showMessageOnRationale) {
        this.mShowMessageOnRationale = showMessageOnRationale;
    }

}
