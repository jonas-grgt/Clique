package io.github.kusoroadeolu.clique.core.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static io.github.kusoroadeolu.clique.core.utils.Constants.ESC;
import static org.junit.jupiter.api.Assertions.*;

class MarkupPreProcessorTest {

    private MarkupPreProcessor processor;

    // Mirror the constants used internally
    private static final String ANSI_SENTINEL = "]\uFFFF";
    private static final String ESCAPE_PLACEHOLDER = "\uE000";

    @BeforeEach
    void setUp() {
        processor = new MarkupPreProcessor();
    }

    // --- preProcess ---

    @ParameterizedTest
    @NullAndEmptySource
    void preProcess_nullOrEmpty_returnsAsIs(String input) {
        assertEquals(input, processor.preProcess(input));
    }

    @Test
    void preProcess_plainText_returnsUnchanged() {
        String input = "hello world";
        assertEquals(input, processor.preProcess(input));
    }

    @Test
    void preProcess_escapedBracket_replacedWithPlaceholder() {
        // "\[" should become ESCAPE_PLACEHOLDER
        String input = "\\[tag]";
        String result = processor.preProcess(input);
        assertTrue(result.contains(ESCAPE_PLACEHOLDER),
                "Escaped bracket should be replaced with placeholder");
        assertFalse(result.contains("\\["),
                "Original escape sequence should be removed");
    }

    @Test
    void preProcess_ansiCode_appendsSentinel() {
        // ESC + [ + ... + m  -->  sentinel appended after 'm'
        String input = ESC + "[31m";
        String result = processor.preProcess(input);
        assertTrue(result.contains(ANSI_SENTINEL),
                "ANSI end char should be followed by sentinel");
    }

    @Test
    void preProcess_unclosedAnsiCode() {
        // ESC + [ + ... + next text, ansi code doesn't contain an 'm'
        String input = ESC + "[31";
        String result = processor.preProcess(input);
        assertFalse(result.contains(ANSI_SENTINEL),
                "ANSI end char should be followed by sentinel");
    }

    @Test
    void preProcess_multipleAnsiCodes_allGetSentinel() {
        String input = ESC + "[31m" + ESC + "[0m";
        String result = processor.preProcess(input);
        long sentinelCount = countOccurrences(result);
        assertEquals(2, sentinelCount, "Each ANSI code should get its own sentinel");
    }

    @Test
    void preProcess_ansiCodeWithEscapedBracket_bothHandled() {
        String input = ESC + "[32m" + "\\[notATag]";
        String result = processor.preProcess(input);
        assertTrue(result.contains(ANSI_SENTINEL));
        assertTrue(result.contains(ESCAPE_PLACEHOLDER));
    }

    @Test
    void preProcess_noAnsiNoEscape_returnsOriginal() {
        String input = "Just some [normal] text";
        assertEquals(input, processor.preProcess(input));
    }

    @Test
    void preProcess_escCharWithoutBracket_notReplaced() {
        String input = "back\\slash";
        String result = processor.preProcess(input);
        assertTrue(result.contains("\\"), "Lone backslash should remain");
    }

    // --- postProcess ---

    @ParameterizedTest
    @NullAndEmptySource
    void postProcess_nullOrEmpty_returnsAsIs(String input) {
        assertEquals(input, processor.postProcess(input));
    }

    @Test
    void postProcess_escapePlaceholder_restoredToBracket() {
        String input = ESCAPE_PLACEHOLDER + "tag]";
        String result = processor.postProcess(input);
        assertEquals("[tag]", result);
    }

    @Test
    void postProcess_ansiSentinel_removed() {
        String input = "text" + ANSI_SENTINEL + "more";
        String result = processor.postProcess(input);
        assertFalse(result.contains(ANSI_SENTINEL));
        assertEquals("textmore", result);
    }

    @Test
    void postProcess_bothPlaceholderAndSentinel_bothCleaned() {
        String input = ESCAPE_PLACEHOLDER + "bold]" + ANSI_SENTINEL;
        String result = processor.postProcess(input);
        assertEquals("[bold]", result);
    }

    @Test
    void postProcess_plainText_unchanged() {
        String input = "nothing special here";
        assertEquals(input, processor.postProcess(input));
    }

    // --- round-trip: preProcess -> postProcess ---

    @Test
    void roundTrip_escapedBracket_restoredCorrectly() {
        String input = "\\[bold]text[/bold]";
        String preProcessed = processor.preProcess(input);
        String postProcessed = processor.postProcess(preProcessed);
        assertEquals("[bold]text[/bold]", postProcessed);
    }

    @Test
    void roundTrip_ansiCode_sentinelCleanedUp() {
        String ansi = ESC + "[31m";
        String preProcessed = processor.preProcess(ansi);
        String postProcessed = processor.postProcess(preProcessed);
        assertFalse(postProcessed.contains(ANSI_SENTINEL));
        // The ESC sequence itself should still be there
        assertTrue(postProcessed.contains(String.valueOf(ESC)));
    }

    @Test
    void roundTrip_mixedInput_cleanOutput() {
        String input = "\\[tag] " + ESC + "[1m" + "bold" + ESC + "[0m";
        String result = processor.postProcess(processor.preProcess(input));
        assertFalse(result.contains(ANSI_SENTINEL));
        assertFalse(result.contains(ESCAPE_PLACEHOLDER));
        assertTrue(result.startsWith("[tag]"));
    }

    // --- helpers ---

    private long countOccurrences(String text) {
        int count = 0, idx = 0;
        while ((idx = text.indexOf(MarkupPreProcessorTest.ANSI_SENTINEL, idx)) != -1) {
            count++;
            idx += MarkupPreProcessorTest.ANSI_SENTINEL.length();
        }
        return count;
    }
}