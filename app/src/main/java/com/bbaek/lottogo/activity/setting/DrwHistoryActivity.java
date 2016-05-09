package com.bbaek.lottogo.activity.setting;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.bbaek.lottogo.R;
import com.bbaek.lottogo.adapter.DrwHistoryListAdapter;
import com.bbaek.lottogo.model.Lotto;
import com.bbaek.lottogo.utils.BBLogger;
import com.bbaek.lottogo.utils.ViewUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DrwHistoryActivity extends AppCompatActivity implements DrwHistoryPresenter.View {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());
    private DrwHistoryPresenter drwHistoryPresenter;
    Context context;

    /* View */
    @Bind(R.id.historyListView)
    ListView historyListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
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
        drwHistoryPresenter = new DrwHistoryPresenterImpl(this);
        drwHistoryPresenter.setView(this);
        drwHistoryPresenter.getAllHistorys();
    }

    @Override
    public void setAllDrwHistory(List<Lotto> historys) {
        historyListView.setAdapter(new DrwHistoryListAdapter(context, historys));
    }
}
