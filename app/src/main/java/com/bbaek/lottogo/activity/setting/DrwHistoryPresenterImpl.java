package com.bbaek.lottogo.activity.setting;

import android.app.Activity;

/**
 * Created by woonsungbaek on 2016. 5. 9..
 */
public class DrwHistoryPresenterImpl implements DrwHistoryPresenter {
    private Activity activity;
    private DrwHistoryPresenter.View view;
    private DrwHistoryModel drwHistoryModel;

    public DrwHistoryPresenterImpl(Activity activity) {
        this.activity = activity;
        this.drwHistoryModel = new DrwHistoryModel(activity.getApplicationContext());
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void getAllHistorys() {
        view.setAllDrwHistory(drwHistoryModel.getAllDrwLottoList());
    }
}
