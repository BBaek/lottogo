package com.b2soft.lottogo.activity.setting;

import com.b2soft.lottogo.model.Lotto;

import java.util.List;

/**
 * Created by woonsungbaek on 2016. 5. 9..
 */
public interface DrwHistoryPresenter {
    void setView(DrwHistoryPresenter.View view);
    void getAllHistorys();

    public interface View {
        void setAllDrwHistory(List<Lotto> historys);
    }
}
