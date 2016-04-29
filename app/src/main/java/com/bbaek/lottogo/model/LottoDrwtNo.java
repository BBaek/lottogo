package com.bbaek.lottogo.model;

import io.realm.RealmObject;

/**
 * Created by woonsungbaek on 2016. 4. 15..
 */
public class LottoDrwtNo extends RealmObject {
    private int drwtNo;
    private String drwtNoStr;

    public int getDrwtNo() {
        return drwtNo;
    }

    public void setDrwtNo(int drwtNo) {
        this.drwtNo = drwtNo;
    }

    public String getDrwtNoStr() {
        return drwtNoStr;
    }

    public void setDrwtNoStr(String drwtNoStr) {
        this.drwtNoStr = drwtNoStr;
    }
}
