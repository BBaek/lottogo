package com.bbaek.lottogo.activity.rank;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.bbaek.lottogo.R;
import com.bbaek.lottogo.activity.MyApplication;
import com.bbaek.lottogo.adapter.RankListAdapter;
import com.bbaek.lottogo.utils.ViewUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RankListActivity extends AppCompatActivity {
    Context mContext;
    @Bind(R.id.rankList) ListView listView;
    @Bind(R.id.adView) AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_list);
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

        mContext = this;

        RankListAdapter adapter = new RankListAdapter(mContext, MyApplication.lottoList);
        listView.setAdapter(adapter);

        // AD
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
