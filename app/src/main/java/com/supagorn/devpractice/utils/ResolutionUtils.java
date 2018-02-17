package com.supagorn.devpractice.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ResolutionUtils {

    public static float dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }

    public static float px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue * scale + 0.5f);
    }

    /**
     * @return Array of screen size index 0 = width, 1 = height
     */
    public static int[] getScreenSize(Context context) {
        int[] screenSize = new int[2];
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowmanager != null) {
            windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
            screenSize[0] = displayMetrics.widthPixels;
            screenSize[1] = displayMetrics.heightPixels;
        }

        return screenSize;
    }
}