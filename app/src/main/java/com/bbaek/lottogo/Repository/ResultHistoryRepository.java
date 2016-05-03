package com.bbaek.lottogo.Repository;

import com.bbaek.lottogo.model.my.InExcludeNo;
import com.bbaek.lottogo.model.my.My;
import com.bbaek.lottogo.model.my.ResultHistoryNo;

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
public class ResultHistoryRepository {

    public void insert(ResultHistoryNo data, TransactionCallback.OnInsertCallback callback) {
        Realm realm = Realm.getDefaultInstance();

        if(!isExistedResult(data.getKey())) {
            realm.beginTransaction();
            data.setId(CommonRepository.getNextKey(ResultHistoryNo.class));
            realm.copyToRealm(data);
            realm.commitTransaction();
        }
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

    public void updateExcludeNo(int no, boolean excluded, TransactionCallback.OnUpdateCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults results = realm.where(InExcludeNo.class).findAll();

        realm.beginTransaction();
        InExcludeNo result = (InExcludeNo) results.where().equalTo("no", no).findFirst();
        result.setExclude(excluded);
        realm.commitTransaction();

        if (callback != null) {
            callback.onSuccess();
        }
    }

    public void updateIncludeNo(int no, boolean included, TransactionCallback.OnUpdateCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults results = realm.where(InExcludeNo.class).findAll();

        realm.beginTransaction();
        InExcludeNo result = (InExcludeNo) results.where().equalTo("no", no).findFirst();
        result.setInclude(included);
        realm.commitTransaction();

        if (callback != null) {
            callback.onSuccess();
        }
    }

    public void updateExcludeNos(boolean beforeExcluded, boolean afterExcluded, TransactionCallback.OnUpdateCallback callback) {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<InExcludeNo> realmResults = selectExcludedNos(beforeExcluded);
        realm.beginTransaction();
        for (int i = realmResults.size() - 1; i >= 0; i--) {
            realmResults.get(i).setExclude(afterExcluded);
        }
        realm.commitTransaction();

        if (callback != null) {
            callback.onSuccess();
        }
    }

    public void updateIncludeNos(boolean beforeIncluded, boolean afterIncluded, TransactionCallback.OnUpdateCallback callback) {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<InExcludeNo> realmResults = selectIncludedNos(beforeIncluded);
        realm.beginTransaction();
        for (int i = realmResults.size() - 1; i >= 0; i--) {
            realmResults.get(i).setInclude(afterIncluded);
        }
        realm.commitTransaction();

        if (callback != null) {
            callback.onSuccess();
        }
    }

    public RealmResults selectAllInExcludeNos() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults results = realm.where(InExcludeNo.class).findAll();
        return results;
    }

    public RealmResults<InExcludeNo> selectExcludedNos(boolean excluded) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<InExcludeNo> results = realm.where(InExcludeNo.class).findAll().where().equalTo("exclude", excluded).findAll();
        return results;
    }

    public RealmResults<InExcludeNo> selectIncludedNos(boolean included) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<InExcludeNo> results = realm.where(InExcludeNo.class).findAll().where().equalTo("include", included).findAll();
        return results;
    }

    public ResultHistoryNo select(String key) {
        Realm realm = Realm.getDefaultInstance();
        RealmObject results = realm.where(ResultHistoryNo.class).equalTo("key", key).findFirst();
        return (ResultHistoryNo) results;
    }

    public boolean isExistedResult(String key) {
        Realm realm = Realm.getDefaultInstance();
        if (realm.where(ResultHistoryNo.class).equalTo("key", key).findAll().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

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
}
