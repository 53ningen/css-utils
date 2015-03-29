package com.github.gomi.ningen.cssutils.css;

import com.helger.css.ECSSVersion;
import com.helger.css.decl.ICSSSelectorMember;
import com.helger.css.writer.CSSWriterSettings;
import fj.Monoid;

import java.util.List;

/**
 * CSS Selector Specificity
 */
public class Specificity implements Comparable<Specificity> {

    /**
     * a:
     * b: number of id selectors which start with "#"
     * c: number of class, attribute, and pseudo class selectors
     * d: number of type, and pseudo element selectors
     */
    final int a;
    final int b;
    final int c;
    final int d;

    static final CSSWriterSettings cssWriterSettings = new CSSWriterSettings(ECSSVersion.CSS30);

    public static Specificity of(final int a, final int b, final int c, final int d) {
        return new Specificity(a, b, c, d);
    }

    public static Specificity of(final int b, final int c, final int d) {
        return new Specificity(b, c, d);
    }

    public static Specificity of(final List<ICSSSelectorMember> selectorMembers) {
        return null;
    }

    public static Specificity of(final ICSSSelectorMember selectorMember) {
        return of(selectorMember.getAsCSSString(cssWriterSettings, 0));
    }

    public static Specificity of(final String selectorMember) {
        if (selectorMember.startsWith("#")) return new Specificity(1, 0, 0);
        return null;
    }

    Specificity(final int a, final int b, final int c, final int d) {
        if (a < 0 | b < 0 | c < 0 | d < 0)
            throw new IllegalArgumentException(String.format("specificity must be zero or positive number."));

        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    private Specificity(final int b, final int c, final int d) {
        this(0, b, c, d);
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public int getD() {
        return d;
    }

    public static Monoid<Specificity> getSum() {
        return Monoid.monoid(
                (m1, m2) -> of(m1.a + m2.a, m1.b + m2.b, m1.c + m2.c, m1.d + m2.d), // sum
                of(0, 0, 0, 0) // zero
        );
    }

    @Override
    public int compareTo(Specificity o) {
        if (o == null) throw new NullPointerException();

        if (o.equals(this)) return 0;
        if (a > o.a) return 1;
        if (b > o.b) return 1;
        if (c > o.c) return 1;
        if (d > o.d) return 1;
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specificity)) return false;

        Specificity that = (Specificity) o;

        if (a != that.a) return false;
        if (b != that.b) return false;
        if (c != that.c) return false;
        if (d != that.d) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = a;
        result = 31 * result + b;
        result = 31 * result + c;
        result = 31 * result + d;
        return result;
    }
}
