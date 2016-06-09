package com.b2soft.lottogo.activity.setting.history;

/**
 * Created by woonsungbaek on 2016. 4. 28..
 */
public interface NewHistoryPresenter {
    void setView(NewHistoryPresenter.View view);
    void deleteAllHistory();

    public interface View {
    }
}
