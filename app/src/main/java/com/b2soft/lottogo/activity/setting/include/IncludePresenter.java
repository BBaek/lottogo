package com.b2soft.lottogo.activity.setting.include;

import com.b2soft.lottogo.adapter.ExcludeGridAdapter;
import com.b2soft.lottogo.adapter.IncludeGridAdapter;

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
