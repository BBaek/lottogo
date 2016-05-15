package com.b2soft.lottogo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.b2soft.lottogo.Repository.CommonRepository;
import com.b2soft.lottogo.Repository.ResultHistoryRepository;
import com.b2soft.lottogo.Repository.TransactionCallback;
import com.b2soft.lottogo.adapter.ScanResultListAdapter;
import com.b2soft.lottogo.model.Lotto;
import com.b2soft.lottogo.model.my.DrwtNos;
import com.b2soft.lottogo.model.my.ResultHistoryNo;
import com.b2soft.lottogo.utils.BBLogger;
import com.b2soft.lottogo.widget.NumberBall;

import io.realm.RealmObject;

/**
 * Created by woonsungbaek on 2016. 5. 2..
 */
public class ScanResultDialog extends Dialog {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());
    private Context context;
    /* header */
    NumberBall drwtNo1;
    NumberBall drwtNo2;
    NumberBall drwtNo3;
    NumberBall drwtNo4;
    NumberBall drwtNo5;
    NumberBall drwtNo6;
    NumberBall drwtBunsNo;
    TextView drwNoDate;
    TextView drwNo;
    TextView rank;
    TextView rankLabel;

    /* body */
    private ListView scanResultList;

    private Button btnClose;
    private View.OnClickListener onClickListener;

    private Lotto lotto;
    private String scanValue;
    private ResultHistoryNo datas;
    ResultHistoryRepository resultRepository = new ResultHistoryRepository();
    CommonRepository lottoRepository = new CommonRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        // 안먹힘
//        getWindow().setGravity(Gravity.CENTER);
        setContentView(R.layout.layout_scan_result_dialog);
        setLayout();
        this.datas = new LottoQRParser(scanValue).getResultHistoryNo();
        if (this.datas != null) {
            this.lotto = getLottoNos();
            if (this.lotto != null) {
                setViewLottoNos();
                getRank();

                resultRepository.insert(datas, new TransactionCallback.OnInsertCallback() {
                    @Override
                    public void onSuccess() {
                        logger.debug("onSuccess insert scan result history");
                        ScanResultListAdapter adapter = new ScanResultListAdapter(context, lotto, datas.getDrwtNoList());
                        scanResultList.setAdapter(adapter);
                    }
                });
            } else {
                Toast.makeText(context, "아직 추첨이 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        } else {
            Toast.makeText(context, "바코드를 확인해 주세요.", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }
    Lotto getLottoNos() {
        RealmObject result = lottoRepository.selectLotto(datas.getDrwNo());
        if (result != null) {
            return (Lotto) result;
        }
        return null;
    }

    void setViewLottoNos() {
        // 스캔한 회차번호(datas.getDrwNo())로 기존 lotto db에서 결과를 검색
        drwNo.setText("" + lotto.getDrwNo());
        drwNoDate.setText("" + lotto.getDrwNoDate());
        drwtNo1.setValue(lotto.getDrwtNo1());
        drwtNo2.setValue(lotto.getDrwtNo2());
        drwtNo3.setValue(lotto.getDrwtNo3());
        drwtNo4.setValue(lotto.getDrwtNo4());
        drwtNo5.setValue(lotto.getDrwtNo5());
        drwtNo6.setValue(lotto.getDrwtNo6());
        drwtBunsNo.setValue(lotto.getBnusNo());
        datas.setAnnoDate(lotto.getDrwNoDate());
    }

    void getRank() {
        for(int i = 0; i < datas.getDrwtNoList().size(); i++) {
            DrwtNos scanResult = datas.getDrwtNoList().get(i);
            scanResult.setRank(LottoUtils.compareRank(lotto, scanResult));
        }
    }

    public ScanResultDialog(Context context, String scanValue, View.OnClickListener onClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.onClickListener = onClickListener;
        this.scanValue = scanValue;
    }

    void setLayout() {
        scanResultList = (ListView) findViewById(R.id.scanResultList);
        rank = (TextView) findViewById(R.id.rankList);
        rank.setVisibility(View.INVISIBLE);
        rankLabel = (TextView) findViewById(R.id.rankLabel);
        rankLabel.setVisibility(View.INVISIBLE);
        drwNo = (TextView) findViewById(R.id.drwNoList);
        drwNoDate = (TextView) findViewById(R.id.drwDateList);
        drwtNo1 = (NumberBall) findViewById(R.id.drwtNo1List);
        drwtNo2 = (NumberBall) findViewById(R.id.drwtNo2List);
        drwtNo3 = (NumberBall) findViewById(R.id.drwtNo3List);
        drwtNo4 = (NumberBall) findViewById(R.id.drwtNo4List);
        drwtNo5 = (NumberBall) findViewById(R.id.drwtNo5List);
        drwtNo6 = (NumberBall) findViewById(R.id.drwtNo6List);
        drwtBunsNo = (NumberBall) findViewById(R.id.drwtBunsNoList);

        btnClose = (Button) findViewById(R.id.scanResultOkBtn);
        btnClose.setOnClickListener(onClickListener);
    }
}
