package com.b2soft.lottogo.activity.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.b2soft.lottogo.R;
import com.b2soft.lottogo.activity.MyApplication;
import com.b2soft.lottogo.adapter.DrwHistoryListAdapter;
import com.b2soft.lottogo.model.Lotto;
import com.b2soft.lottogo.utils.BBLogger;
import com.b2soft.lottogo.utils.ViewUtils;
import com.google.android.gms.ads.AdView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DrwHistoryActivity extends AppCompatActivity implements DrwHistoryPresenter.View {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());
    private DrwHistoryPresenter drwHistoryPresenter;
    Context context;

    /* View */
    @Bind(R.id.historyListView) ListView historyListView;
    @Bind(R.id.adView) AdView adView;

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

        // AD
        adView.loadAd(MyApplication.adRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drwhistory_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_find) {
            logger.debug("find");
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("찾기");
            alert.setMessage("회차를 입력해주세요.");

            // Set an EditText view to get user input
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            alert.setView(input);

            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    int pos = 0;
                    String value = input.getText().toString();
                    if (!value.isEmpty() ) {
                        int v = Integer.parseInt(value);
                        if (v >= historyListView.getAdapter().getCount()) {
                            pos = historyListView.getAdapter().getCount() - 1;
                        } else {
                            pos = v - 1;
                        }
                    }
                    historyListView.setSelection(pos);
                }
            });


            alert.setNegativeButton("취소",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });
            alert.show();
        } else if(id == R.id.action_gotop) {
            logger.debug("gotop");
            historyListView.setSelection(0);
        } else if(id == R.id.action_golast) {
            logger.debug("golast");
            historyListView.setSelection(historyListView.getAdapter().getCount() - 1);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setAllDrwHistory(List<Lotto> historys) {
        historyListView.setAdapter(new DrwHistoryListAdapter(context, historys));
    }
}
