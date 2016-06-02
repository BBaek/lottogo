package com.b2soft.lottogo.Repository;

import com.b2soft.lottogo.model.my.NewHistoryNo;
import com.b2soft.lottogo.model.my.ResultHistoryNo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by woonsungbaek on 2016. 4. 4..
 *
 * Realm CRUD Common Class
 */
public class NewHistoryRepository {
    public void save(NewHistoryNo data, TransactionCallback.OnInsertCallback callback) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(data);
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    public ResultHistoryNo select(String key) {
        Realm realm = Realm.getDefaultInstance();
        RealmObject result = realm.where(ResultHistoryNo.class).equalTo("key", key).findFirst();
        return (ResultHistoryNo) result;
    }

    public RealmResults<ResultHistoryNo> selectAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ResultHistoryNo> results = realm.where(ResultHistoryNo.class).findAll();
        return results;
    }

    public RealmResults<ResultHistoryNo> selectSortedAll(String fieldName) {
        return selectSortedAll(fieldName, Sort.ASCENDING);
    }

    public RealmResults<ResultHistoryNo> selectSortedAll(String fieldName, Sort sort) {
        RealmResults<ResultHistoryNo> results = selectAll();
        results.sort(fieldName, sort);
        return results;
    }

    public boolean isExistedResult(String key) {
        Realm realm = Realm.getDefaultInstance();
        if (realm.where(ResultHistoryNo.class).equalTo("key", key).findAll().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(String key) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ResultHistoryNo> results = realm.where(ResultHistoryNo.class).equalTo("key", key).findAll();
        if (results != null) {
            realm.beginTransaction();
            results.clear();
            realm.commitTransaction();
            return true;
        }
        return false;
    }
}
