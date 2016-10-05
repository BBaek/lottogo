package com.b2soft.lottogo.activity.setting.history;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.b2soft.lottogo.R;
import com.b2soft.lottogo.ScanResultDialog;
import com.b2soft.lottogo.activity.MyApplication;
import com.b2soft.lottogo.adapter.ResultHistoryGridAdapter;
import com.b2soft.lottogo.model.my.ResultHistoryNo;
import com.b2soft.lottogo.utils.BBLogger;
import com.b2soft.lottogo.utils.ViewUtils;
import com.daimajia.swipe.util.Attributes;
import com.google.android.gms.ads.AdView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultHistoryActivity extends AppCompatActivity {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());
    Context context;

    @Bind(R.id.resultHistoryGridView) GridView gridView;
    @Bind(R.id.adView) AdView adView;

    ScanResultDialog rankDialog;

    ResultHistoryGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_history);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtils.antiOverlapClick(v, 1000);
                onBackPressed();
            }
        });

        context = this;
        adapter = new ResultHistoryGridAdapter(context);
        adapter.setMode(Attributes.Mode.Multiple);
        gridView.setAdapter(adapter);
        gridView.setSelected(false);

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                logger.debug("onItemLongClick: " + position);
                return false;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = ((ResultHistoryNo) adapter.getItem(position)).getKey();
                logger.debug("onItemClick] position:" + position + " / key: " + key);
                rankDialog = new ScanResultDialog(context, (ResultHistoryNo) adapter.getItem(position), new View.OnClickListener() {
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
        });


        gridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                logger.debug("onItemSelected: " + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // AD
        adView.loadAd(MyApplication.adRequest);
    }
}
