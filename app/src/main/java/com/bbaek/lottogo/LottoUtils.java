package com.bbaek.lottogo;

import android.content.Context;

import com.bbaek.lottogo.Repository.CommonRepository;
import com.bbaek.lottogo.Repository.LottoRepository;
import com.bbaek.lottogo.Repository.TransactionCallback;
import com.bbaek.lottogo.model.Lotto;
import com.bbaek.lottogo.model.my.DrwtNos;
import com.bbaek.lottogo.model.my.ResultHistoryNo;
import com.bbaek.lottogo.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;

/**
 * Created by woonsungbaek on 2016. 4. 18..
 */
public class LottoUtils {
    Context context;
    CommonRepository commonRepository = new CommonRepository();

    RealmResults<Lotto> rank1Result;
    RealmResults<Lotto> rank2Result;
    RealmResults<Lotto> rank3Result;
    RealmResults<Lotto> rank4Result;
    RealmResults<Lotto> rank5Result;

    public int getBallColor(int number) {
        if (number < 11) {
            return context.getResources().getColor(R.color.colorBallYellow);
        } else if (number >= 11 && number < 21) {
            return context.getResources().getColor(R.color.colorBallBlue);
        } else if (number >= 21 && number < 31) {
            return context.getResources().getColor(R.color.colorBallRed);
        } else if (number >= 31 && number < 41) {
            return context.getResources().getColor(R.color.colorBallPurple);
        } else {
            return context.getResources().getColor(R.color.colorBallGreen);
        }
    }

    public static int compareRank(Lotto drwLotto, DrwtNos datas) {
        int matchCnt = 0;
        List<Integer> drwNoList = toArrayList(drwLotto);
        if (drwNoList.contains(datas.getDrwtNo1())) matchCnt++;
        if (drwNoList.contains(datas.getDrwtNo2())) matchCnt++;
        if (drwNoList.contains(datas.getDrwtNo3())) matchCnt++;
        if (drwNoList.contains(datas.getDrwtNo4())) matchCnt++;
        if (drwNoList.contains(datas.getDrwtNo5())) matchCnt++;
        if (drwNoList.contains(datas.getDrwtNo6())) matchCnt++;

        if (matchCnt == 6) {
            return 1;
        } else if (matchCnt == 5) {
            return 3;
        } else if (matchCnt == 4) {
            int bnusNo = drwLotto.getBnusNo();
            if (bnusNo == datas.getDrwtNo1()
                    || bnusNo == datas.getDrwtNo2()
                    || bnusNo == datas.getDrwtNo3()
                    || bnusNo == datas.getDrwtNo4()
                    || bnusNo == datas.getDrwtNo5()
                    || bnusNo == datas.getDrwtNo6()) {
                return 2;
            } else {
                return 4;
            }
        } else if (matchCnt == 3) {
            return 5;
        } else {
            return 0;
        }
    }

    public LottoUtils(Context context) {
        this.context = context;
    }

    public static List<Integer> sortByValue(final Map map){
        List<Integer> list = null;
        if(map != null && map.size() > 0) {
            list = new ArrayList(map.values());
            Collections.sort(list);
        }
        return list;
    }

    public static List<Integer> toArrayList(Lotto lotto) {
        List<Integer> list = null;
        if (lotto != null) {
            list = new ArrayList<>();
            list.add(lotto.getDrwtNo1());
            list.add(lotto.getDrwtNo2());
            list.add(lotto.getDrwtNo3());
            list.add(lotto.getDrwtNo4());
            list.add(lotto.getDrwtNo5());
            list.add(lotto.getDrwtNo6());
        }

        return list;
    }

    public static String convertDoubleDigit(int value) {
        if (value < 10) {
            return "0" + value;
        }
        return "" + value;
    }

