package com.b2soft.lottogo.activity.setting.history;

import android.content.Context;

import com.b2soft.lottogo.Repository.NewHistoryRepository;
import com.b2soft.lottogo.Repository.ResultHistoryRepository;
import com.b2soft.lottogo.model.my.NewHistoryNo;
import com.b2soft.lottogo.model.my.ResultHistoryNo;
import com.b2soft.lottogo.utils.BBLogger;

import java.util.ArrayList;
import java.util.List;

import io.realm.Sort;


/**
 * Created by woonsungbaek on 2016. 5. 31..
 */
public class NewHistoryModel {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());

    private Context context;
    NewHistoryRepository newHistoryRepository;

    public NewHistoryModel(Context context) {
        this.context = context;
        newHistoryRepository = new NewHistoryRepository();
    }

//    public List<NewHistoryNo> getAllNewHistory() {
//        List<NewHistoryNo> results = newHistoryRepository.selectSortedAll("id", Sort.DESCENDING);
//        logger.debug("get All New Histories size: " + results.size());
////        logger.debug("get All New Histories: " + results);
//        return results;
//    }

    public List<NewHistoryNo> getAllNewHistory() {
        List<NewHistoryNo> results = new ArrayList<>();
        List<NewHistoryNo> mark = getAllMarkedNewHistory(true);
        List<NewHistoryNo> unmark = getAllMarkedNewHistory(false);
        results.addAll(mark);
        results.addAll(unmark);
        logger.debug("get All New Histories size: " + results.size());
        return results;
    }

    protected List<NewHistoryNo> getAllMarkedNewHistory(boolean isMarked) {
        List<NewHistoryNo> results = newHistoryRepository.selectAllMarked(isMarked);
        logger.debug("getAllMarkedNewHistory: " + isMarked + ": " + results.size());
        return results;
    }

    public NewHistoryNo getNewHistory(int id) {
        return newHistoryRepository.select(id);
    }

    public boolean deleteNewHistory(NewHistoryNo data) {
        return newHistoryRepository.delete(data.getId());
    }

    public boolean deleteAllNewHistory(boolean includedMarked) {
        if (includedMarked) {
            return newHistoryRepository.deleteAll();
        }
        return newHistoryRepository.deleteAllNotMarked();
    }

    public void toggleMark(NewHistoryNo data) {
        newHistoryRepository.updateBookmark(data.getId(), !data.isBookmark());
    }
}
