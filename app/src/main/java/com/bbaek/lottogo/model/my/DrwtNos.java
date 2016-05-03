package com.bbaek.lottogo.model.my;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by woonsungbaek on 2016. 4. 28..
 */
public class DrwtNos extends RealmObject {
    // history number

    private int rank;
    private int drwtNo1;
    private int drwtNo2;
    private int drwtNo3;
    private int drwtNo4;
    private int drwtNo5;
    private int drwtNo6;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getDrwtNo1() {
        return drwtNo1;
    }

    public void setDrwtNo1(int drwtNo1) {
        this.drwtNo1 = drwtNo1;
    }

    public int getDrwtNo2() {
        return drwtNo2;
    }

    public void setDrwtNo2(int drwtNo2) {
        this.drwtNo2 = drwtNo2;
    }

    public int getDrwtNo3() {
        return drwtNo3;
    }

    public void setDrwtNo3(int drwtNo3) {
        this.drwtNo3 = drwtNo3;
    }

    public int getDrwtNo4() {
        return drwtNo4;
    }

    public void setDrwtNo4(int drwtNo4) {
        this.drwtNo4 = drwtNo4;
    }

    public int getDrwtNo5() {
        return drwtNo5;
    }

    public void setDrwtNo5(int drwtNo5) {
        this.drwtNo5 = drwtNo5;
    }

    public int getDrwtNo6() {
        return drwtNo6;
    }

    public void setDrwtNo6(int drwtNo6) {
        this.drwtNo6 = drwtNo6;
    }
}
