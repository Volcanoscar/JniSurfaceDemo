package com.android.myapplication;

import android.util.Log;

import timber.log.Timber;

/**
 * Created by Administrator on 2016/10/9.
 */

public class UserNotFoundInfo implements UserPrintInterface {

    /**
     * message : Not Found
     * documentation_url : https://developer.github.com/v3
     */

    private String message;
    private String documentation_url;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDocumentation_url() {
        return documentation_url;
    }

    public void setDocumentation_url(String documentation_url) {
        this.documentation_url = documentation_url;
    }

    @Override
    public String toString() {
        return "UserNotFoundInfo{" +
                "message='" + message + '\'' +
                ", documentation_url='" + documentation_url + '\'' +
                '}';
    }

    @Override
    public void userInfoPrint() {
        Timber.e(toString());
    }
}
