package com.github.gomi.ningen.cssutils.css;

import com.helger.css.ECSSVersion;
import com.helger.css.decl.CSSStyleRule;
import com.helger.css.writer.CSSWriterSettings;

import java.util.List;
import java.util.stream.Collectors;

public class Selector {

    final String selector;
    static final CSSWriterSettings settings = new CSSWriterSettings(ECSSVersion.CSS30);

    Selector(final String selector) {
        this.selector = selector;
    }

    public static List<Selector> of(final CSSStyleRule rule) {
        if (rule == null) throw new NullPointerException();
        return rule.getAllSelectors().stream()
                .map(s -> new Selector(s.getAsCSSString(settings, 0)))
                .collect(Collectors.toList());
    }

    public String getSelector() {
        return selector;
    }

    @Override
    public String toString() {
        return String.format("{selector:%s}", selector);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Selector)) return false;

        Selector selector1 = (Selector) o;

        if (!selector.equals(selector1.selector)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return selector.hashCode();
    }
}
