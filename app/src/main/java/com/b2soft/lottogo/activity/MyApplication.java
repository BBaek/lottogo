package com.b2soft.lottogo.activity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDexApplication;

import com.b2soft.lottogo.model.Lotto;
import com.b2soft.lottogo.model.Migration;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.google.android.gms.ads.AdRequest;

import java.util.List;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by woonsungbaek on 2016. 3. 30..
 */
public class MyApplication extends MultiDexApplication {
    private static MyApplication instance;
    public static boolean DEBUG;
    public static List<Lotto> lottoList;
    public static AdRequest adRequest;
    @Override
    public void onCreate() {
        super.onCreate();
        // init plugin
        // fabric
        Fabric.with(this, new Crashlytics());
        instance = this;

        // initdata to realm(~1.18)
        new Migration(this);

//        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext())
//                .name("lotto.realm")
//                .schemaVersion(0)
//                .deleteRealmIfMigrationNeeded()
//                .build();
//
//        // set default Realm
//        Realm.setDefaultConfiguration(config);

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
