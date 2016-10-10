package com.android.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.Surface;

import java.nio.ByteBuffer;

import timber.log.Timber;

/**
 * Created by Administrator on 2016/10/10.
 */

public class SurfaceUtils {

    private Bitmap mBitmap;
    private ByteBuffer mByteBuffer;
    private int w, h;
    private Surface mSurface;

    public void initTargetRectAndSurface(int width,int height,Surface surface){
        this.w = width;
        this.h = height;
        this.mSurface = surface;
    }

    private ByteBuffer surfaceInit() {
        synchronized (this) {
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
            mByteBuffer = ByteBuffer.allocateDirect(w * h * 2);
            Timber.d("I reached here...【surfaceInit】");
            return mByteBuffer;
        }
    }

    private void surfaceRender() {
        synchronized (this) {
            try {
                Canvas c = mSurface.lockCanvas(null);
                mBitmap.copyPixelsFromBuffer(mByteBuffer);
                c.drawBitmap(mBitmap, 0, 0, null);
                mSurface.unlockCanvasAndPost(c);
                Timber.d("I reached here...【surfaceRender】");
            } catch (Exception e) {
            }
        }
    }

    private void surfaceRelease() {
        synchronized (this) {
            mBitmap.recycle();
            mBitmap = null;
            mByteBuffer = null;
            Timber.d("I reached here...【surfaceRelease】");
        }
    }

}
