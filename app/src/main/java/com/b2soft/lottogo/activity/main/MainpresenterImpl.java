package com.b2soft.lottogo.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.b2soft.lottogo.LottoAvg;
import com.b2soft.lottogo.LottoUtils;
import com.b2soft.lottogo.activity.ActivityModel;
import com.b2soft.lottogo.activity.MyApplication;
import com.b2soft.lottogo.activity.rank.RankListActivity;
import com.b2soft.lottogo.activity.setting.SettingActivity;
import com.github.mikephil.charting.data.BarData;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    public void setVisibleViews(boolean isShow) {
        if (view != null) {
            view.setVisibleUsingInfo(!isShow);
            view.setVisibleAvgResultContainer(isShow);
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
        view.setAvgAdapterDatas(calAvgs(lottoAvg));
    }

    private List<String[]> calAvgs(LottoAvg lottoAvg) {
        List<String[]> result = new ArrayList<>();
        result.add(calAvgTotalSum(lottoAvg));
        result.add(calAvgTotalSeq(lottoAvg));
//        result.add(calAvgTotalDummy(lottoAvg));
        result.add(calAvgTotalLowHighCnt(lottoAvg));
        result.add(calAvgTotalLeft(lottoAvg));
        result.add(calAvgTotal123(lottoAvg));
        result.add(calAvgTotalOddEvenCnt(lottoAvg));
        result.add(calAvgTotalRight(lottoAvg));
        result.add(calAvgTotal456(lottoAvg));

        return result;
    }

    protected void calAvgPickedNumber(Map<Integer, Integer> balls) {
        List<Integer> ballList = LottoUtils.sortByValue(balls);
        view.setAvgPickedNumber(new BarData(mainModel.getHBallLabel(ballList), mainModel.getHBarDataSet(ballList)));
    }

    protected String[] calAvgTotalSum(LottoAvg lottoAvg) {
        return new String[]{"총합", "" + lottoAvg.getTotalSum()};
    }

    protected String[] calAvgTotalDummy(LottoAvg lottoAvg) {
        return new String[]{"", ""};
    }

    protected String[] calAvgTotalSeq(LottoAvg lottoAvg) {
        return new String[]{"연번", "" + lottoAvg.getTotalSequentialDigit()};
    }

    protected String[] calAvgTotalLowHighCnt(LottoAvg lottoAvg) {
        return new String[]{"고저율", "고[" + lottoAvg.getTotalCountHighDigit() + "]/저[" + lottoAvg.getTotalCountLowDigit() + "]"};
    }

    protected String[] calAvgTotalOddEvenCnt(LottoAvg lottoAvg) {
        return new String[]{"홀짝율", "홀[" + lottoAvg.getTotalCountOddDigit() + "]/짝[" + lottoAvg.getTotalCountEvenDigit() + "]"};
    }

    protected String[] calAvgTotalLeft(LottoAvg lottoAvg) {
        return new String[]{"좌변합", "" + lottoAvg.getTotalLeftDigit()};
    }

    protected String[] calAvgTotalRight(LottoAvg lottoAvg) {
        return new String[]{"우변합", "" + lottoAvg.getTotalRightDigit()};
    }

    protected String[] calAvgTotal123(LottoAvg lottoAvg) {
        return new String[]{"123합", "" + lottoAvg.getTotalNo123()};
    }

    protected String[] calAvgTotal456(LottoAvg lottoAvg) {
        return new String[]{"456합", "" + lottoAvg.getTotalNo456()};
    }
}
