package com.bbaek.lottogo.model.my;

import com.bbaek.lottogo.model.Lotto;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by woonsungbaek on 2016. 4. 28..
 */
public class ResultHistoryNo extends RealmObject {
    // history number
    @PrimaryKey
    private int id;
    private String key; // 0700m082532353845q121722354144q091724253342q070809192241q1013182324391673179286
    private String annoDate;
    private int drwNo;
    private RealmList<DrwtNos> drwtNoList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAnnoDate() {
        return annoDate;
    }

    public void setAnnoDate(String annoDate) {
        this.annoDate = annoDate;
    }

    public int getDrwNo() {
        return drwNo;
    }

    public void setDrwNo(int drwNo) {
        this.drwNo = drwNo;
    }

    public RealmList<DrwtNos> getDrwtNoList() {
        return drwtNoList;
    }

    public void setDrwtNoList(RealmList<DrwtNos> drwtNoList) {
        this.drwtNoList = drwtNoList;
    }
}
