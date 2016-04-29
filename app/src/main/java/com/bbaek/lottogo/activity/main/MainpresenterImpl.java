package com.bbaek.lottogo.activity.main;

import android.app.Activity;

import com.bbaek.lottogo.activity.ActivityModel;
import com.bbaek.lottogo.activity.MyApplication;
import com.bbaek.lottogo.activity.rank.RankListActivity;
import com.bbaek.lottogo.activity.setting.SettingActivity;

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
}
