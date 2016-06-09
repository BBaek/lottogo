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
        data.setId(CommonRepository.getNextKey(NewHistoryNo.class));
        realm.copyToRealm(data);
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    public NewHistoryNo select(int id) {
        Realm realm = Realm.getDefaultInstance();
        RealmObject result = realm.where(NewHistoryNo.class).equalTo("id", id).findFirst();
        return (NewHistoryNo) result;
    }

    public RealmResults<NewHistoryNo> selectAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<NewHistoryNo> results = realm.where(NewHistoryNo.class).findAll();
        return results;
    }

    public RealmResults<NewHistoryNo> selectSortedAll(String fieldName) {
        return selectSortedAll(fieldName, Sort.ASCENDING);
    }

    public RealmResults<NewHistoryNo> selectSortedAll(String fieldName, Sort sort) {
        RealmResults<NewHistoryNo> results = selectAll();
        results.sort(fieldName, sort);
        return results;
    }

    public RealmResults<NewHistoryNo> selectAllMarked(boolean isMarked) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<NewHistoryNo> results = realm.where(NewHistoryNo.class).equalTo("bookmark", isMarked).findAll();
        results.sort("id", Sort.DESCENDING);
        return results;
    }

    public boolean updateBookmark(int id, boolean isMark) {
        Realm realm = Realm.getDefaultInstance();
        NewHistoryNo no = select(id);
        if (no != null) {
            realm.beginTransaction();
            no.setBookmark(isMark);
            realm.commitTransaction();
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<NewHistoryNo> results = realm.where(NewHistoryNo.class).equalTo("id", id).findAll();
        if (results != null) {
            realm.beginTransaction();
            results.clear();
            realm.commitTransaction();
            return true;
        }
        return false;
    }

    public boolean deleteAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<NewHistoryNo> results = realm.where(NewHistoryNo.class).findAll();
        if (results != null) {
            realm.beginTransaction();
            results.clear();
            realm.commitTransaction();
            return true;
        }
        return false;
    }

    public boolean deleteAllNotMarked() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<NewHistoryNo> results = realm.where(NewHistoryNo.class).equalTo("bookmark", false).findAll();
        if (results != null) {
            realm.beginTransaction();
            results.clear();
            realm.commitTransaction();
            return true;
        }
        return false;
    }
}
