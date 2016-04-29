package com.bbaek.lottogo.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by woonsungbaek on 2015. 12. 1..
 */
public class DHFragmentUtils {

    private final String TAG = "===== DRHOUSE.DEBUG." + this.getClass().getSimpleName();

    public void replaceFragment(FragmentActivity parent, int targetContainer, Fragment srcFragment, Bundle data, String tag) {
        Log.i(TAG, "Replace Fragment ID : " + String.valueOf(srcFragment.getId()));

        if (data != null) {
            srcFragment.setArguments(data);
        }

        android.support.v4.app.FragmentManager fragmentManager = parent.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(targetContainer, srcFragment, tag);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void addFragment(FragmentActivity parent, int targetContainer, Fragment srcFragment, Bundle data, String tag) {
        Log.i(TAG, "Add Fragment ID : " + String.valueOf(srcFragment.getId()));

        if (data != null) {
            srcFragment.setArguments(data);
        }

        android.support.v4.app.FragmentManager fragmentManager = parent.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(targetContainer, srcFragment, tag);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}