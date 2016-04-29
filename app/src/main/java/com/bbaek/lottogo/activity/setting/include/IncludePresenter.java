package com.bbaek.lottogo.activity.setting.include;

import com.bbaek.lottogo.adapter.ExcludeGridAdapter;
import com.bbaek.lottogo.adapter.IncludeGridAdapter;

/**
 * Created by woonsungbaek on 2016. 4. 28..
 */
public interface IncludePresenter {
    void setView(IncludePresenter.View view);
    boolean validateIncludedCount();
    void clearAllSelected(IncludeGridAdapter adapter);

    public interface View {
    }
}
