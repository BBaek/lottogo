package com.b2soft.lottogo.activity.setting.exclude;

import android.app.Activity;

import com.b2soft.lottogo.activity.setting.InExcludeModel;
import com.b2soft.lottogo.adapter.ExcludeGridAdapter;

/**
 * Created by woonsungbaek on 2016. 4. 28..
 */
public class ExcludePresenterImpl implements ExcludePresenter{
    private Activity activity;
    private ExcludePresenter.View view;
    private InExcludeModel inExcludeModel;

    public ExcludePresenterImpl(Activity activity) {
        this.activity = activity;
        this.inExcludeModel = new InExcludeModel(activity.getApplicationContext());
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public boolean validateExcludedCount() {
        if (inExcludeModel.getExcludedSize() < 40) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void clearAllSelected(ExcludeGridAdapter adapter) {
        inExcludeModel.clearAllSelectedExcluded(adapter);
    }
}
