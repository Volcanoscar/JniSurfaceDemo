package com.android.myapplication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/10/8.
 */
public class NetRequestQueue {
    private volatile RequestQueue requestQueue = null;
    private static NetRequestQueue ourInstance = null;

    public static NetRequestQueue getInstance(Context context) {
        if (ourInstance == null){
            ourInstance = new NetRequestQueue(context);
        }
        return ourInstance;
    }

    private NetRequestQueue(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void addQueue(Request request){
        requestQueue.add(request);
    }
}
