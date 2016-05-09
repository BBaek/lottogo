package com.bbaek.lottogo.activity.setting;

import android.app.Activity;

import com.bbaek.lottogo.activity.ActivityModel;
import com.bbaek.lottogo.activity.setting.exclude.ExcludeNoActivity;
import com.bbaek.lottogo.activity.setting.history.NewHistoryActivity;
import com.bbaek.lottogo.activity.setting.history.ResultHistoryActivity;
import com.bbaek.lottogo.activity.setting.include.IncludeNoActivity;

/**
 * Created by woonsungbaek on 2016. 4. 28..
 */
public class SettingPresenterImpl implements SettingPresenter {
    private Activity activity;
    private SettingPresenter.View view;
//    private MainModel mainModel;

    public SettingPresenterImpl(Activity activity) {
        this.activity = activity;
//        this.mainModel = new MainModel(activity.getApplicationContext());
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void openActivity(int position) {
        switch (position) {
            case 0:
                ActivityModel.changeStartActivity(activity, DrwHistoryActivity.class, false, null);
                break;
            case 1:
                ActivityModel.changeStartActivity(activity, IncludeNoActivity.class, false, null);
                break;
            case 2:
                ActivityModel.changeStartActivity(activity, ExcludeNoActivity.class, false, null);
                break;
            case 3:
                ActivityModel.changeStartActivity(activity, NewHistoryActivity.class, false, null);
                break;
            case 4:
                ActivityModel.changeStartActivity(activity, ResultHistoryActivity.class, false, null);
                break;
        }
    }
}
