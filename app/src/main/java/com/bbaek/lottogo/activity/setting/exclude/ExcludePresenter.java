package com.bbaek.lottogo.activity.setting.exclude;

import com.bbaek.lottogo.adapter.ExcludeGridAdapter;
import com.bbaek.lottogo.adapter.HistoryListAdapter;

import java.util.Map;

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
