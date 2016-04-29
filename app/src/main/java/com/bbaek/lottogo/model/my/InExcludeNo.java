package com.bbaek.lottogo.model.my;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by woonsungbaek on 2016. 4. 28..
 */
public class InExcludeNo extends RealmObject {

    @PrimaryKey
    private int no;
    private boolean exclude;
    private boolean include;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public boolean isExclude() {
        return exclude;
    }

    public void setExclude(boolean exclude) {
        this.exclude = exclude;
    }

    public boolean isInclude() {
        return include;
    }

    public void setInclude(boolean include) {
        this.include = include;
    }
}
