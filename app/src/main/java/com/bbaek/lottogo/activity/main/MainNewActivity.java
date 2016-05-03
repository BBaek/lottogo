package com.bbaek.lottogo.activity.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
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
import com.google.zxing.integration.android.IntentResult;

import java.util.Iterator;
import java.util.Map;

public class MainNewActivity extends Activity implements MainPresenter.View {

    private BBLogger logger = new BBLogger(getClass().getSimpleName());
    private MainPresenter mainPresenter;

    Context context;
    ProgressDialog progressDialog;

    /* view */
    NumberBallMetrix ballNumberMetrix;
    RelativeLayout mainGo;
    ImageView btnSetting;
    ImageView btnScan;
    // history listview
    ListView historyListView;
    // rank view
    LinearLayout analRankContainer;
    TextView[] analRanks;

    ScanResultDialog rankDialog;
    WebView adWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        context = this;
        mainPresenter = new MainpresenterImpl(this);
        mainPresenter.setView(this);

        analRanks = new TextView[5];
        analRanks[0] = (TextView) findViewById(R.id.rank1);
        analRanks[1] = (TextView) findViewById(R.id.rank2);
        analRanks[2] = (TextView) findViewById(R.id.rank3);
        analRanks[3] = (TextView) findViewById(R.id.rank4);
        analRanks[4] = (TextView) findViewById(R.id.rank5);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("번호를 찾고 있습니다.\n잠시만 기다려주세요.");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        ballNumberMetrix = (NumberBallMetrix) findViewById(R.id.ballNumberMetrix);
        mainGo = (RelativeLayout) findViewById(R.id.mainGo);
        mainGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtils.antiOverlapClick(v, 1000);
                if(!progressDialog.isShowing()) progressDialog.show();
                mainPresenter.showUsingInfo(false);
                mainPresenter.drawBalls();
            }
        });

        btnSetting = (ImageView) findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtils.antiOverlapClick(v, 1000);
                mainPresenter.openActivitySetting();
            }
        });

        btnScan = (ImageView) findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtils.antiOverlapClick(v, 1000);
                mainPresenter.scanQRcode();
//                "http://qr.nlotto.co.kr/?v=0700m082532353845q121722354144q091724253342q070809192241q1013182324391673179286"
//                rankDialog = new ScanResultDialog(context, "http://qr.nlotto.co.kr/?v=0700m112328293044q112328293013q112328293045q112328293334q1123283132331673179286", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ViewUtils.antiOverlapClick(v, 1000);
//                        if (rankDialog.isShowing()) {
//                            rankDialog.dismiss();
//                        }
//                    }
//                });
//                rankDialog.show();
            }
        });

        historyListView = (ListView) findViewById(R.id.historyList);
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewUtils.antiOverlapClick(view, 1000);
                logger.debug("click(select) history index: " + position);
                mainPresenter.drawBalls(position);
            }
        });
        historyListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ViewUtils.antiOverlapClick(view, 1000);
                logger.debug("long click(delete) history index: " + position);
                mainPresenter.removeHistory(position);
                return false;
            }
        });

        analRankContainer = (LinearLayout) findViewById(R.id.analRankContainer);
        analRankContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtils.antiOverlapClick(v, 1000);
                mainPresenter.openActivityRankResult();
            }
        });

        adWebView = (WebView) findViewById(R.id.adWebView);
        adWebView.getSettings().setJavaScriptEnabled(true); // enabled javaScript in WebView
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
        if(historyListView.getAdapter() == null) {
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
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        mainPresenter.scanQRcodeResult(requestCode, resultCode, intent);
    }
}
