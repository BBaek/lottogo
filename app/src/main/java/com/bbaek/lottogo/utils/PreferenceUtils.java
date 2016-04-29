package com.bbaek.lottogo.utils;

import android.content.Context;

import java.util.List;

/**
 * Created by woonsungbaek on 2015. 12. 4..
 */
public class PreferenceUtils extends BasePreferenceUtils
{
    private static PreferenceUtils _instance = null;

    private static final String DRWT_NO_1 = "drwt_no_1";
    private static final String DRWT_NO_2 = "drwt_no_2";
    private static final String DRWT_NO_3 = "drwt_no_3";
    private static final String DRWT_NO_4 = "drwt_no_4";
    private static final String DRWT_NO_5 = "drwt_no_5";
    private static final String DRWT_NO_6 = "drwt_no_6";
    private static final String DRWT_NO_BONUS = "drwt_no_bouns";

    public static synchronized PreferenceUtils instance(Context context)
    {
        if (_instance == null)
            _instance = new PreferenceUtils(context);
        return _instance;
    }

    protected PreferenceUtils(Context context)
    {
        super(context);
    }

    public void putDrwtNoBouns(int value)
    {
        put(DRWT_NO_BONUS, value);
    }

    public int getDrwtNoBouns()
    {
        return get(DRWT_NO_BONUS, 0);
    }

    public void putDrwtNo1(int value)
    {
        put(DRWT_NO_1, value);
    }

    public int getDrwtNo1()
    {
        return get(DRWT_NO_1, 0);
    }

    public void putDrwtNo2(int value)
    {
        put(DRWT_NO_2, value);
    }

    public int getDrwtNo2()
    {
        return get(DRWT_NO_2, 0);
    }

    public void putDrwtNo3(int value)
    {
        put(DRWT_NO_3, value);
    }

    public int getDrwtNo3()
    {
        return get(DRWT_NO_3, 0);
    }

    public void putDrwtNo4(int value)
    {
        put(DRWT_NO_4, value);
    }

    public int getDrwtNo4()
    {
        return get(DRWT_NO_4, 0);
    }

    public void putDrwtNo5(int value)
    {
        put(DRWT_NO_5, value);
    }

    public int getDrwtNo5()
    {
        return get(DRWT_NO_5, 0);
    }

    public void putDrwtNo6(int value)
    {
        put(DRWT_NO_6, value);
    }

    public int getDrwtNo6()
    {
        return get(DRWT_NO_6, 0);
    }

    public void clearDrwtNos() {
        putDrwtNo1(0);
        putDrwtNo2(0);
        putDrwtNo3(0);
        putDrwtNo4(0);
        putDrwtNo5(0);
        putDrwtNo6(0);
    }

    public void initDrwtNos(int drwtNo1, int drwtNo2, int drwtNo3, int drwtNo4, int drwtNo5, int drwtNo6) {
        putDrwtNo1(drwtNo1);
        putDrwtNo2(drwtNo2);
        putDrwtNo3(drwtNo3);
        putDrwtNo4(drwtNo4);
        putDrwtNo5(drwtNo5);
        putDrwtNo6(drwtNo6);
    }

    public void initDrwtNos(List<Integer> list) {
        putDrwtNo1(list.get(0));
        putDrwtNo2(list.get(1));
        putDrwtNo3(list.get(2));
        putDrwtNo4(list.get(3));
        putDrwtNo5(list.get(4));
        putDrwtNo6(list.get(5));
    }
}
