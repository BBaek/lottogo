package com.b2soft.lottogo.activity.setting.history;

import android.app.Activity;

import com.b2soft.lottogo.activity.ActivityModel;
import com.b2soft.lottogo.activity.setting.DrwHistoryActivity;
import com.b2soft.lottogo.activity.setting.SettingPresenter;
import com.b2soft.lottogo.activity.setting.exclude.ExcludeNoActivity;
import com.b2soft.lottogo.activity.setting.include.IncludeNoActivity;

/**
 * Created by woonsungbaek on 2016. 4. 28..
 */
public class NewHistoryPresenterImpl implements NewHistoryPresenter {
    private Activity activity;
    private View view;
    private NewHistoryModel newHistoryModel;

    public NewHistoryPresenterImpl(Activity activity) {
        this.activity = activity;
        this.newHistoryModel = new NewHistoryModel(activity.getApplicationContext());
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void deleteAllHistory() {
        // do not delete marked item
        newHistoryModel.deleteAllNewHistory(false);
    }
}
