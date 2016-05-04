package com.bbaek.lottogo.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bbaek.lottogo.R;
import com.bbaek.lottogo.activity.main.MainPresenter;
import com.bbaek.lottogo.adapter.SettingListAdapter;
import com.bbaek.lottogo.utils.BBLogger;
import com.bbaek.lottogo.utils.ViewUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class SettingActivity extends AppCompatActivity implements SettingPresenter.View {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());
    private SettingPresenter settingPresenter;

    Context context;
    @Bind(R.id.settingListView) ListView settingListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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
        settingPresenter = new SettingPresenterImpl(this);
        settingPresenter.setView(this);

        settingListView.setAdapter(new SettingListAdapter(context));
    }

    @OnItemClick(R.id.settingListView)
    void onItemClick(View view, int position) {
        ViewUtils.antiOverlapClick(view, 1000);
        logger.debug("click setting index: " + position);
        settingPresenter.openActivity(position);
    }
}
