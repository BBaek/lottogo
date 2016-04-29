package com.bbaek.lottogo.activity.setting;

import com.bbaek.lottogo.adapter.HistoryListAdapter;

import java.util.Map;

/**
 * Created by woonsungbaek on 2016. 4. 28..
 */
public interface SettingPresenter {
    void setView(SettingPresenter.View view);
    void openActivity(int position);

    public interface View {
    }
}
