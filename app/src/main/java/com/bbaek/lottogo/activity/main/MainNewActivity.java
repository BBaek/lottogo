package com.bbaek.lottogo.activity.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bbaek.lottogo.LottoQRParser;
import com.bbaek.lottogo.ScanResultDialog;
import com.bbaek.lottogo.adapter.HistoryListAdapter;
import com.bbaek.lottogo.R;
import com.bbaek.lottogo.utils.BBLogger;
import com.bbaek.lottogo.utils.ViewUtils;
import com.bbaek.lottogo.widget.NumberBallMetrix;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class MainNewActivity extends Activity implements MainPresenter.View {

    private BBLogger logger = new BBLogger(getClass().getSimpleName());
    private MainPresenter mainPresenter;

    Context context;
    ProgressDialog progressDialog;

    /* view */
    @Bind(R.id.ballNumberMetrix) NumberBallMetrix ballNumberMetrix;
    @Bind(R.id.mainGo) RelativeLayout mainGo;
    @Bind(R.id.btnSetting) ImageView btnSetting;
    @Bind(R.id.btnScan) ImageView btnScan;
    // history listview
    @Bind(R.id.historyList) ListView historyListView;
    // rank view
    @Bind(R.id.analRankContainer) LinearLayout analRankContainer;
    @Bind({R.id.rank1, R.id.rank2, R.id.rank3, R.id.rank4, R.id.rank5}) TextView[] analRanks;

    // scan
    ScanResultDialog rankDialog;
    @Bind(R.id.adWebView) WebView adWebView;

    // avg
    CheckBox excludeBonusNo; // include bounsNo
    @Bind(R.id.chart) HorizontalBarChart chart;
    @Bind(R.id.totalSum) TextView totalSum;
    @Bind(R.id.totalSeq) TextView totalSeq;
    @Bind(R.id.lowHigh) TextView totalLowHighCnt;
    @Bind(R.id.oddEven) TextView totalOddEvenCnt;
    @Bind(R.id.totalLeft) TextView totalLeft;
    @Bind(R.id.totalRight) TextView totalRight;
    @Bind(R.id.total123) TextView total123;
    @Bind(R.id.total456) TextView total456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        ButterKnife.bind(this);
        context = this;
        mainPresenter = new MainpresenterImpl(this);
        mainPresenter.setView(this);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("번호를 찾고 있습니다.\n잠시만 기다려주세요.");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        adWebView.getSettings().setJavaScriptEnabled(true); // enabled javaScript in WebView
    }

    @OnClick({R.id.mainGo, R.id.btnSetting, R.id.btnScan, R.id.analRankContainer})
    protected void onClickView(View view) {
        ViewUtils.antiOverlapClick(view, 1000);
        switch (view.getId()) {
            case R.id.mainGo:
                genRandomBalls();
                break;
            case R.id.btnSetting:
                mainPresenter.openActivitySetting();
                break;
            case R.id.btnScan:
                mainPresenter.scanQRcode();
                /* testcode */
                // 11.23.28.29.30.44 = 13
//                String msg = "http://qr.nlotto.co.kr/?v=" +
//                        "0700m" +
//                        "112328293044q" + // 1st
//                        "111323304445q" + // 2nd
//                        "071123282930q" + // 3rd
//                        "011129304445q" + // 4th
//                        "010211132844" + // 5th
//                        "1673179286";
//                String msg = "http://qr.nlotto.co.kr/?v=" +
//                        "0700m" +
//                        "082532353845q" +
//                        "121722354144q" +
//                        "091724253342q" +
//                        "070809192241q" +
//                        "101318232439" +
//                        "1673179286";
//                rankDialog = new ScanResultDialog(context, msg, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ViewUtils.antiOverlapClick(v, 1000);
//                        if (rankDialog.isShowing()) {
//                            rankDialog.dismiss();
//                        }
//                    }
//                });
//                rankDialog.show();
                break;
            case R.id.analRankContainer:
                mainPresenter.openActivityRankResult();
                break;
        }
    }

    @OnItemClick(R.id.historyList)
    void drawBalls(View view, int position) {
        ViewUtils.antiOverlapClick(view, 1000);
        logger.debug("click(select) history index: " + position);
        mainPresenter.drawBalls(position);
    }

    @OnItemLongClick(R.id.historyList)
    boolean removeHistory(View view, int position) {
        ViewUtils.antiOverlapClick(view, 1000);
        logger.debug("long click(delete) history index: " + position);
        mainPresenter.removeHistory(position);
        return false;
    }

    private void genRandomBalls() {
        if (!progressDialog.isShowing()) progressDialog.show();
        mainPresenter.showUsingInfo(false);
        mainPresenter.drawBalls();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setVisibleUsingInfo(boolean visible) {
        TextView info = (TextView) findViewById(R.id.textInfo);
        if (visible) {
            info.setVisibility(View.VISIBLE);
        } else {
            info.setVisibility(View.GONE);
        }
    }

    @Override
    public void setBallNumberMetrix(Map<Integer, Integer> balls) {
        ballNumberMetrix.initBall();
        Iterator keys = balls.keySet().iterator();
        while (keys.hasNext()) {
            int key = (int) keys.next();
            ballNumberMetrix.setBall(key, balls.get(key));
        }

        mainPresenter.drawRank(balls);
    }

    @Override
    public void setRankText(String[] text) {
        analRanks[0].setText(text[0]);
        analRanks[1].setText(text[1]);
        analRanks[2].setText(text[2]);
        analRanks[3].setText(text[3]);
        analRanks[4].setText(text[4]);

        progressDialog.dismiss();
    }

    @Override
    public void setHistoryAdapter(HistoryListAdapter adapter) {
        if (historyListView.getAdapter() == null) {
            historyListView.setAdapter(adapter);
        }
    }

    @Override
    public void getScanQRcodeResult(IntentResult scanningResult) {
        logger.debug("formatName: " + scanningResult.getFormatName());
        logger.debug("contents: " + scanningResult.getContents());
        if (scanningResult.getContents() != null) {
            rankDialog = new ScanResultDialog(context, scanningResult.getContents(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewUtils.antiOverlapClick(v, 1000);
                    if (rankDialog.isShowing()) {
                        rankDialog.dismiss();
                    }
                }
            });
            rankDialog.show();
        }
    }

    @Override
    public void setAvgPickedNumber(BarData data) {
        chart.setData(data);
        chart.setDescription("");
        chart.animateY(1000);
        chart.invalidate();
    }

    @Override
    public void setAvgTotalSum(String value) {
        totalSum.setText(value);
    }

    @Override
    public void setAvgTotalSeq(String value) {
        totalSeq.setText(value);
    }

    @Override
    public void setAvgTotalLowHighCnt(String value) {
        totalLowHighCnt.setText(value);
    }

    @Override
    public void setAvgTotalOddEvenCnt(String value) {
        totalOddEvenCnt.setText(value);
    }

    @Override
    public void setAvgTotalLeft(String value) {
        totalLeft.setText(value);
    }

    @Override
    public void setAvgTotalRight(String value) {
        totalRight.setText(value);
    }

    @Override
    public void setAvgTotal123(String value) {
        total123.setText(value);
    }

    @Override
    public void setAvgTotal456(String value) {
        total456.setText(value);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        mainPresenter.scanQRcodeResult(requestCode, resultCode, intent);
    }
}
