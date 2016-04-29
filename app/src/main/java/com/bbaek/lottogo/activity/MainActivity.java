package com.bbaek.lottogo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.bbaek.common.client.lottogo.manager.LottoManager;
import com.bbaek.common.client.lottogo.model.LottoInfo;
import com.bbaek.lottogo.R;
import com.bbaek.lottogo.Repository.CommonRepository;
import com.bbaek.lottogo.Repository.TransactionCallback;
import com.bbaek.lottogo.model.Lotto;
import com.bbaek.lottogo.utils.BBLogger;
import com.bbaek.lottogo.widget.NumberBall;

import java.io.IOException;
import java.io.InputStream;

import io.realm.RealmList;
import io.realm.RealmResults;

//public class MainActivity extends AppCompatActivity {
public class MainActivity extends Activity {

    private BBLogger logger = new BBLogger(getClass().getSimpleName());
    Context mContext;
    ProgressDialog progressDialog;

    TextView drwNo;
    TextView drwDate;
    CheckBox excludeBonusNo;
    TextView totalSum;
    TextView totalLowHighCnt;
    TextView totalOddEvenCnt;
    TextView totalSeq;
    TextView totalLeft;
    TextView totalRight;
    TextView total123;
    TextView total456;

    Spinner spinner;

    long start;
    NumberBall drwtNos[] = new NumberBall[7];

    Lotto lotto;
    CommonRepository commonRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        commonRepository = new CommonRepository();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("데이터를 불러오고 있습니다.\n잠시만 기다려주세요.");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        drwtNos[0] = (NumberBall) findViewById(R.id.ballNo1);
        drwtNos[1] = (NumberBall) findViewById(R.id.ballNo2);
        drwtNos[2] = (NumberBall) findViewById(R.id.ballNo3);
        drwtNos[3] = (NumberBall) findViewById(R.id.ballNo4);
        drwtNos[4] = (NumberBall) findViewById(R.id.ballNo5);
        drwtNos[5] = (NumberBall) findViewById(R.id.ballNo6);
        drwtNos[6] = (NumberBall) findViewById(R.id.ballBunsNo);

