package com.bbaek.lottogo.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.bbaek.lottogo.activity.ActivityModel;
import com.bbaek.lottogo.activity.MyApplication;
import com.bbaek.lottogo.activity.rank.RankListActivity;
import com.bbaek.lottogo.activity.setting.SettingActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Map;

/**
 * Created by woonsungbaek on 2016. 4. 27..
 */
public class MainpresenterImpl implements MainPresenter {
    private Activity activity;
    private MainPresenter.View view;
    private MainModel mainModel;

    public MainpresenterImpl(Activity activity) {
        this.activity = activity;
        this.mainModel = new MainModel(activity.getApplicationContext());
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void showUsingInfo(boolean isShow) {
        if (view != null) {
            view.setVisibleUsingInfo(isShow);
        }
    }

    @Override
    public void drawBalls() {
        drawBalls(null);
    }

    @Override
    public void drawBalls(int pos) {
        drawBalls(mainModel.getHistoryAdapter().getItem(pos));
    }

    @Override
    public void drawBalls(Map<Integer, Integer> balls) {
        if (balls == null) {
            Map<Integer, Integer> _balls = mainModel.getRandBalls();
            view.setBallNumberMetrix(_balls);
            saveHistory(_balls);
        } else {
            view.setBallNumberMetrix(balls);
        }
    }

    @Override
    public void drawRank(Map<Integer, Integer> balls) {
        if (view != null) {
            view.setRankText(mainModel.getEachRankCount(balls));
        }
    }

    @Override
    public void saveHistory(Map<Integer, Integer> balls) {
        mainModel.saveHistory(balls);
        view.setHistoryAdapter(mainModel.getHistoryAdapter());
    }

    @Override
    public void removeHistory(int pos) {
        mainModel.removeHistory(pos);
        view.setHistoryAdapter(mainModel.getHistoryAdapter());
    }

    @Override
    public void openActivityRankResult() {
        mainModel.updateRankResults();
        if (MyApplication.lottoList != null && MyApplication.lottoList.size() > 0) {
            ActivityModel.changeStartActivity(activity, RankListActivity.class, false, null);
        }
    }

    @Override
    public void openActivitySetting() {
        ActivityModel.changeStartActivity(activity, SettingActivity.class, false, null);
    }

    @Override
    public void scanQRcode() {
        IntentIntegrator scanInterIntegrator = new IntentIntegrator(activity);
        scanInterIntegrator.initiateScan();
    }

    @Override
    public void scanQRcodeResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (view != null && scanningResult != null) {
            view.getScanQRcodeResult(scanningResult);
        } else {
            Toast.makeText(activity, "No scan data received!", Toast.LENGTH_SHORT).show();
        }

    }
}
