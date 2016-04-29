package com.bbaek.lottogo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by woonsungbaek on 2015. 11. 15..
 */
public class BasePreferenceUtils
{
    private BBLogger logger = new BBLogger(getClass().getSimpleName());

    private SharedPreferences _sharedPreferences;

    protected BasePreferenceUtils(Context context)
    {
        super();
        _sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * key 수동 설정
     *
     * @param key
     *           키 값
     * @param value
     *           내용
     */
    protected void put(String $key, String $value)
    {
        logger.info("put: key[" + $key + "]/value[" + $value + "]");

        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putString($key, $value);
        editor.commit();
    }

    /**
     * String 값 가져오기
     *
     * @param key
     *           키 값
     * @return String (기본값 null)
     */
    protected String get(String $key)
    {
        return _sharedPreferences.getString($key, null);
    }

    /**
     * key 설정
     *
     * @param key
     *           키 값
     * @param value
     *           내용
     */
    protected void put(String $key, boolean $value)
    {
        logger.info("put: key[" + $key + "]/value[" + $value + "]");

        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putBoolean($key, $value);
        editor.commit();
    }

    /**
     * Boolean 값 가져오기
     *
     * @param key
     *           키 값
     * @param defValue
     *           기본값
     * @return Boolean
     */
    protected boolean get(String $key, boolean $default)
    {
        return _sharedPreferences.getBoolean($key, $default);
    }

    /**
     * key 설정
     *
     * @param key
     *           키 값
     * @param value
     *           내용
     */
    protected void put(String $key, int $value)
    {
        logger.info("put: key[" + $key + "]/value[" + $value + "]");

        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putInt($key, $value);
        editor.commit();
    }

    /**
     * int 값 가져오기
     *
     * @param key
     *           키 값
     * @param defValue
     *           기본값
     * @return int
     */
    protected int get(String $key, int $default)
    {
        return _sharedPreferences.getInt($key, $default);
    }

    /**
     * key 설정
     *
     * @param key
     *           키 값
     * @param value
     *           내용
     */
    protected void put(String $key, long $value)
    {
        logger.info("put: key[" + $key + "]/value[" + $value + "]");

        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putLong($key, $value);
        editor.commit();
    }

    /**
     * long 값 가져오기
     *
     * @param key
     *           키 값
     * @param defValue
     *           기본값
     * @return int
     */
    protected long get(String $key, long $default)
    {
        return _sharedPreferences.getLong($key, $default);
    }
}