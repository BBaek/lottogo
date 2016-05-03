package com.bbaek.lottogo;

import com.bbaek.lottogo.model.Lotto;
import com.bbaek.lottogo.model.LottoDrwtNo;

import java.util.List;
import java.util.Map;

import io.realm.RealmList;
import io.realm.annotations.Ignore;

/**
 * Created by woonsungbaek on 2016. 5. 3..
 */
public class LottoAvg {
    private final int MIDDLE_DIGIT = 25;

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
    private boolean includeBonus;
    // 1~25
    private int totalLowDigit;
    // 26~45
    private int totalHighDigit;

    // 1~25
    private int totalCountLowDigit;
    // 26~45
    private int totalCountHighDigit;

    // 1, 3 ~ 45
    private int totalOddDigit;
    // 2, 4 ~ 44
    private int totalEvenDigit;

    // 1, 3 ~ 45
    private int totalCountOddDigit;
    // 2, 4 ~ 44
    private int totalCountEvenDigit;

    // 1, 2, 4, 5, 6, 10 = (1,2) + (4,5) + (5,6) = 3
    private int totalSequentialDigit;

    // 1, 10, 45 = 1 + 4 = 5
    private int totalLeftDigit;
    // 1, 10, 45 = 1 + 0 + 5 = 6
    private int totalRightDigit;

    private int totalNo123;
    private int totalNo456;
    private int totalSum;

    public LottoAvg(Map<Integer, Integer> datas) {
        setData(datas);
        this.includeBonus = false;
    }

    public LottoAvg(Map<Integer, Integer> datas, boolean includeBonus) {
        setData(datas);
        this.includeBonus = includeBonus;
    }

    public void setData(Map<Integer, Integer> datas) {
        List<Integer> sortedDatas = LottoUtils.sortByValue(datas);
        drwtNo1 = sortedDatas.get(0);
        drwtNo2 = sortedDatas.get(1);
        drwtNo3 = sortedDatas.get(2);
        drwtNo4 = sortedDatas.get(3);
        drwtNo5 = sortedDatas.get(4);
        drwtNo6 = sortedDatas.get(5);
    }

    private int[] getDrwtNoArr() {
        return new int[]{ drwtNo1, drwtNo2, drwtNo3, drwtNo4, drwtNo5, drwtNo6 };
    }

    public int getTotalLowDigit() {
        totalLowDigit = 0;

        for (int value : getDrwtNoArr()) {
            if (value <= MIDDLE_DIGIT) {
                totalLowDigit += value;
            } else {
                break;
            }
        }
        if (includeBonus && bnusNo <= MIDDLE_DIGIT) totalLowDigit += bnusNo;

        return totalLowDigit;
    }

    public int getTotalHighDigit() {
        totalHighDigit = 0;

        for (int value : getDrwtNoArr()) {
            if (value > MIDDLE_DIGIT) {
                totalHighDigit += value;
            } else {
                break;
            }
        }
        if (includeBonus && bnusNo > MIDDLE_DIGIT) totalHighDigit += bnusNo;

        return totalHighDigit;
    }

    public int getTotalCountLowDigit() {
        totalCountLowDigit = 0;

        for (int value : getDrwtNoArr()) {
            if (value <= MIDDLE_DIGIT) {
                totalCountLowDigit++;
            } else {
                break;
            }
        }
        if (includeBonus && bnusNo <= MIDDLE_DIGIT) totalCountLowDigit++;

        return totalCountLowDigit;
    }

    public int getTotalCountHighDigit() {
        totalCountHighDigit = 0;

        for (int value : getDrwtNoArr()) {
            if (value > MIDDLE_DIGIT) {
                totalCountHighDigit++;
            }
        }
        if (includeBonus && bnusNo > MIDDLE_DIGIT) totalCountHighDigit++;

        return totalCountHighDigit;
    }

    public int getTotalOddDigit() {
        totalOddDigit = 0;

        for (int value : getDrwtNoArr()) {
            if ((value % 2) > 0) {
                totalOddDigit += value;
            }
        }
        if (includeBonus && (bnusNo % 2) > 0) totalOddDigit += bnusNo;

        return totalOddDigit;
    }

    public int getTotalEvenDigit() {
        totalEvenDigit = 0;

        for (int value : getDrwtNoArr()) {
            if ((value % 2) == 0) {
                totalEvenDigit += value;
            }
        }
        if (includeBonus && (bnusNo % 2) == 0) totalEvenDigit += bnusNo;

        return totalEvenDigit;
    }

    public int getTotalCountOddDigit() {
        totalCountOddDigit = 0;

        for (int value : getDrwtNoArr()) {
            if ((value % 2) > 0) {
                totalCountOddDigit++;
            }
        }
        if (includeBonus && (bnusNo % 2) > 0) totalCountOddDigit++;

        return totalCountOddDigit;
    }

    public int getTotalCountEvenDigit() {
        totalCountEvenDigit = 0;

        for (int value : getDrwtNoArr()) {
            if ((value % 2) == 0) {
                totalCountEvenDigit++;
            }
        }
        if (includeBonus && (bnusNo % 2) == 0) totalCountEvenDigit++;

        return totalCountEvenDigit;
    }

    public int getTotalSequentialDigit() {
        totalSequentialDigit = 0;

        if(isTotalSequentialDigit(drwtNo1, drwtNo2)) totalSequentialDigit++;
        if(isTotalSequentialDigit(drwtNo2, drwtNo3)) totalSequentialDigit++;
        if(isTotalSequentialDigit(drwtNo3, drwtNo4)) totalSequentialDigit++;
        if(isTotalSequentialDigit(drwtNo4, drwtNo5)) totalSequentialDigit++;
        if(isTotalSequentialDigit(drwtNo5, drwtNo6)) totalSequentialDigit++;
        if(isTotalSequentialDigit(drwtNo6, drwtNo2)) totalSequentialDigit++;

        return totalSequentialDigit;
    }

    private boolean isTotalSequentialDigit(int value, int value2) {
        if ((value + 1) == value2) {
            return true;
        }
        return false;
    }

    public int getTotalLeftDigit() {
        totalLeftDigit = 0;

        totalLeftDigit += drwtNo1 / 10;
        totalLeftDigit += drwtNo2 / 10;
        totalLeftDigit += drwtNo3 / 10;
        totalLeftDigit += drwtNo4 / 10;
        totalLeftDigit += drwtNo5 / 10;
        totalLeftDigit += drwtNo6 / 10;
        if(includeBonus) totalLeftDigit += bnusNo / 10;

        return totalLeftDigit;
    }

    public int getTotalRightDigit() {
        totalRightDigit = 0;

        totalRightDigit += drwtNo1 % 10;
        totalRightDigit += drwtNo2 % 10;
        totalRightDigit += drwtNo3 % 10;
        totalRightDigit += drwtNo4 % 10;
        totalRightDigit += drwtNo5 % 10;
        totalRightDigit += drwtNo6 % 10;
        if(includeBonus) totalRightDigit += bnusNo % 10;

        return totalRightDigit;
    }

    public int getTotalNo123() {
        totalNo123 = drwtNo1 + drwtNo2 + drwtNo3;
        return totalNo123;
    }

    public int getTotalNo456() {
        totalNo456 = drwtNo4 + drwtNo5 + drwtNo6;
        return totalNo456;
    }

    public int getTotalSum() {
        totalSum = drwtNo1 + drwtNo2 + drwtNo3 + drwtNo4 + drwtNo5 + drwtNo6;
        if(includeBonus) totalSum += bnusNo;
        return  totalSum;
    }
}
