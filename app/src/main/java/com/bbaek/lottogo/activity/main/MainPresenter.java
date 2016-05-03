package com.bbaek.lottogo.activity.main;

import android.content.Intent;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.bbaek.lottogo.adapter.HistoryListAdapter;
import com.github.mikephil.charting.data.BarData;
import com.google.zxing.integration.android.IntentResult;

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
    void scanQRcode();
    void scanQRcodeResult(int requestCode, int resultCode, Intent intent);
    void updateAvgAllDatas(Map<Integer, Integer> balls);

    public interface View {
        void setVisibleUsingInfo(boolean visible);
        void setBallNumberMetrix(Map<Integer, Integer> balls);
        void setRankText(String[] text);
        void setHistoryAdapter(HistoryListAdapter adapter);
        void getScanQRcodeResult(IntentResult scanningResult);
        void setAvgPickedNumber(BarData data);
        void setAvgTotalSum(String value);
        void setAvgTotalSeq(String value);
        void setAvgTotalLowHighCnt(String value);
        void setAvgTotalOddEvenCnt(String value);
        void setAvgTotalLeft(String value);
        void setAvgTotalRight(String value);
        void setAvgTotal123(String value);
        void setAvgTotal456(String value);
    }
}
