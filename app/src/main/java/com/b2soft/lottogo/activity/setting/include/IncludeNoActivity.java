package com.b2soft.lottogo.activity.setting.include;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.b2soft.lottogo.R;
import com.b2soft.lottogo.activity.setting.exclude.ExcludePresenter;
import com.b2soft.lottogo.activity.setting.exclude.ExcludePresenterImpl;
import com.b2soft.lottogo.adapter.ExcludeGridAdapter;
import com.b2soft.lottogo.adapter.IncludeGridAdapter;
import com.b2soft.lottogo.utils.BBLogger;
import com.b2soft.lottogo.utils.ViewUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IncludeNoActivity extends AppCompatActivity implements IncludePresenter.View {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());
    private IncludePresenter includePresenter;

    Context context;

    /* view */
    @Bind(R.id.includeGirdView) GridView ballGridView;
    @Bind(R.id.includeSelectedClsBtn) Button selectClearBtn;

    @Bind(R.id.adView) AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_include_no);
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
        includePresenter = new IncludePresenterImpl(this);
        includePresenter.setView(this);

        ballGridView.setAdapter(new IncludeGridAdapter(context));

        // AD
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @OnClick(R.id.includeSelectedClsBtn)
    void onClick(View view) {
        ViewUtils.antiOverlapClick(view, 1000);
        switch (view.getId()) {
            case R.id.excludeSelectedClsBtn:
                includePresenter.clearAllSelected((IncludeGridAdapter) ballGridView.getAdapter());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (includePresenter.validateIncludedCount()) {
            super.onBackPressed();
        } else {
            Toast.makeText(context, "5개를 초과할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}