package com.bbaek.lottogo.model;

import android.os.Bundle;

import com.bbaek.common.client.lottogo.model.LottoInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by woonsungbaek on 2016. 3. 29..
 */
public class Lotto extends RealmObject {

    @Ignore
    private final int MIDDLE_DIGIT = 25;

    public static Lotto newInstance(LottoInfo lottoInfo) {
        Lotto lotto = new Lotto();
        lotto.setDrwNo(lottoInfo.getDrwNo());
        lotto.setBnusNo(lottoInfo.getBnusNo());
        lotto.setDrwtNo1(lottoInfo.getDrwtNo1());
        lotto.setDrwtNo2(lottoInfo.getDrwtNo2());
        lotto.setDrwtNo3(lottoInfo.getDrwtNo3());
        lotto.setDrwtNo4(lottoInfo.getDrwtNo4());
        lotto.setDrwtNo5(lottoInfo.getDrwtNo5());
        lotto.setDrwtNo6(lottoInfo.getDrwtNo6());
        lotto.setFirstPrzwnerCo(lottoInfo.getFirstPrzwnerCo());
        lotto.setFirstWinamnt(lottoInfo.getFirstWinamnt());
        lotto.setReturnValue(lottoInfo.getReturnValue());
        lotto.setTotSellamnt(lottoInfo.getTotSellamnt());
        lotto.setDrwNoDate(lottoInfo.getDrwNoDate());
        return lotto;
    }

    public Lotto() {
        this.includeBonus = true;
    }

    public Lotto(boolean includeBonus) {
        this.includeBonus = includeBonus;
    }

    @PrimaryKey
    private int drwNo;
    private String returnValue;
    private String drwNoDate;
    private int firstPrzwnerCo;
    // 1
    private int drwtNo1;
    // 2
    private int drwtNo2;
    // 3
    private int drwtNo3;
    // 4
    private int drwtNo4;
    // 5
    private int drwtNo5;
    // 6
    private int drwtNo6;
    // bonus
    private int bnusNo;
    private long firstWinamnt;
    private long totSellamnt;

    private RealmList<LottoDrwtNo> drwtNos;

    @Ignore
    private int rank;

    @Ignore
    private boolean includeBonus;

    // 1~25
    @Ignore
    private int totalLowDigit;
    // 26~45
    @Ignore
    private int totalHighDigit;

    // 1~25
    @Ignore
    private int totalCountLowDigit;
    // 26~45
    @Ignore
    private int totalCountHighDigit;

    // 1, 3 ~ 45
    @Ignore
    private int totalOddDigit;
    // 2, 4 ~ 44
    @Ignore
    private int totalEvenDigit;

    // 1, 3 ~ 45
    @Ignore
    private int totalCountOddDigit;
    // 2, 4 ~ 44
    @Ignore
    private int totalCountEvenDigit;

    // 1, 2, 4, 5, 6, 10 = (1,2) + (4,5) + (5,6) = 3
    @Ignore
    private int totalSequentialDigit;

    // 1, 10, 45 = 1 + 4 = 5
    @Ignore
    private int totalLeftDigit;
    // 1, 10, 45 = 1 + 0 + 5 = 6
    @Ignore
    private int totalRightDigit;

    @Ignore
    private int totalNo123;

    @Ignore
    private int totalNo456;

    @Ignore
    private int totalSum;

    public int getDrwNo() {
        return drwNo;
    }

