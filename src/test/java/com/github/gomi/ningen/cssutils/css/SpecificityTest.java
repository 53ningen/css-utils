package com.github.gomi.ningen.cssutils.css;

import fj.Monoid;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SpecificityTest {

    @Test(expected = IllegalArgumentException.class)
    public void testSpecificityArgumentsMustNotBeNegativeA() {
        Specificity.of(-1, 0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSpecificityArgumentsMustNotBeNegativeB() {
        Specificity.of(0, -1, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSpecificityArgumentsMustNotBeNegativeC() {
        Specificity.of(0, 0, -1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSpecificityArgumentsMustNotBeNegativeD() {
        Specificity.of(0, 0, 0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSpecificityArgumentsMustNotBeNegative() {
        Specificity.of(-1, -1, -1, -1);
    }

    @Test
    public void testEquals() {
        assertTrue( Specificity.of(0, 0, 0, 0).equals(Specificity.of(0, 0, 0, 0)));
        assertTrue( Specificity.of(1, 2, 3, 4).equals(Specificity.of(1, 2, 3, 4)));
        assertFalse(Specificity.of(0, 0, 0, 0).equals(Specificity.of(0, 0, 0, 1)));
        assertFalse(Specificity.of(1, 2, 3, 4).equals(Specificity.of(4, 3, 2, 1)));
    }

    @Test
    public void testCompareToGraterThanCase() {
        assertTrue(Specificity.of(10, 10, 10, 10).compareTo(Specificity.of( 0,  0,  0,  0)) > 0);
        assertTrue(Specificity.of(10, 10, 10, 10).compareTo(Specificity.of(10,  0,  0,  0)) > 0);
        assertTrue(Specificity.of(10, 10, 10, 10).compareTo(Specificity.of( 0, 10,  0,  0)) > 0);
        assertTrue(Specificity.of(10, 10, 10, 10).compareTo(Specificity.of( 0,  0, 10,  0)) > 0);
        assertTrue(Specificity.of(10, 10, 10, 10).compareTo(Specificity.of( 0,  0,  0, 10)) > 0);
    }

    @Test
    public void testCompareToEqualCase() {
        assertThat(Specificity.of(0, 0, 0, 0).compareTo(Specificity.of(0, 0, 0, 0)), is(0));
        assertThat(Specificity.of(1, 2, 3, 4).compareTo(Specificity.of(1, 2, 3, 4)), is(0));
    }

    @Test
    public void testCompareToLessThanCase() {
        assertTrue(Specificity.of( 0,  0,  0,  0).compareTo(Specificity.of(10, 10, 10, 10)) < 0);
        assertTrue(Specificity.of(10,  0,  0,  0).compareTo(Specificity.of(10, 10, 10, 10)) < 0);
        assertTrue(Specificity.of( 0, 10,  0,  0).compareTo(Specificity.of(10, 10, 10, 10)) < 0);
        assertTrue(Specificity.of( 0,  0, 10,  0).compareTo(Specificity.of(10, 10, 10, 10)) < 0);
        assertTrue(Specificity.of( 0,  0,  0, 10).compareTo(Specificity.of(10, 10, 10, 10)) < 0);
    }

    @Test
    public void testCompareToSymmetry() {
        final Specificity a = Specificity.of(1, 1, 1, 1);
        final Specificity b = Specificity.of(1, 1, 1, 2);
        assertThat(a.compareTo(b), is(-b.compareTo(a)));
        assertThat(b.compareTo(a), is(-a.compareTo(b)));
    }

    @Test
    public void testCompareToTransitivity() {
        final Specificity a = Specificity.of(1, 1, 2, 1);
        final Specificity b = Specificity.of(1, 1, 1, 2);
        final Specificity c = Specificity.of(1, 1, 1, 1);
        assertTrue(a.compareTo(b) > 0);
        assertTrue(b.compareTo(c) > 0);
        assertTrue(a.compareTo(c) > 0);
    }

    @Test
    public void testSum() {
        final Specificity a = Specificity.of(1, 1, 2, 1);
        final Specificity b = Specificity.of(1, 1, 1, 2);
        assertThat(Specificity.getSum().sum(a, b), is(Specificity.of(2, 2, 3, 3)));
    }

    @Test
    public void testSumAssociativity() {
        final Specificity a = Specificity.of(1, 2, 3, 4);
        final Specificity b = Specificity.of(5, 6, 7, 8);
        final Specificity c = Specificity.of(9,10,11,12);
        final Monoid<Specificity> op = Specificity.getSum();
        assertThat(op.sum(op.sum(a, b), c), is(op.sum(a, op.sum(b, c))));
    }

    @Test
    public void testSumIdentity() {
        final Specificity a = Specificity.of(1, 2, 3, 4);
        final Monoid<Specificity> op = Specificity.getSum();
        assertThat(op.sum(a, op.zero()), is(op.sum(op.zero(), a)));
        assertThat(op.sum(a, op.zero()), is(a));
        assertThat(op.sum(op.zero(), a), is(a));
    }

}
