package com.b2soft.lottogo.Repository;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by woonsungbaek on 2016. 3. 30..
 */
public class TransactionCallback extends Realm.Transaction.Callback {

    public interface OnDeleteCallback {
        void onSuccess();
    }
    public interface OnInsertCallback {
        void onSuccess();
    }
    public interface OnUpdateCallback {
        void onSuccess();
    }
    public interface OnSelectCallback {
        void onSuccess(RealmObject result);
    }
    public interface OnSelectAllCallback {
        void onSuccess(RealmResults results);
    }
    public interface OnSelectAllCallbackList {
        void onSuccess(RealmList results);
    }
}
