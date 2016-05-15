package com.b2soft.lottogo.activity.setting;

import android.content.Context;

import com.b2soft.lottogo.Repository.LottoRepository;
import com.b2soft.lottogo.model.Lotto;
import com.b2soft.lottogo.utils.BBLogger;

import java.util.List;

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
