package com.b2soft.lottogo.activity.setting.history;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.b2soft.lottogo.R;
import com.b2soft.lottogo.activity.MyApplication;
import com.b2soft.lottogo.activity.setting.SettingPresenter;
import com.b2soft.lottogo.activity.setting.SettingPresenterImpl;
import com.b2soft.lottogo.adapter.NewHistoryListAdapter;
import com.b2soft.lottogo.utils.BBLogger;
import com.b2soft.lottogo.utils.ViewUtils;
import com.google.android.gms.ads.AdView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewHistoryActivity extends AppCompatActivity implements NewHistoryPresenter.View {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());
    @Bind(R.id.newHistoryList) ListView listView;
    @Bind(R.id.newHistoryClsBtn) Button clsBtn;
    @Bind(R.id.adView) AdView adView;

    Context context;
    private NewHistoryPresenter presenter;
    NewHistoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_history);
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
        presenter = new NewHistoryPresenterImpl(this);
        presenter.setView(this);

        adapter = new NewHistoryListAdapter(context);
        listView.setAdapter(adapter);
        listView.setSelected(false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                logger.debug("onItemClick: " + position);
                // not run
//                if (adapter.isOpen(position)) {
//                    adapter.closeItem(position);
//                }
            }
        });

        clsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteAllHistory();
                adapter.deleteAllData();
                adapter.notifyDataSetChanged();
            }
        });

        // AD
        adView.loadAd(MyApplication.adRequest);
    }
}
