package io.github.kusoroadeolu.clique.frame;

import io.github.kusoroadeolu.clique.config.FrameAlign;
import io.github.kusoroadeolu.clique.core.display.Component;
import io.github.kusoroadeolu.clique.core.utils.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.github.kusoroadeolu.clique.core.utils.Constants.NEWLINE;
import static org.junit.jupiter.api.Assertions.*;

class FrameTest {

    private DefaultFrame frame;

    @BeforeEach
    public void setup(){
        this.frame = new DefaultFrame();
    }

    private static Component component(String output) {
        return () -> output;
    }

    private static String[] lines(String rendered) {
        return rendered.split(NEWLINE);
    }

    private static String stripAnsi(String s) {
        return StringUtils.stripAnsi(s);
    }

    // -------------------------
    // Validation tests
    // -------------------------

    @Test
    void throwsWhenTitleWiderThanFrameAtRenderTime() {
        assertThrows(IllegalArgumentException.class, () ->
                frame.width(5)
                        .title("This title is way too long")
                        .nest("hi")
                        .render()
        );
    }

    @Test
    void throwsWhenNodeContentWiderThanExplicitWidth() {
        assertThrows(IllegalArgumentException.class, () ->
                frame.width(3)
                        .nest("this string is too wide")
                        .get()
        );
    }

    @Test
    void throwsWhenWidthIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                frame.width(-1)
        );
    }

    // -------------------------
    // Title rendering tests
    // -------------------------

    @Test
    void frameWithNoTitleHasPlainTopBorder() {
        String rendered = stripAnsi(
                frame.nest("hello")
                        .get()
        );
        String topLine = lines(rendered)[0];
        // Top border should have no spaces, since no title has been embedded
        assertFalse(topLine.contains(" "));
    }

    @Test
    void frameWithTitleEmbedsTitleInTopBorder() {
        String title = "MyApp";
        String rendered = stripAnsi(
                frame.title(title)
                        .nest("hello")
                        .get()
        );
        String topLine = lines(rendered)[0];
        assertTrue(topLine.contains(title));
        assertTrue(topLine.contains(" "));
    }

    @Test
    void titleIsSurroundedBySpacesInTopBorder() {
        String rendered = stripAnsi(
                frame.title("T")
                        .nest("some content")
                        .get()
        );
        String topLine = lines(rendered)[0];
        assertTrue(topLine.contains(" T "));
    }

    @Test
    void topAndBottomBorderHaveSameWidth() {
        String rendered = stripAnsi(
                frame.title("Title")
                        .nest("hello world")
                        .get()
        );
        String[] ls = lines(rendered);
        assertEquals(ls[0].length(), ls[ls.length - 1].length());
    }

    // -------------------------
    // Nested component tests
    // -------------------------

    @Test
    void nestedComponentOutputAppearsInFrame() {
        Component comp = component("foo");
        String rendered = stripAnsi(
                frame.nest(comp)
                        .get()
        );
        assertTrue(rendered.contains("foo"));
    }

    @Test
    void allContentLinesHaveSameWidth() {
        String rendered = stripAnsi(
                frame.nest("short")
                        .nest("a much longer line")
                        .get()
        );
        String[] ls = lines(rendered);
        int expectedWidth = ls[0].length();
        for (String line : ls) {
            assertEquals(expectedWidth, line.length(), "Line width mismatch: [" + line + "]");
        }
    }

    @Test
    void frameWidthDerivedFromWidestNode() {
        String wide = "a much longer string";
        String narrow = "hi";
        String rendered = stripAnsi(
                frame.nest(wide)
                        .nest(narrow)
                        .get()
        );
        // Every line should be the same width, driven by the wider content
        String[] ls = lines(rendered);
        int expectedWidth = ls[0].length();
        for (String line : ls) {
            assertEquals(expectedWidth, line.length());
        }
    }

    @Test
    void nestedComponentWithMultilineOutput() {
        Component comp = component("line one\nline two\nline three");
        String rendered = stripAnsi(
                frame.nest(comp)
                        .get()
        );
        assertTrue(rendered.contains("line one"));
        assertTrue(rendered.contains("line two"));
        assertTrue(rendered.contains("line three"));
    }

    @Test
    void frameIsCachedOnSubsequentGetCalls() {
        DefaultFrame frame = this.frame.nest("hello");
        String first = frame.get();
        String second = frame.get();
        assertSame(first, second); // same instance due to lazy cache
    }

    @Test
    void cachedFrameIsNulledAfterModification() {
        DefaultFrame frame = this.frame.nest("hello");
        String first = frame.get();
        assertNotNull(first);
        frame.nest(frame);
        assertNotNull(frame.get()); // same instance due to lazy cache
    }

    @Test
    void leftAlignedNodeIsFlushToLeftBorder() {
        String rendered = stripAnsi(
                frame.nest("hi", FrameAlign.LEFT)
                        .nest("much wider content here")
                        .get()
        );
        // Line with "hi" should have it immediately after the vline with no leading spaces
        String hiLine = Arrays.stream(lines(rendered))
                .filter(l -> l.contains("hi") && !l.contains("much"))
                .findFirst()
                .get();
        // after the vline char, next char should be 'h'
        assertEquals('h', hiLine.charAt(1));
    }
}