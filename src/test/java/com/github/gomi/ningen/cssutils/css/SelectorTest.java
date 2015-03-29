package com.github.gomi.ningen.cssutils.css;

import com.google.common.collect.Lists;
import com.helger.css.ECSSVersion;
import com.helger.css.decl.CascadingStyleSheet;
import com.helger.css.reader.CSSReader;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class SelectorTest {

    static final String GROUP_OF_SELECTORS_CSS_PATH = "/css/group_of_selectors.css";

    @Test
    public void testInstantiationGroupOfSelectors() throws IOException {
        final CascadingStyleSheet css = CSSReader.readFromString(IOUtils.toString(getClass().getResourceAsStream(GROUP_OF_SELECTORS_CSS_PATH)), ECSSVersion.CSS30);
        assertNotNull(css);
        final List<Selector> selector = css.getAllStyleRules().stream()
                .map(Selector::of)
                .collect(Collectors.toList())
                .get(0);
        final List<Selector> expected = Lists.newArrayList(
                new Selector(".index h1"),
                new Selector(".index h2"),
                new Selector(".index h3"),
                new Selector(".index h4"),
                new Selector(".index h5"),
                new Selector(".index h6")
        );
        assertThat(selector, is(expected));
    }
}