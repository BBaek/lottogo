package com.b2soft.lottogo.activity.main;

import android.content.Intent;

import com.b2soft.lottogo.adapter.GenHistoryListAdapter;
import com.github.mikephil.charting.data.BarData;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;
import java.util.Map;

/**
 * Created by woonsungbaek on 2016. 4. 27..
 */
public interface MainPresenter {
    void setView(MainPresenter.View view);
    void setVisibleViews(boolean isShow);
    void drawBalls();
    void drawBalls(int pos);
    void drawBalls(Map<Integer, Integer> balls);
    void drawRank(Map<Integer, Integer> balls);
    void saveHistory(Map<Integer, Integer> balls);
    void removeHistory(int pos);
    void openActivityRankResult();
    void openActivitySetting();
    void scanQRcode();
    void scanQRcodeResult(int requestCode, int resultCode, Intent intent);
    void updateAvgAllDatas(Map<Integer, Integer> balls);

    public interface View {
        void setVisibleUsingInfo(boolean visible);
        void setVisibleAvgResultContainer(boolean visible);
        void setBallNumberMetrix(Map<Integer, Integer> balls);
        void setRankText(String[] text);
        void setHistoryAdapter(GenHistoryListAdapter adapter);
        void getScanQRcodeResult(IntentResult scanningResult);
        void setAvgPickedNumber(BarData data);
        void setAvgAdapterDatas(List<String[]> items);
    }
}
