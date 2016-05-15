package com.b2soft.lottogo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by woonsungbaek on 2016. 4. 27..
 */
public class ActivityModel {

    static public void changeStartActivity(final Activity activity, final Class cls, final boolean finish, final Bundle bundle) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (cls != null) {
                    Intent intent = new Intent(activity.getApplicationContext(), cls);
                    if (bundle != null) {
                        intent.putExtra("BUNDLE", bundle);
                    }
                    activity.startActivity(intent);
                }
                if (finish) {
                    activity.finish();
                }
            }
        }, 500);
    }
}