    public void setDrwNo(int drwNo) {
        this.drwNo = drwNo;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getDrwNoDate() {
        return drwNoDate;
    }

    public void setDrwNoDate(String drwNoDate) {
        this.drwNoDate = drwNoDate;
    }

    public int getFirstPrzwnerCo() {
        return firstPrzwnerCo;
    }

    public void setFirstPrzwnerCo(int firstPrzwnerCo) {
        this.firstPrzwnerCo = firstPrzwnerCo;
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

    public int getBnusNo() {
        return bnusNo;
    }

    public void setBnusNo(int bnusNo) {
        this.bnusNo = bnusNo;
    }

    public RealmList<LottoDrwtNo> getDrwtNos() {
        return drwtNos;
    }

    public void setDrwtNos(RealmList<LottoDrwtNo> drwtNos) {
        this.drwtNos = drwtNos;
    }

    public long getFirstWinamnt() {
        return firstWinamnt;
    }

    public void setFirstWinamnt(long firstWinamnt) {
        this.firstWinamnt = firstWinamnt;
    }

    public long getTotSellamnt() {
        return totSellamnt;
    }

    public void setTotSellamnt(long totSellamnt) {
        this.totSellamnt = totSellamnt;
    }

    public boolean isIncludeBonus() {
        return includeBonus;
    }

    public void setIncludeBonus(boolean includeBonus) {
        this.includeBonus = includeBonus;
    }

//    private int[] getDrwtNoArr() {
//        return new int[]{ drwtNo1, drwtNo2, drwtNo3, drwtNo4, drwtNo5, drwtNo6 };
//    }
//
//    public int getTotalLowDigit() {
//        totalLowDigit = 0;
//
//        for (int value : getDrwtNoArr()) {
//            if (value <= MIDDLE_DIGIT) {
//                totalLowDigit += value;
//            } else {
//                break;
//            }
//        }
//        if (includeBonus && bnusNo <= MIDDLE_DIGIT) totalLowDigit += bnusNo;
//
//        return totalLowDigit;
//    }
//
//    public int getTotalHighDigit() {
//        totalHighDigit = 0;
//
//        for (int value : getDrwtNoArr()) {
//            if (value > MIDDLE_DIGIT) {
//                totalHighDigit += value;
//            } else {
//                break;
//            }
//        }
//        if (includeBonus && bnusNo > MIDDLE_DIGIT) totalHighDigit += bnusNo;
//
//        return totalHighDigit;
//    }
//
//    public int getTotalCountLowDigit() {
//        totalCountLowDigit = 0;
//
//        for (int value : getDrwtNoArr()) {
//            if (value <= MIDDLE_DIGIT) {
//                totalCountLowDigit++;
//            } else {
//                break;
//            }
//        }
//        if (includeBonus && bnusNo <= MIDDLE_DIGIT) totalCountLowDigit++;
//
//        return totalCountLowDigit;
//    }
//
//    public int getTotalCountHighDigit() {
//        totalCountHighDigit = 0;
//
//        for (int value : getDrwtNoArr()) {
//            if (value > MIDDLE_DIGIT) {
//                totalCountHighDigit++;
//            }
//        }
//        if (includeBonus && bnusNo > MIDDLE_DIGIT) totalCountHighDigit++;
//
//        return totalCountHighDigit;
//    }
//
//    public int getTotalOddDigit() {
//        totalOddDigit = 0;
//
//        for (int value : getDrwtNoArr()) {
//            if ((value % 2) > 0) {
//                totalOddDigit += value;
//            }
//        }
//        if (includeBonus && (bnusNo % 2) > 0) totalOddDigit += bnusNo;
//
//        return totalOddDigit;
//    }
//
//    public int getTotalEvenDigit() {
//        totalEvenDigit = 0;
//
//        for (int value : getDrwtNoArr()) {
//            if ((value % 2) == 0) {
//                totalEvenDigit += value;
//            }
//        }
//        if (includeBonus && (bnusNo % 2) == 0) totalEvenDigit += bnusNo;
//
//        return totalEvenDigit;
//    }
//
//    public int getTotalCountOddDigit() {
//        totalCountOddDigit = 0;
//
//        for (int value : getDrwtNoArr()) {
//            if ((value % 2) > 0) {
//                totalCountOddDigit++;
//            }
//        }
//        if (includeBonus && (bnusNo % 2) > 0) totalCountOddDigit++;
//
//        return totalCountOddDigit;
//    }
//
//    public int getTotalCountEvenDigit() {
//        totalCountEvenDigit = 0;
//
//        for (int value : getDrwtNoArr()) {
//            if ((value % 2) == 0) {
//                totalCountEvenDigit++;
//            }
//        }
//        if (includeBonus && (bnusNo % 2) == 0) totalCountEvenDigit++;
//
//        return totalCountEvenDigit;
//    }
//
//    public int getTotalSequentialDigit() {
//        totalSequentialDigit = 0;
//
//        if(isTotalSequentialDigit(drwtNo1, drwtNo2)) totalSequentialDigit++;
//        if(isTotalSequentialDigit(drwtNo2, drwtNo3)) totalSequentialDigit++;
//        if(isTotalSequentialDigit(drwtNo3, drwtNo4)) totalSequentialDigit++;
//        if(isTotalSequentialDigit(drwtNo4, drwtNo5)) totalSequentialDigit++;
//        if(isTotalSequentialDigit(drwtNo5, drwtNo6)) totalSequentialDigit++;
//        if(isTotalSequentialDigit(drwtNo6, drwtNo2)) totalSequentialDigit++;
//
//        return totalSequentialDigit;
//    }
//
//    private boolean isTotalSequentialDigit(int value, int value2) {
//        if ((value + 1) == value2) {
//            return true;
//        }
//        return false;
//    }
//
//    public int getTotalLeftDigit() {
//        totalLeftDigit = 0;
//
//        totalLeftDigit += drwtNo1 / 10;
//        totalLeftDigit += drwtNo2 / 10;
//        totalLeftDigit += drwtNo3 / 10;
//        totalLeftDigit += drwtNo4 / 10;
//        totalLeftDigit += drwtNo5 / 10;
//        totalLeftDigit += drwtNo6 / 10;
//        if(includeBonus) totalLeftDigit += bnusNo / 10;
//
//        return totalLeftDigit;
//    }
//
//    public int getTotalRightDigit() {
//        totalRightDigit = 0;
//
//        totalRightDigit += drwtNo1 % 10;
//        totalRightDigit += drwtNo2 % 10;
//        totalRightDigit += drwtNo3 % 10;
//        totalRightDigit += drwtNo4 % 10;
//        totalRightDigit += drwtNo5 % 10;
//        totalRightDigit += drwtNo6 % 10;
//        if(includeBonus) totalRightDigit += bnusNo % 10;
//
//        return totalRightDigit;
//    }
//
//    public int getTotalNo123() {
//        totalNo123 = drwtNo1 + drwtNo2 + drwtNo3;
//        return totalNo123;
//    }
//
//    public int getTotalNo456() {
//        totalNo456 = drwtNo4 + drwtNo5 + drwtNo6;
//        return totalNo456;
//    }
//
//    public int getTotalSum() {
//        totalSum = drwtNo1 + drwtNo2 + drwtNo3 + drwtNo4 + drwtNo5 + drwtNo6;
//        if(includeBonus) totalSum += bnusNo;
//        return  totalSum;
//    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}