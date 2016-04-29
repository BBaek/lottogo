package com.bbaek.lottogo.activity.setting.exclude;

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

import com.bbaek.lottogo.R;
import com.bbaek.lottogo.activity.main.MainPresenter;
import com.bbaek.lottogo.adapter.ExcludeGridAdapter;
import com.bbaek.lottogo.utils.BBLogger;
import com.bbaek.lottogo.utils.ViewUtils;

public class ExcludeNoActivity extends AppCompatActivity implements ExcludePresenter.View {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());
    private ExcludePresenter excludePresenter;

    Context context;

    /* view */
    GridView ballGridView;
    Button selectClearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exclude_no);
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

        ballGridView = (GridView) findViewById(R.id.excludeGirdView);
        ballGridView.setAdapter(new ExcludeGridAdapter(context));

        selectClearBtn = (Button) findViewById(R.id.excludeSelectedClsBtn);
        selectClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excludePresenter.clearAllSelected((ExcludeGridAdapter) ballGridView.getAdapter());
            }
        });
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
