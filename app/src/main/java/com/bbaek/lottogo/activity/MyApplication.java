package com.bbaek.lottogo.activity;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.bbaek.lottogo.model.Lotto;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdRequest;

import io.fabric.sdk.android.Fabric;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by woonsungbaek on 2016. 3. 30..
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    public static boolean DEBUG;
    public static List<Lotto> lottoList;
    public static AdRequest adRequest;
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        instance = this;
        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext())
                .name("lotto.realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        this.DEBUG = isDebuggable(this);
        this.adRequest = new AdRequest.Builder().build();
    }

    public static MyApplication getInstance() {
        return instance;
    }


    /**
     * 현재 디버그모드여부를 리턴
     *
     * @param context
     * @return
     */
    private boolean isDebuggable(Context context) {
        boolean debuggable = false;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo appinfo = pm.getApplicationInfo(context.getPackageName(), 0);
            debuggable = (0 != (appinfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
        /* debuggable variable will remain false */
        }

        return debuggable;
    }
}
