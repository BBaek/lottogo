package com.bbaek.lottogo.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.bbaek.lottogo.LottoAvg;
import com.bbaek.lottogo.LottoUtils;
import com.bbaek.lottogo.activity.ActivityModel;
import com.bbaek.lottogo.activity.MyApplication;
import com.bbaek.lottogo.activity.rank.RankListActivity;
import com.bbaek.lottogo.activity.setting.SettingActivity;
import com.github.mikephil.charting.data.BarData;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;
import java.util.Map;

/**
 * Created by woonsungbaek on 2016. 4. 27..
 */
public class MainpresenterImpl implements MainPresenter {
    private Activity activity;
    private MainPresenter.View view;
    private MainModel mainModel;

    public MainpresenterImpl(Activity activity) {
        this.activity = activity;
        this.mainModel = new MainModel(activity.getApplicationContext());
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void showUsingInfo(boolean isShow) {
        if (view != null) {
            view.setVisibleUsingInfo(isShow);
        }
    }

    @Override
    public void drawBalls() {
        drawBalls(null);
    }

    @Override
    public void drawBalls(int pos) {
        drawBalls(mainModel.getGenHistoryAdapter().getItem(pos));
    }

    @Override
    public void drawBalls(Map<Integer, Integer> balls) {
        Map<Integer, Integer> _balls = balls;
        if (_balls == null) {
            _balls = mainModel.getRandBalls();
            saveHistory(_balls);
        }
        view.setBallNumberMetrix(_balls);
        updateAvgAllDatas(_balls);
    }

    @Override
    public void drawRank(Map<Integer, Integer> balls) {
        if (view != null) {
            view.setRankText(mainModel.getEachRankCount(balls));
        }
    }

    @Override
    public void saveHistory(Map<Integer, Integer> balls) {
        mainModel.saveHistory(balls);
        view.setHistoryAdapter(mainModel.getGenHistoryAdapter());
    }

    @Override
    public void removeHistory(int pos) {
        mainModel.removeHistory(pos);
        view.setHistoryAdapter(mainModel.getGenHistoryAdapter());
    }

    @Override
    public void openActivityRankResult() {
        mainModel.updateRankResults();
        if (MyApplication.lottoList != null && MyApplication.lottoList.size() > 0) {
            ActivityModel.changeStartActivity(activity, RankListActivity.class, false, null);
        }
    }

    @Override
    public void openActivitySetting() {
        ActivityModel.changeStartActivity(activity, SettingActivity.class, false, null);
    }

    @Override
    public void scanQRcode() {
        IntentIntegrator scanInterIntegrator = new IntentIntegrator(activity);
        scanInterIntegrator.initiateScan();
    }

    @Override
    public void scanQRcodeResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (view != null && scanningResult != null) {
            view.getScanQRcodeResult(scanningResult);
        } else {
            Toast.makeText(activity, "No scan data received!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void updateAvgAllDatas(Map<Integer, Integer> balls) {
        calAvgPickedNumber(balls);
        LottoAvg lottoAvg = new LottoAvg(balls);
        calAvgTotalSum(lottoAvg);
        calAvgTotalSeq(lottoAvg);
        calAvgTotalLowHighCnt(lottoAvg);
        calAvgTotalOddEvenCnt(lottoAvg);
        calAvgTotalLeft(lottoAvg);
        calAvgTotalRight(lottoAvg);
        calAvgTotal123(lottoAvg);
        calAvgTotal456(lottoAvg);
    }

    protected void calAvgPickedNumber(Map<Integer, Integer> balls) {
        List<Integer> ballList = LottoUtils.sortByValue(balls);
        view.setAvgPickedNumber(new BarData(mainModel.getHBallLabel(ballList), mainModel.getHBarDataSet(ballList)));
    }

    protected void calAvgTotalSum(LottoAvg lottoAvg) {
        view.setAvgTotalSum("" + lottoAvg.getTotalSum());
    }

    protected void calAvgTotalSeq(LottoAvg lottoAvg) {
        view.setAvgTotalSeq("" + lottoAvg.getTotalSequentialDigit());
    }

    protected void calAvgTotalLowHighCnt(LottoAvg lottoAvg) {
        view.setAvgTotalLowHighCnt("저[" + lottoAvg.getTotalCountLowDigit() + "]/고[" + lottoAvg.getTotalCountHighDigit() + "]");
    }

    protected void calAvgTotalOddEvenCnt(LottoAvg lottoAvg) {
        view.setAvgTotalOddEvenCnt("홀[" + lottoAvg.getTotalCountOddDigit() + "]/짝[" + lottoAvg.getTotalCountEvenDigit() + "]");
    }

    protected void calAvgTotalLeft(LottoAvg lottoAvg) {
        view.setAvgTotalLeft("" + lottoAvg.getTotalLeftDigit());
    }

    protected void calAvgTotalRight(LottoAvg lottoAvg) {
        view.setAvgTotalRight("" + lottoAvg.getTotalRightDigit());
    }

    protected void calAvgTotal123(LottoAvg lottoAvg) {
        view.setAvgTotal123("" + lottoAvg.getTotalNo123());
    }

    protected void calAvgTotal456(LottoAvg lottoAvg) {
        view.setAvgTotal456("" + lottoAvg.getTotalNo456());
    }
}
