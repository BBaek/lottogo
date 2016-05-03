package com.bbaek.lottogo.model.my;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by woonsungbaek on 2016. 4. 28..
 */
public class NewHistoryNo extends RealmObject {
    // history number

    @PrimaryKey
    private int no;

    private int seq;

    private String date;

}
