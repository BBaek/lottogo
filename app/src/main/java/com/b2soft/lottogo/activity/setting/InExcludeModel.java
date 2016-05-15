package com.b2soft.lottogo.activity.setting;

import android.content.Context;

import com.b2soft.lottogo.LottoUtils;
import com.b2soft.lottogo.Repository.InExcludeRepository;
import com.b2soft.lottogo.Repository.TransactionCallback;
import com.b2soft.lottogo.adapter.ExcludeGridAdapter;
import com.b2soft.lottogo.adapter.IncludeGridAdapter;
import com.b2soft.lottogo.model.my.InExcludeNo;
import com.b2soft.lottogo.model.my.My;
import com.b2soft.lottogo.utils.BBLogger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by woonsungbaek on 2016. 4. 27..
 */
public class InExcludeModel {
    private BBLogger logger = new BBLogger(getClass().getSimpleName());

    private Context context;
    private LottoUtils lottoUtils;
    private InExcludeRepository inExcludeRepository;

    public InExcludeModel(Context context) {
        this.context = context;
        this.lottoUtils = new LottoUtils(context);
        this.inExcludeRepository = new InExcludeRepository();
    }

    public void saveBalls(Map<Integer, InExcludeNo> datas) {
        if (datas != null) {
            Iterator<Integer> keys = datas.keySet().iterator();
            RealmList<InExcludeNo> realmDatas = new RealmList<>();
            while (keys.hasNext()) {
                int key = keys.next();
                realmDatas.add(datas.get(key));
            }
            inExcludeRepository.insert(realmDatas, new TransactionCallback.OnInsertCallback() {
                @Override
                public void onSuccess() {
                    logger.debug("onSuccess : saveBalls");
                }
            });
        }
    }

    public void saveBall(InExcludeNo data) {
        inExcludeRepository.insert(data, new TransactionCallback.OnInsertCallback() {
            @Override
            public void onSuccess() {
                logger.debug("onSuccess : saveBall");
            }
        });
    }

    public Map<Integer, InExcludeNo> getInitData() {
        Map<Integer, InExcludeNo> results = new HashMap<>();
        RealmResults realmResults = selectMy();
        if (realmResults.size() > 0) { // yes
            for (int i = 1; i <= 45; i++) {
                InExcludeNo inExcludeNo = selectInExcludeNo(i);
                if (inExcludeNo == null) {
                    inExcludeNo = new InExcludeNo();
                    inExcludeNo.setNo(i);
                    inExcludeNo.setExclude(false);
                    inExcludeNo.setInclude(false);
                    saveBall(inExcludeNo);
                }
                results.put(i, inExcludeNo);
            }
        } else { // no
            inExcludeRepository.insertFirst(new TransactionCallback.OnInsertCallback() {
                @Override
                public void onSuccess() {
                    logger.debug("onSuccess : save my");
                }
            });

            for (int i = 1; i <= 45; i++) {
                InExcludeNo inExcludeNo = new InExcludeNo();
                inExcludeNo.setNo(i);
                inExcludeNo.setExclude(false);
                inExcludeNo.setInclude(false);
                results.put(i, inExcludeNo);
            }
            saveBalls(results);
        }

        return results;
    }

    public void updateExcludedBalls(int no, boolean excluded) {
        inExcludeRepository.updateExcludeNo(no, excluded, new TransactionCallback.OnUpdateCallback() {
            @Override
            public void onSuccess() {
                logger.debug("onSuccess : updateExcludedBalls");
            }
        });
    }

    public void updateIncludedBalls(int no, boolean included) {
        inExcludeRepository.updateIncludeNo(no, included, new TransactionCallback.OnUpdateCallback() {
            @Override
            public void onSuccess() {
                logger.debug("onSuccess : updateIncludedBalls");
            }
        });
    }

    public int getExcludedSize() {
//        RealmResults results = inExcludeRepository.selectAllInExcludeNos();
        return inExcludeRepository.selectExcludedNos(true).size();
    }

    public int getIncludedSize() {
        RealmResults results = inExcludeRepository.selectAllInExcludeNos();
        return inExcludeRepository.selectIncludedNos(true).size();
    }

    public RealmResults selectAllInExcludeNos() {
        return inExcludeRepository.selectAllInExcludeNos();
    }

    public RealmResults selectMy() {
        return inExcludeRepository.selectMy();
    }

    public InExcludeNo selectInExcludeNo(int no) {
        return inExcludeRepository.selectInExcludeNo(no);
    }

    public void clearAllSelectedExcluded(final ExcludeGridAdapter adapter) {
        inExcludeRepository.updateExcludeNos(true, false, new TransactionCallback.OnUpdateCallback() {
            @Override
            public void onSuccess() {
                logger.debug("onSuccess : clearAllSelectedExcluded");
                adapter.initData();
            }
        });
    }

    public void clearAllSelectedIncluded(final IncludeGridAdapter adapter) {
        inExcludeRepository.updateIncludeNos(true, false, new TransactionCallback.OnUpdateCallback() {
            @Override
            public void onSuccess() {
                logger.debug("onSuccess : clearAllSelectedIncluded");
                adapter.initData();
            }
        });
    }
}
