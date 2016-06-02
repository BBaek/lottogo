package com.b2soft.lottogo.model.my;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by woonsungbaek on 2016. 4. 28..
 */
public class NewHistoryNo extends RealmObject {
    // history number

    @PrimaryKey
    private int id;

    private DrwtNos nos;

    private String genDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DrwtNos getNos() {
        return nos;
    }

    public void setNos(DrwtNos nos) {
        this.nos = nos;
    }

    public String getGenDate() {
        return genDate;
    }

    public void setGenDate(String genDate) {
        this.genDate = genDate;
    }
}
