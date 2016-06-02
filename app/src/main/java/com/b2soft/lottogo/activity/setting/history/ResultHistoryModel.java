package com.b2soft.lottogo.activity.setting.history;

import android.content.Context;

import com.b2soft.lottogo.Repository.LottoRepository;
import com.b2soft.lottogo.Repository.ResultHistoryRepository;
import com.b2soft.lottogo.Repository.TransactionCallback;
import com.b2soft.lottogo.model.my.ResultHistoryNo;
import com.b2soft.lottogo.utils.BBLogger;

import java.util.ArrayList;
import java.util.List;

import io.realm.Sort;


/**
 * Created by woonsungbaek on 2016. 5. 31..
 */
public class ResultHistoryModel {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());

    private Context context;
    ResultHistoryRepository resultHistoryRepository;

    public ResultHistoryModel(Context context) {
        this.context = context;
        resultHistoryRepository = new ResultHistoryRepository();
    }

    public List<ResultHistoryNo> getAllResultHistory() {
        List<ResultHistoryNo> results = resultHistoryRepository.selectSortedAll("drwNo", Sort.DESCENDING);
        logger.debug("getAllResultHistories: " + results);
        return results;
    }

    public ResultHistoryNo getResultHistory(String key) {
        return resultHistoryRepository.select(key);
    }

    public boolean deleteResultHistory(String key) {
        return resultHistoryRepository.delete(key);
    }

}
