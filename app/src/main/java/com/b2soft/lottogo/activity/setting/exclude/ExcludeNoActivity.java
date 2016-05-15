package com.b2soft.lottogo.activity.setting.exclude;

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
import com.b2soft.lottogo.activity.MyApplication;
import com.b2soft.lottogo.activity.main.MainPresenter;
import com.b2soft.lottogo.adapter.ExcludeGridAdapter;
import com.b2soft.lottogo.utils.BBLogger;
import com.b2soft.lottogo.utils.ViewUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExcludeNoActivity extends AppCompatActivity implements ExcludePresenter.View {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());
    private ExcludePresenter excludePresenter;

    Context context;

    /* view */
    @Bind(R.id.excludeGirdView) GridView ballGridView;
    @Bind(R.id.excludeSelectedClsBtn) Button selectClearBtn;

    @Bind(R.id.adView) AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exclude_no);
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
        excludePresenter = new ExcludePresenterImpl(this);
        excludePresenter.setView(this);
        ballGridView.setAdapter(new ExcludeGridAdapter(context));

        // AD
        adView.loadAd(MyApplication.adRequest);
    }

    @OnClick(R.id.excludeSelectedClsBtn)
    void onClick(View view) {
        ViewUtils.antiOverlapClick(view, 1000);
        switch (view.getId()) {
            case R.id.excludeSelectedClsBtn:
                excludePresenter.clearAllSelected((ExcludeGridAdapter) ballGridView.getAdapter());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (excludePresenter.validateExcludedCount()) {
            super.onBackPressed();
        } else {
            Toast.makeText(context, "최소 6개는 선택되지 않아야 합니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
