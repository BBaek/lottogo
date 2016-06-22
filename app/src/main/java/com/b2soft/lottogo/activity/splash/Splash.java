package com.b2soft.lottogo.activity.splash;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.b2soft.lottogo.BuildConfig;
import com.bbaek.common.client.lottogo.manager.LottoManager;
import com.bbaek.common.client.lottogo.model.LottoInfo;
import com.b2soft.lottogo.R;
import com.b2soft.lottogo.Repository.CommonRepository;
import com.b2soft.lottogo.Repository.TransactionCallback;
import com.b2soft.lottogo.activity.main.MainNewActivity;
import com.b2soft.lottogo.model.Lotto;
import com.b2soft.lottogo.utils.BBLogger;
import com.b2soft.lottogo.utils.PreferenceUtils;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;
import io.realm.RealmResults;

public class Splash extends FragmentActivity {
    private BBLogger logger = new BBLogger(this,getClass().getSimpleName());
    Context mContext;
    CommonRepository commonRepository;

    @Bind(R.id.splashVer) TextView versionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        this.mContext = this;
        commonRepository = new CommonRepository();
        getAppCurVersionCode();
        initLottoData();
    }

    private void insertDBFromJson() {
        AssetManager assetMgr = getAssets();
        try {
            InputStream is = assetMgr.open("lotto.json");
            commonRepository.insert(Lotto.class, is, new TransactionCallback.OnInsertCallback() {
                @Override
                public void onSuccess() {
                    logger.debug("insert lotto to file(lotto.json)");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getAppCurVersionCode() {
        PreferenceUtils.instance(mContext).putAppVersion(BuildConfig.VERSION_NAME);
        versionText.setText(BuildConfig.VERSION_NAME);
        logger.debug("CurrentVersion: " + BuildConfig.VERSION_NAME + " [" + BuildConfig.VERSION_CODE + "]");
    }

    private void killApp() {
        moveTaskToBack(true);
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void initLottoData() {
        for (String str : fileList()) {
            logger.debug("file: " + str + "(" + getFileStreamPath(str));
//            deleteFile(str); // test code
        }
        commonRepository.select(Lotto.class, new TransactionCallback.OnSelectAllCallback() {
            @Override
            public void onSuccess(RealmResults results) {
                int maxDrwNo = 0;
                if (results != null && results.size() > 0) {
                    maxDrwNo = results.where().max("drwNo").intValue();
                }
                logger.debug("now db maxDrwNo value: " + maxDrwNo);

                if (maxDrwNo <= 0) {
                    // reaml file (2016.6.22 : 707)
//                    insertDBFromJson();
                    maxDrwNo = results.size();
                    logger.debug("init data from realmDB [" + maxDrwNo + "]");
                }

                // network
                logger.debug("init data from network");
                new SelectDrwNoHttpTask().execute(maxDrwNo + 1);
            }
        });
    }

    protected void changeStartActivity(final Context context, final Class cls, final boolean finish, final Bundle bundle) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (cls != null) {
                    Intent intent = new Intent(context, cls);
                    if (bundle != null) {
                        intent.putExtra("BUNDLE", bundle);
                    }
                    startActivity(intent);
                }
                if (finish) {
                    finish();
                }
            }
        }, 500);
    }

    class SelectDrwNoHttpTask extends AsyncTask<Integer, Void, RealmList<Lotto>> {
        @Override
        protected RealmList<Lotto> doInBackground(Integer... params) {
            RealmList<Lotto> result = new RealmList<>();
            LottoManager mgr = new LottoManager();
            int drwNo = params[0];
            try {
                while (true) {
                    logger.debug("requst next DrwNo: " + drwNo);
                    LottoInfo res = (LottoInfo) mgr.getLottoNumber(drwNo);
                    if (res != null) {
                        if (res.getReturnValue().equals("success")) {
                            Lotto lotto = Lotto.newInstance(res);
                            logger.debug(lotto.toString());
                            logger.debug("return value is [" + lotto.getReturnValue() + "]");
                            result.add(lotto);
                        } else {
//                            result = null;
                            break;
                        }
                    }
                    drwNo++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }


        @Override
        protected void onPostExecute(final RealmList<Lotto> result) {
            super.onPostExecute(result);
            if (result != null) {
                commonRepository.insert(result, new TransactionCallback.OnInsertCallback() {
                    @Override
                    public void onSuccess() {
                        commonRepository.updateLotto(new TransactionCallback.OnUpdateCallback() {
                            @Override
                            public void onSuccess() {
                                logger.debug("onSuccess update");
                                commonRepository.select(Lotto.class, new TransactionCallback.OnSelectAllCallback() {
                                    @Override
                                    public void onSuccess(RealmResults results) {
                                        for (int i = 0; i < results.size(); i++) {
                                            logger.debug("" + results.get(i));
                                        }
                                    }
                                });
                                logger.debug("onSuccess");
                                changeStartActivity(mContext, MainNewActivity.class, true, null);
                            }
                        });
                    }
                });
            }
        }
    }
}
