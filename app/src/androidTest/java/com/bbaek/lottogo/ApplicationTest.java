package com.bbaek.lottogo;

import android.app.Application;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.bbaek.lottogo.Repository.LottoRepository;
import com.bbaek.lottogo.model.Lotto;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.RealmResults;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    @Test
    public void select() throws Exception {
        RealmResults results = new LottoRepository().selectAll();
        int assetsFileSize = 694;
        assertTrue(results.size() >= assetsFileSize);
    }
}