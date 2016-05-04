package com.bbaek.lottogo.Repository;

import com.bbaek.lottogo.LottoUtils;
import com.bbaek.lottogo.model.Lotto;
import com.bbaek.lottogo.model.LottoDrwtNo;
import com.bbaek.lottogo.model.my.DrwtNos;

import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by woonsungbaek on 2016. 4. 4..
 *
 * Realm CRUD Common Class
 */
public class LottoRepository {

    public <E extends RealmObject> void insert(E clazz, TransactionCallback.OnInsertCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(clazz);
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    public <E extends RealmList> void insert(E clazz, TransactionCallback.OnInsertCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(clazz);
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    public void insert(Class<? extends RealmObject> clazz, InputStream is, TransactionCallback.OnInsertCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        try {
            realm.createAllFromJson(clazz, is);
            realm.commitTransaction();

            if (callback != null) {
                callback.onSuccess();
            }
        } catch (IOException e) {
            realm.cancelTransaction();
            e.printStackTrace();
        }
    }

    public void updateLotto(TransactionCallback.OnUpdateCallback callback) {
        Realm realm = Realm.getDefaultInstance();

        RealmResults results = realm.where(Lotto.class).findAll();

        realm.beginTransaction();
        for(int i = 0; i < results.size(); i++) {
            Lotto lotto = (Lotto) results.get(i);
            if (lotto.getDrwtNos().size() != 6) {
                LottoDrwtNo drwtNo1 = new LottoDrwtNo();
                drwtNo1.setDrwtNo(lotto.getDrwtNo1());
                drwtNo1.setDrwtNoStr(LottoUtils.convertDoubleDigit(lotto.getDrwtNo1()));
                lotto.getDrwtNos().add(drwtNo1);

                LottoDrwtNo drwtNo2 = new LottoDrwtNo();
                drwtNo2.setDrwtNo(lotto.getDrwtNo2());
                drwtNo2.setDrwtNoStr(LottoUtils.convertDoubleDigit(lotto.getDrwtNo2()));
                lotto.getDrwtNos().add(drwtNo2);

                LottoDrwtNo drwtNo3 = new LottoDrwtNo();
                drwtNo3.setDrwtNo(lotto.getDrwtNo3());
                drwtNo3.setDrwtNoStr(LottoUtils.convertDoubleDigit(lotto.getDrwtNo3()));
                lotto.getDrwtNos().add(drwtNo3);

                LottoDrwtNo drwtNo4 = new LottoDrwtNo();
                drwtNo4.setDrwtNo(lotto.getDrwtNo4());
                drwtNo4.setDrwtNoStr(LottoUtils.convertDoubleDigit(lotto.getDrwtNo4()));
                lotto.getDrwtNos().add(drwtNo4);

                LottoDrwtNo drwtNo5 = new LottoDrwtNo();
                drwtNo5.setDrwtNo(lotto.getDrwtNo5());
                drwtNo5.setDrwtNoStr(LottoUtils.convertDoubleDigit(lotto.getDrwtNo5()));
                lotto.getDrwtNos().add(drwtNo5);

                LottoDrwtNo drwtNo6 = new LottoDrwtNo();
                drwtNo6.setDrwtNo(lotto.getDrwtNo6());
                drwtNo6.setDrwtNoStr(LottoUtils.convertDoubleDigit(lotto.getDrwtNo6()));
                lotto.getDrwtNos().add(drwtNo6);
            }
        }
        realm.commitTransaction();

        if (callback != null) {
            callback.onSuccess();
        }
    }

    public void update(Class<? extends RealmObject> clazz, TransactionCallback.OnUpdateCallback callback) {
        Realm realm = Realm.getDefaultInstance();

        RealmResults results = realm.where(clazz).findAll();

        realm.beginTransaction();
        for(int i = 0; i < results.size(); i++) {
            Lotto lotto = (Lotto) results.get(i);
            if (lotto.getDrwtNos().size() != 6) {
            }
        }
        realm.commitTransaction();

        if (callback != null) {
            callback.onSuccess();
        }
    }

    public void select(Class<? extends RealmObject> clazz, TransactionCallback.OnSelectAllCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults results = realm.where(clazz).findAll();

        if (callback != null) {
            callback.onSuccess(results);
        }
    }

    public void selectLotto(int drwNo, TransactionCallback.OnSelectCallback callback) {
        if (callback != null) {
            callback.onSuccess(selectLotto(drwNo));
        }
    }

    public RealmObject selectLotto(int drwNo) {
        Realm realm = Realm.getDefaultInstance();
        RealmObject results = realm.where(Lotto.class).equalTo("drwNo", drwNo).findFirst();
        return results;
    }

    public RealmResults select(Class<? extends RealmObject> clazz) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults results = realm.where(clazz).findAll();

        return results;
    }

    public RealmResults selectAvgPickedNumber(int ballNo) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults results = realm.where(Lotto.class).contains("drwtNos.drwtNoStr", LottoUtils.convertDoubleDigit(ballNo)).findAll();

        return results;
    }

//    public RealmResults selectMatchNumbers(int drwNo, DrwtNos drwtNos) {
//        Realm realm = Realm.getDefaultInstance();
//        RealmResults results = realm.where(Lotto.class)
//                .beginGroup()
//                .equalTo("drwNo", drwNo)
//                .contains("drwtNos.drwtNoStr", LottoUtils.convertDoubleDigit(drwtNos.getDrwtNo1()))
//                .contains("drwtNos.drwtNoStr", LottoUtils.convertDoubleDigit(drwtNos.getDrwtNo2()))
//                .contains("drwtNos.drwtNoStr", LottoUtils.convertDoubleDigit(drwtNos.getDrwtNo3()))
//                .contains("drwtNos.drwtNoStr", LottoUtils.convertDoubleDigit(drwtNos.getDrwtNo4()))
//                .contains("drwtNos.drwtNoStr", LottoUtils.convertDoubleDigit(drwtNos.getDrwtNo5()))
//                .contains("drwtNos.drwtNoStr", LottoUtils.convertDoubleDigit(drwtNos.getDrwtNo6()))
//                .endGroup()
//                .findAll();
//
//        return results;
//    }

    public void delete(Class<? extends RealmObject> clazz, TransactionCallback.OnDeleteCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults results = realm.where(clazz).findAll();
        if (results != null) {
            realm.beginTransaction();
            results.clear();
            realm.commitTransaction();
        }

        if (callback != null)
            callback.onSuccess();
    }

    static public int getNextKey(Class<? extends RealmObject> clazz) {
        Realm realm = Realm.getDefaultInstance();
        try {
            return realm.where(clazz).max("id").intValue() + 1;
        } catch (Exception e) {
            return 1;
        }
    }
}
