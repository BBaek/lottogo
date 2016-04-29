package com.bbaek.lottogo.activity.setting.include;

import android.app.Activity;

import com.bbaek.lottogo.activity.setting.InExcludeModel;
import com.bbaek.lottogo.adapter.IncludeGridAdapter;

/**
 * Created by woonsungbaek on 2016. 4. 28..
 */
public class IncludePresenterImpl implements IncludePresenter{
    private Activity activity;
    private IncludePresenter.View view;
    private InExcludeModel inExcludeModel;

    public IncludePresenterImpl(Activity activity) {
        this.activity = activity;
        this.inExcludeModel = new InExcludeModel(activity.getApplicationContext());
    }

    @Override
    public void setView(IncludePresenter.View view) {
        this.view = view;
    }

    @Override
    public boolean validateIncludedCount() {
        if (inExcludeModel.getIncludedSize() <= 5) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void clearAllSelected(IncludeGridAdapter adapter) {
        inExcludeModel.clearAllSelectedIncluded(adapter);
    }
}
