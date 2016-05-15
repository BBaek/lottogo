package com.b2soft.lottogo.activity.setting.exclude;

import com.b2soft.lottogo.adapter.ExcludeGridAdapter;

/**
 * Created by woonsungbaek on 2016. 4. 28..
 */
public interface ExcludePresenter {
    void setView(ExcludePresenter.View view);
    boolean validateExcludedCount();
    void clearAllSelected(ExcludeGridAdapter adapter);

    public interface View {
    }
}
