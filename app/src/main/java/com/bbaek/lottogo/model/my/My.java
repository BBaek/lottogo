package com.bbaek.lottogo.model.my;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by woonsungbaek on 2016. 4. 28..
 */
public class My extends RealmObject {
    @PrimaryKey
    private int seq;

    private RealmList<InExcludeNo> inExcludeNoList;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public RealmList<InExcludeNo> getInExcludeNoList() {
        return inExcludeNoList;
    }

    public void setInExcludeNoList(RealmList<InExcludeNo> inExcludeNoList) {
        this.inExcludeNoList = inExcludeNoList;
    }
}