    public int findRankCount(int rank, Map values) {
        List<Integer> list = sortByValue(values);
        switch (rank) {
            case 1:
                PreferenceUtils.instance(context).initDrwtNos(list);
                dbSelectRank1(list);
                return rank1Result.size();
            case 2:
                dbSelectRank2(list);
                return rank2Result.size();
            case 3:
                dbSelectRank3(list);
                return rank3Result.size();
            case 4:
                dbSelectRank4(list);
                return rank4Result.size();
            case 5:
                dbSelectRank5(list);
                return rank5Result.size();
        }
        return 0;
    }

    public List<Lotto> getRankResults() {
        List<Lotto> results = new ArrayList<>();
        if (rank1Result != null) results.addAll(toArrList(1, rank1Result));
        if (rank2Result != null) results.addAll(toArrList(2, rank2Result));
        if (rank3Result != null) results.addAll(toArrList(3, rank3Result));
        if (rank4Result != null) results.addAll(toArrList(4, rank4Result));
        if (rank5Result != null) results.addAll(toArrList(5, rank5Result));
        return results;
    }

    protected List<Lotto> toArrList(int rank, RealmResults<Lotto> values) {
        List<Lotto> results = new ArrayList<>();
        for (Lotto lotto : values) {
            lotto.setRank(rank);
            results.add(lotto);
        }
        return results;
    }

    public RealmResults<Lotto> getRank1Result() {
        return rank1Result;
    }

    public RealmResults<Lotto> getRank2Result() {
        return rank2Result;
    }

    public RealmResults<Lotto> getRank3Result() {
        return rank3Result;
    }

    public RealmResults<Lotto> getRank4Result() {
        return rank4Result;
    }

    public RealmResults<Lotto> getRank5Result() {
        return rank5Result;
    }

    protected void dbSelectRank1(final List<Integer> value) {
        commonRepository.select(Lotto.class, new TransactionCallback.OnSelectAllCallback() {
            @Override
            public void onSuccess(RealmResults results) {
                rank1Result = results.where()
                            .equalTo("drwtNo1", value.get(0))
                            .equalTo("drwtNo2", value.get(1))
                            .equalTo("drwtNo3", value.get(2))
                            .equalTo("drwtNo4", value.get(3))
                            .equalTo("drwtNo5", value.get(4))
                            .equalTo("drwtNo6", value.get(5))
                        .findAll();
            }
        });
    }

    protected void dbSelectRank2(final List<Integer> value) {
        commonRepository.select(Lotto.class, new TransactionCallback.OnSelectAllCallback() {
            @Override
            public void onSuccess(RealmResults results) {
                rank2Result = results.where()
                            .beginGroup()
                                .equalTo("bnusNo", value.get(0))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .equalTo("bnusNo", value.get(1))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .equalTo("bnusNo", value.get(2))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .equalTo("bnusNo", value.get(3))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .equalTo("bnusNo", value.get(4))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .equalTo("bnusNo", value.get(5))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                            .endGroup()
                        .findAll();
            }
        });
    }

    protected void dbSelectRank3(final List<Integer> value) {
        commonRepository.select(Lotto.class, new TransactionCallback.OnSelectAllCallback() {
            @Override
            public void onSuccess(RealmResults results) {
                rank3Result = results.where()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                        .findAll();
            }
        });
    }

    protected void dbSelectRank4(final List<Integer> value) {
        commonRepository.select(Lotto.class, new TransactionCallback.OnSelectAllCallback() {
            @Override
            public void onSuccess(RealmResults results) {
                rank4Result = results.where()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                        .findAll();
            }
        });
    }

    protected void dbSelectRank5(final List<Integer> value) {
        commonRepository.select(Lotto.class, new TransactionCallback.OnSelectAllCallback() {
            @Override
            public void onSuccess(RealmResults results) {
                rank5Result = results.where()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(0)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(1)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(2)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                            .or()
                            .beginGroup()
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(3)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(4)))
                                .contains("drwtNos.drwtNoStr", convertDoubleDigit(value.get(5)))
                            .endGroup()
                        .findAll();
            }
        });
    }

}
