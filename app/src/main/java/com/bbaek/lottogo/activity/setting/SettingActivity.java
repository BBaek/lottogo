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

public class SettingActivity extends AppCompatActivity implements SettingPresenter.View {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());
    private SettingPresenter settingPresenter;
    Context context;
    ListView settingListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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

        settingListView = (ListView) findViewById(R.id.settingListView);
        settingListView.setAdapter(new SettingListAdapter(context));
        settingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewUtils.antiOverlapClick(parent, 2000);
                logger.debug("click setting index: " + position);
                settingPresenter.openActivity(position);
            }
        });
    }
}
