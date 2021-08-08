package com.coinbase.orderbookservice.bean;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.math.BigDecimal.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoundedTreeMapTest {

    @Test
    void sizeStaysConstant() {
        BoundedTreeMap<BigDecimal, BigDecimal> map = new BoundedTreeMap<>(2);
        map.put(ONE, ONE);
        map.put(TEN, TEN);
        map.put(ZERO, ZERO);

        assertEquals(2, map.size());
    }

    @Test
    void sizeStaysConstant_reverse() {
        BoundedTreeMap<BigDecimal, BigDecimal> map = new BoundedTreeMap<>(2, true);
        map.put(ONE, ONE);
        map.put(TEN, TEN);
        map.put(ZERO, ZERO);

        assertEquals(2, map.size());
    }

    @Test
    void elementsAreOrdered() {
        BoundedTreeMap<BigDecimal, BigDecimal> map = new BoundedTreeMap<>(2);
        map.put(TEN, TEN);
        map.put(ZERO, ZERO);
        assertEquals(map.keySet(), sortedSet(ZERO, TEN));
    }

    @Test
    void elementsAreOrdered_reverse() {
        BoundedTreeMap<BigDecimal, BigDecimal> map = new BoundedTreeMap<>(2, true);
        map.put(ZERO, ZERO);
        map.put(TEN, TEN);
        assertEquals(map.keySet(), sortedSet(TEN, ZERO));
    }

    @Test
    void elementAddedInRightPosition() {
        BoundedTreeMap<BigDecimal, BigDecimal> map = new BoundedTreeMap<>(3);
        map.put(TEN, TEN);
        map.put(ZERO, ZERO);
        map.put(ONE, ONE);
        assertEquals(map.keySet(), sortedSet(ZERO, ONE, TEN));
    }

    @Test
    void elementAddedInRightPosition_reverse() {
        BoundedTreeMap<BigDecimal, BigDecimal> map = new BoundedTreeMap<>(3, true);
        map.put(TEN, TEN);
        map.put(ZERO, ZERO);
        map.put(ONE, ONE);
        assertEquals(map.keySet(), sortedSet(TEN, ZERO, ONE));
    }

    @Test
    void elementNotInRangeDiscardedIfMoreThenMaxSize() {
        BoundedTreeMap<BigDecimal, BigDecimal> map = new BoundedTreeMap<>(2);
        map.put(TEN, TEN);
        map.put(ZERO, ZERO);
        map.put(ONE, ONE);
        assertEquals(map.keySet(), sortedSet(ZERO, ONE));
    }

    @Test
    void elementNotInRangeDiscardedIfMoreThenMaxSize_reverse() {
        BoundedTreeMap<BigDecimal, BigDecimal> map = new BoundedTreeMap<>(2, true);
        map.put(TEN, TEN);
        map.put(ZERO, ZERO);
        map.put(ONE, ONE);
        assertEquals(map.keySet(), sortedSet(TEN, ONE));
    }

    @Test
    void checkUpdate_whenMapNotOverflown() {
        BoundedTreeMap<BigDecimal, BigDecimal> map = new BoundedTreeMap<>(2);
        assertTrue(map.put(TEN, TEN));
        assertTrue(map.put(ZERO, ZERO));
    }

    @Test
    void checkUpdate_whenSameElementAddedAgain() {
        BoundedTreeMap<BigDecimal, BigDecimal> map = new BoundedTreeMap<>(2);
        assertTrue(map.put(TEN, TEN));
        assertTrue(map.put(ZERO, ZERO));
        assertFalse(map.put(TEN, TEN));
    }

    @Test
    void checkUpdate_whenNewElementAddedAlsoGetsDiscarded() {
        BoundedTreeMap<BigDecimal, BigDecimal> map = new BoundedTreeMap<>(2);
        assertTrue(map.put(ZERO, ZERO));
        assertTrue(map.put(ONE, ONE));
        assertFalse(map.put(TEN, TEN));
    }

    @Test
    void checkUpdate_whenOlderElementGetDiscarded_whenNewAdded() {
        BoundedTreeMap<BigDecimal, BigDecimal> map = new BoundedTreeMap<>(2);
        assertTrue(map.put(ZERO, ZERO));
        assertTrue(map.put(TEN, TEN));
        assertTrue(map.put(ONE, ONE));
    }

    @Test
    void checkUpdate_whenValueGetsUpdated() {
        BoundedTreeMap<BigDecimal, BigDecimal> map = new BoundedTreeMap<>(2);
        assertTrue(map.put(ZERO, ZERO));
        assertTrue(map.put(TEN, TEN));
        assertTrue(map.put(ZERO, TEN));
    }

    @Test
    void removeWhenExists() {
        BoundedTreeMap<BigDecimal, BigDecimal> map = new BoundedTreeMap<>(2);
        assertTrue(map.put(ZERO, ZERO));
        assertTrue(map.put(TEN, TEN));
        assertTrue(map.remove(ZERO));
        assertEquals(map.keySet(), sortedSet(TEN));
    }

    @Test
    void removeWhenKeyDoesntExists() {
        BoundedTreeMap<BigDecimal, BigDecimal> map = new BoundedTreeMap<>(2);
        assertTrue(map.put(TEN, TEN));
        assertFalse(map.remove(ZERO));
    }

    private SortedSet<BigDecimal> sortedSet(BigDecimal... values) {
        SortedSet<BigDecimal> expected = new TreeSet<>();
        Arrays.stream(values).forEach(v-> expected.add(v));
        return expected;
    }
}