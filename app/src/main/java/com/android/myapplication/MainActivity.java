package com.android.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.android.myapplication.nativeutils.JniSurface;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import timber.log.Timber;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

    private final String url = "https://www.baidu.com/";
    private SurfaceView mSurfaceView;
    private SurfaceUtils mSurfaceUtils;
    private Bitmap bitmap;

    static {
        System.loadLibrary("jniSurface");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        mSurfaceView.getHolder().addCallback(this);
        getActionBar().setSubtitle("周四");
        NetRequestQueue.getInstance(getApplicationContext()).addQueue(request);
        NetRequestQueue.getInstance(getApplicationContext()).addQueue(gsonRequest);
        bitmap = drawableToBitmap(getDrawable(R.drawable.urname));
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Timber.e(response);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("pengcancan",error.toString());
        }
    });

    GsonRequest gsonRequest = new GsonRequest("Volcanoscar", new Response.Listener<UserPrintInterface>() {
        @Override
        public void onResponse(UserPrintInterface response) {
            response.userInfoPrint();
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Timber.e("【onErrorResponse】 " + error.getMessage());
        }
    });

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceUtils = new SurfaceUtils();
        mSurfaceUtils.initTargetRectAndSurface(bitmap.getWidth(),bitmap.getHeight(),holder.getSurface());
        JniSurface.init(mSurfaceUtils);
        int[] pixels = new int[bitmap.getWidth()*bitmap.getHeight()];
        bitmap.getPixels(pixels,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
        JniSurface.render(mSurfaceUtils,pixels,bitmap.getWidth(),bitmap.getHeight());
        Timber.d("[surfaceCreated] size , Width : " + bitmap.getWidth() + ", height : " + bitmap.getHeight());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //mSurfaceUtils.initTargetRectAndSurface(width,height,holder.getSurface());
        Timber.d("[surfaceChanged] size , Width : " + width + ", height : " + height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
