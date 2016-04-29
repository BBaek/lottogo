package com.bbaek.lottogo.utils;

import android.content.Context;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by woonsungbaek on 2015. 12. 30..
 */
public class ViewUtils {
    public static void antiOverlapClick(final View view, long antiTime) {
        view.setEnabled(false);
        Handler _hanHandler = new Handler();
        _hanHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, antiTime);
    }

    public static int convertPxToDp(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static float convertPxToDpFloat(Context context, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    static private final int DEFAULT_HDIP_DENSITY_SCALE = 1;

    public static int convertDpToPx(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / DEFAULT_HDIP_DENSITY_SCALE * scale);
    }
}
