package com.bbaek.lottogo.activity.main;

import android.content.Context;

import com.bbaek.lottogo.LottoUtils;
import com.bbaek.lottogo.Repository.InExcludeRepository;
import com.bbaek.lottogo.Repository.LottoRepository;
import com.bbaek.lottogo.activity.MyApplication;
import com.bbaek.lottogo.adapter.GenHistoryListAdapter;
import com.bbaek.lottogo.model.my.InExcludeNo;
import com.bbaek.lottogo.utils.BBLogger;
import com.bbaek.lottogo.widget.NumberBallMetrix;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

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

    public MainModel(Context context) {
        this.context = context;
        lottoUtils = new LottoUtils(context);
        genHistoryAdapter = new GenHistoryListAdapter(context, new ArrayList<Map<Integer, Integer>>());
        inExcludeRepository = new InExcludeRepository();
    }

    public Map<Integer, Integer> getRandBalls() {
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
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < balls.size(); i++) {
            entries.add(new BarEntry(repository.selectAvgPickedNumber(balls.get(i)).size(), i));
        }

        BarDataSet barDataSet1 = new BarDataSet(entries, "");
        barDataSet1.setDrawValues(false);
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

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
