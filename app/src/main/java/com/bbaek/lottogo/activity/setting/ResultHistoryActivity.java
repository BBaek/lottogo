package com.bbaek.lottogo.activity.setting;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bbaek.lottogo.R;
import com.bbaek.lottogo.utils.BBLogger;
import com.bbaek.lottogo.utils.ViewUtils;

public class ResultHistoryActivity extends AppCompatActivity {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_history);
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

    }
}
