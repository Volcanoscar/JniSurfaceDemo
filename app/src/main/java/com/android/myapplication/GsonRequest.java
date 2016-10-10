package com.android.myapplication;

import android.os.Looper;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/10/9.
 */

public class GsonRequest extends Request<UserPrintInterface> {

    final Gson gson = new Gson();

    private final Response.Listener<UserPrintInterface> mListener;

    public GsonRequest(int method, String url, Response.Listener<UserPrintInterface> listener,
                       Response.ErrorListener errorListener){
        super(method,url,errorListener);
        mListener = listener;
    }

    public GsonRequest(String username, Response.Listener<UserPrintInterface> listener, Response.ErrorListener errorListener) {
        this(Method.GET, Constants.API_URL + username, listener, errorListener);
    }

    @Override
    protected Response<UserPrintInterface> parseNetworkResponse(NetworkResponse response) {
        UserPrintInterface userInfo;
        Log.i("pengcancan","【parseNetworkResponse】 Main thread ? " + (Looper.myLooper() == Looper.getMainLooper()) + ", response.statusCode : " + response.statusCode);
        try {
            userInfo = gson.fromJson(new String(response.data, HttpHeaderParser.parseCharset(response.headers)),UserInfo.class);
        } catch (Exception e) {
            userInfo = gson.fromJson(new String(response.data),UserNotFoundInfo.class);
        }
        return Response.success(userInfo, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(UserPrintInterface response) {
        mListener.onResponse(response);
        Log.i("pengcancan","【deliverResponse】 Main thread ? " + (Looper.myLooper() == Looper.getMainLooper()));
    }
}
