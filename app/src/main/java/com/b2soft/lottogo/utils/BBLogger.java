package com.b2soft.lottogo.utils;

import android.content.Context;
import android.util.Log;

import com.b2soft.lottogo.BuildConfig;

/**
 * Created by woonsungbaek on 2015. 11. 17..
 */
public class BBLogger {
    Context context;
    String activity = "";
    String tag = "";

    public BBLogger(Context context) {
        this.context = context;
        this.activity = context.getClass().getSimpleName();
        this.tag = "===== ADO.DEBUG[" + activity + "]";
    }

    public BBLogger(Context context, String tag) {
        this.context = context;
        this.activity = tag;
        this.tag = "===== ADO.DEBUG[" + activity + "]";
    }

    public BBLogger(String tag) {
        this.activity = tag;
        this.tag = "===== ADO.DEBUG[" + activity + "]";
    }

    public void info(String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg);
        }
    }

    public void info(String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg, tr);
        }
    }

    public void error(String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
    }

    public void error(String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg, tr);
        }
    }

    public void debug(String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public void debug(String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg, tr);
        }
    }

    public void warn(String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, msg);
        }
    }

    public void warn(String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, msg, tr);
        }
    }
}

