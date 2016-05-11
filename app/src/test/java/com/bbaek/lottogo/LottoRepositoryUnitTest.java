package com.bbaek.lottogo;

import com.bbaek.lottogo.Repository.LottoRepository;
import com.bbaek.lottogo.activity.MyApplication;
import com.bbaek.lottogo.model.Lotto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Suite;

import io.realm.RealmResults;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */

public class LottoRepositoryUnitTest {
    LottoRepository repository;

    @Before
    public void init() {
        repository = new LottoRepository();
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        assertFalse(true);
    }

    @Test
    public void select() throws Exception {
        RealmResults<Lotto> lottos = repository.selectAll();
        assertEquals(702, lottos.size());
        assertFalse(true);
    }
}