        drwNo = (TextView) findViewById(R.id.drwNo);
        drwDate = (TextView) findViewById(R.id.drwDate);
        excludeBonusNo = (CheckBox) findViewById(R.id.excludeBonusNo);
        excludeBonusNo.setChecked(true);
        excludeBonusNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setData(lotto, isChecked);
            }
        });

        totalSum = (TextView) findViewById(R.id.totalSum);
        totalSeq = (TextView) findViewById(R.id.totalSeq);
        totalLowHighCnt = (TextView) findViewById(R.id.lowHigh);
        totalOddEvenCnt = (TextView) findViewById(R.id.oddEven);
        totalLeft = (TextView) findViewById(R.id.totalLeft);
        totalRight = (TextView) findViewById(R.id.totalRight);
        total123 = (TextView) findViewById(R.id.total123);
        total456 = (TextView) findViewById(R.id.total456);

        spinner = (Spinner) findViewById(R.id.spinner);
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item);
        commonRepository.select(Lotto.class, new TransactionCallback.OnSelectAllCallback() {
            @Override
            public void onSuccess(RealmResults results) {
                for (int i = 0; i < results.size(); i++) {
                    spinnerAdapter.add(((Lotto) results.get(i)).getDrwNo() + "");
                }
            }
        });
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final int _drwNo = Integer.parseInt(((TextView) view).getText().toString());
                logger.debug("spinner select: " + _drwNo);
                commonRepository.select(Lotto.class, new TransactionCallback.OnSelectAllCallback() {
                    @Override
                    public void onSuccess(RealmResults results) {
                        if (results != null) {
                            lotto = (Lotto) results.where().equalTo("drwNo", _drwNo).findFirst();
                            setData(lotto, excludeBonusNo.isChecked());
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        progressDialog.show();
        start = System.currentTimeMillis();
        initLottoData();
    }

    private void writeReadedJsonFile() {
        AssetManager assetMgr = getAssets();
        try {
            InputStream is = assetMgr.open("lotto.json");
            commonRepository.insert(Lotto.class, is, new TransactionCallback.OnInsertCallback() {
                @Override
                public void onSuccess() {
                    logger.debug("insert lotto to file(lotto.json)");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initLottoData() {
        commonRepository.select(Lotto.class, new TransactionCallback.OnSelectAllCallback() {
            @Override
            public void onSuccess(RealmResults results) {
                int maxDrwNo = 0;
                if (results != null && results.size() > 0) {
                    maxDrwNo = results.where().max("drwNo").intValue();
                }
                logger.debug("maxDrwNo: " + maxDrwNo);

                if (maxDrwNo <= 0) {
                    // file (2016.3.23 : 694)
                    writeReadedJsonFile();
                    maxDrwNo = results.size();
                    logger.debug("init data > JsonFile [" + maxDrwNo + "]");
                }

                // network
                logger.debug("init data > network");
                new HttpTask().execute(maxDrwNo + 1);
            }
        });
    }

    private void setData(Lotto lotto, boolean isIncludeBonus) {
        lotto.setIncludeBonus(isIncludeBonus);

        drwNo.setText("" + lotto.getDrwNo());
        drwDate.setText("" + lotto.getDrwNoDate());

        drwtNos[0].setValue(lotto.getDrwtNo1());
        drwtNos[1].setValue(lotto.getDrwtNo2());
        drwtNos[2].setValue(lotto.getDrwtNo3());
        drwtNos[3].setValue(lotto.getDrwtNo4());
        drwtNos[4].setValue(lotto.getDrwtNo5());
        drwtNos[5].setValue(lotto.getDrwtNo6());
        drwtNos[6].setValue(lotto.getBnusNo());

        totalSum.setText("" + lotto.getTotalSum());
        totalSeq.setText("" + lotto.getTotalSequentialDigit());
        totalLowHighCnt.setText("저[" + lotto.getTotalCountLowDigit() + "]/고[" + lotto.getTotalCountHighDigit() + "]");
        totalOddEvenCnt.setText("홀[" + lotto.getTotalCountOddDigit() + "]/짝[" + lotto.getTotalCountEvenDigit() + "]");
//        totalOddEvenCnt.setText("홀[" + lotto.get);
        totalLeft.setText("" + lotto.getTotalLeftDigit());
        totalRight.setText("" + lotto.getTotalRightDigit());
        total123.setText("" + lotto.getTotalNo123());
        total456.setText("" + lotto.getTotalNo456());
    }

    class HttpTask extends AsyncTask<Integer, Void, RealmList<Lotto>> {
        @Override
        protected RealmList<Lotto> doInBackground(Integer... params) {
            RealmList<Lotto> result = new RealmList<>();
            LottoManager mgr = new LottoManager();
            int drwNo = params[0];
            try {
                while (true) {
                    logger.debug("requst next DrwNo: " + drwNo);
                    LottoInfo res = (LottoInfo) mgr.getLottoNumber(drwNo);
                    if (res != null) {
                        if (res.getReturnValue().equals("success")) {
                            Lotto lotto = Lotto.newInstance(res);
                            logger.debug(lotto.toString());
                            logger.debug("return value is [" + lotto.getReturnValue() + "]");
                            result.add(lotto);
                        } else {
                            break;
                        }
                    }
                    drwNo++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(final RealmList<Lotto> result) {
            super.onPostExecute(result);
            commonRepository.insert(result, new TransactionCallback.OnInsertCallback() {
                @Override
                public void onSuccess() {
                    commonRepository.select(Lotto.class, new TransactionCallback.OnSelectAllCallback() {
                        @Override
                        public void onSuccess(RealmResults results) {
                            for (int i = 0; i < results.size(); i++) {
                                logger.debug("" + results.get(i));
                            }
                        }
                    });
                    logger.debug("onSuccess");
                    progressDialog.dismiss();
                }
            });
        }
    }
}
