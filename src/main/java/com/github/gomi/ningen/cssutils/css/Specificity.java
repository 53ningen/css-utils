package com.github.gomi.ningen.cssutils.css;

/**
 * CSS Selector Specificity
 */
public class Specificity implements Comparable<Specificity> {

    final int a;
    final int b;
    final int c;
    final int d;

    public static Specificity of(final int a, final int b, final int c, final int d) {
        return new Specificity(a, b, c, d);
    }

    public static Specificity of(final int b, final int c, final int d) {
        return new Specificity(b, c, d);
    }

    private Specificity(final int a, final int b, final int c, final int d) {
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
