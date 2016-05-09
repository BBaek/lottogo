package com.bbaek.lottogo.activity.setting;

import android.content.Context;

import com.bbaek.lottogo.Repository.LottoRepository;
import com.bbaek.lottogo.model.Lotto;
import com.bbaek.lottogo.utils.BBLogger;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by woonsungbaek on 2016. 5. 9..
 */
public class DrwHistoryModel {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());

    private Context context;
    LottoRepository lottoRepository;

    public DrwHistoryModel(Context context) {
        this.context = context;
        lottoRepository = new LottoRepository();
    }

    public List<Lotto> getAllDrwLottoList() {
        return lottoRepository.selectAll();
    }
}
