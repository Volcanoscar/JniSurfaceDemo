package com.android.myapplication.nativeutils;

import com.android.myapplication.SurfaceUtils;

/**
 * Created by Administrator on 2016/10/10.
 */

public class JniSurface {
    public native static void init(SurfaceUtils surfaceUtils);
    public native static void release(SurfaceUtils surfaceUtils);
    public native static void render(SurfaceUtils surfaceUtils,int[] pixels,int w, int h);
}
