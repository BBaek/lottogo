package com.bbaek.lottogo.activity.main;

import android.widget.ListAdapter;
import android.widget.ListView;

import com.bbaek.lottogo.adapter.HistoryListAdapter;

import java.util.Map;

/**
 * Created by woonsungbaek on 2016. 4. 27..
 */
public interface MainPresenter {
    void setView(MainPresenter.View view);
    void showUsingInfo(boolean isShow);
    void drawBalls();
    void drawBalls(int pos);
    void drawBalls(Map<Integer, Integer> balls);
    void drawRank(Map<Integer, Integer> balls);
    void saveHistory(Map<Integer, Integer> balls);
    void removeHistory(int pos);
    void openActivityRankResult();
    void openActivitySetting();

    public interface View {
        void setVisibleUsingInfo(boolean visible);
        void setBallNumberMetrix(Map<Integer, Integer> balls);
        void setRankText(String[] text);
        void setHistoryAdapter(HistoryListAdapter adapter);
    }
}