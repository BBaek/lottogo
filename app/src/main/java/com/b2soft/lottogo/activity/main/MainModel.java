package com.b2soft.lottogo.activity.main;

import android.content.Context;

import com.b2soft.lottogo.LottoUtils;
import com.b2soft.lottogo.Repository.NewHistoryRepository;
import com.b2soft.lottogo.Repository.InExcludeRepository;
import com.b2soft.lottogo.Repository.LottoRepository;
import com.b2soft.lottogo.Repository.TransactionCallback;
import com.b2soft.lottogo.activity.MyApplication;
import com.b2soft.lottogo.adapter.GenHistoryListAdapter;
import com.b2soft.lottogo.model.my.DrwtNos;
import com.b2soft.lottogo.model.my.InExcludeNo;
import com.b2soft.lottogo.model.my.NewHistoryNo;
import com.b2soft.lottogo.utils.BBLogger;
import com.b2soft.lottogo.widget.NumberBallMetrix;
import com.bbaek.common.utils.DateUtils;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.realm.RealmResults;

/**
 * Created by woonsungbaek on 2016. 4. 27..
 */
public class MainModel {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());

    private Context context;
    private LottoUtils lottoUtils;
    GenHistoryListAdapter genHistoryAdapter;
    InExcludeRepository inExcludeRepository;
    NewHistoryRepository newHistoryRepository;

    public MainModel(Context context) {
        this.context = context;
        lottoUtils = new LottoUtils(context);
        genHistoryAdapter = new GenHistoryListAdapter(context, new ArrayList<Map<Integer, Integer>>());
        inExcludeRepository = new InExcludeRepository();
        newHistoryRepository = new NewHistoryRepository();
    }

    public Map<Integer, Integer> getRandBalls() {
//        try {
//            Thread.sleep(new Random().nextInt(5) * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        final int BALL_SIZE = 6;
        Map<Integer, Integer> balls = new HashMap<>();
        Random random = new Random();
        RealmResults realmResults = inExcludeRepository.selectAllInExcludeNos();
        RealmResults realmIncludeResults = realmResults.where().equalTo("include", true).findAll();
        RealmResults realmExcludeResults = realmResults.where().equalTo("exclude", true).findAll();
        List excludeList = new ArrayList();
        for(int i =0; i< realmExcludeResults.size(); i++) {
            InExcludeNo inExcludeNo = (InExcludeNo) realmExcludeResults.get(i);
            excludeList.add(inExcludeNo.getNo());
        }

        for (int i = 0; i < BALL_SIZE; i++) {
            int key = 0;
            int value = 0;
            do {
                key = random.nextInt(NumberBallMetrix.METRIX_SIZE); // 25칸 바둑칸
                if (realmIncludeResults.size() > i) {
                    InExcludeNo inExcludeNo = (InExcludeNo) realmIncludeResults.get(i);
                    value = inExcludeNo.getNo();
                } else {
                    value = random.nextInt(44) + 1;
                }
            } while (balls.containsKey(key) || balls.containsValue(value) || excludeList.contains(value));
            logger.debug("[" + (i + 1) + "]:" + key + "/" + value );
            balls.put(key, value);
        }

        return balls;
    }

    public String[] getEachRankCount(Map<Integer, Integer> balls) {
        final int RANK = 5;
        String[] results = new String[RANK];
        for(int i = 1; i <= RANK; i++) {
            results[i - 1] = "" + lottoUtils.findRankCount(i, balls);
        }

        return results;
    }

    public GenHistoryListAdapter getGenHistoryAdapter() {
        return genHistoryAdapter;
    }

    public void saveHistory(Map<Integer, Integer> balls) {
        genHistoryAdapter.addItem(balls);
        genHistoryAdapter.notifyDataSetChanged();
        saveHistoryDB(balls);
    }

    public void saveHistoryDB(Map<Integer, Integer> balls) {
        NewHistoryNo data = new NewHistoryNo();
        List<Integer> list = lottoUtils.sortByValue(balls);
        DrwtNos nos = new DrwtNos();
        nos.setDrwtNo1(list.get(0));
        nos.setDrwtNo2(list.get(1));
        nos.setDrwtNo3(list.get(2));
        nos.setDrwtNo4(list.get(3));
        nos.setDrwtNo5(list.get(4));
        nos.setDrwtNo6(list.get(5));
        data.setNos(nos);
        data.setBookmark(false);
        data.setGenDate(DateUtils.getNowDate("yyyy-MM-dd"));
        newHistoryRepository.save(data, new TransactionCallback.OnInsertCallback() {
            @Override
            public void onSuccess() {
                logger.debug("successed new history balls from DB!");
            }
        });
    }

    public void removeHistory(int pos) {
        genHistoryAdapter.removeItem(pos);
        genHistoryAdapter.notifyDataSetChanged();
    }

    public void updateRankResults() {
        MyApplication.lottoList = lottoUtils.getRankResults();
    }

    public ArrayList<IBarDataSet> getHBarDataSet(List<Integer> balls) {
        LottoRepository repository = new LottoRepository();
        ArrayList<IBarDataSet> dataSets = null;
        List<Integer> colors = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < balls.size(); i++) {
            entries.add(new BarEntry(repository.selectAvgPickedNumber(balls.get(i)).size(), i));
            colors.add(lottoUtils.getBallColor(balls.get(i)));
        }

        BarDataSet barDataSet1 = new BarDataSet(entries, "");
        barDataSet1.setDrawValues(false);
        barDataSet1.setColors(colors);
        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    public ArrayList<String> getHBallLabel(List<Integer> label) {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int no : label) {
            xAxis.add("" + no);
        }
        return xAxis;
    }

}
