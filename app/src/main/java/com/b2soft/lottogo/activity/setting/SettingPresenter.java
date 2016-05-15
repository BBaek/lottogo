package com.b2soft.lottogo.activity.setting;

/**
 * Created by woonsungbaek on 2016. 4. 28..
 */
public interface SettingPresenter {
    void setView(SettingPresenter.View view);
    void openActivity(int position);

    public interface View {
    }
}
