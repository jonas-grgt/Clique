package io.github.kusoroadeolu.clique.internal.markup;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.internal.exception.UnidentifiedStyleException;
import io.github.kusoroadeolu.clique.style.StyleCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.github.kusoroadeolu.clique.internal.markup.Tokenizer.tokenize;
import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {
    
    private static final String DELIMITER = ",";

    @BeforeEach
    void setUp() {
    }

    // ── Happy path ────────────────────────────────────────────────────────────

    @Test
    void singleValidTag_producesOneToken() {
        var result = tokenize("[red]hello[/]", DELIMITER, false);
        assertEquals(2, result.tokens().size()); // [red] and [/]
    }

    @Test
    void multipleValidTags_producesCorrectTokenCount() {
        var result = tokenize("[red]hello[/] [bold]world[/]", DELIMITER, false);
        assertEquals(4, result.tokens().size());
    }

    @Test
    void combinedStyles_allResolve() {
        var result = tokenize("[red, bold, ul]text[/]", DELIMITER, false);
        assertEquals(2, result.tokens().size()); //red bold ul + reset
        assertEquals(3, result.tokens().getFirst().styles().size());
    }

    @Test
    void brightColor_resolvesCorrectly() {
        var result = tokenize("[*red]text[/]", DELIMITER, false);
        assertEquals(2, result.tokens().size());
    }

    @Test
    void backgroundColor_resolvesCorrectly() {
        var result = tokenize("[bg_red]text[/]", DELIMITER, false);
        assertEquals(2, result.tokens().size());
    }

    @Test
    void brightBackground_resolvesCorrectly() {
        var result = tokenize("[*bg_red]text[/]", DELIMITER, false);
        assertEquals(2, result.tokens().size());
    }

    @Test
    void resetTag_resolvesCorrectly() {
        var result = tokenize("[/]", DELIMITER, false);
        assertEquals(1, result.tokens().size());
    }

    @Test
    void spaceDelimiter_resolvesStyles() {
        var result = tokenize("[red bold]text[/]", " ", false);
        assertEquals(2, result.tokens().size());
        assertEquals(2, result.tokens().getFirst().styles().size());
    }

    // ── Malformed / edge case input ───────────────────────────────────────────

    @Test
    void nullInput_returnsEmptyResult() {
        var result = tokenize(null, DELIMITER, false);
        assertTrue(result.tokens().isEmpty());
    }

    @Test
    void emptyString_returnsEmptyResult() {
        var result = tokenize("", DELIMITER, false);
        assertTrue(result.tokens().isEmpty());
    }

    @Test
    void noTags_returnsEmptyResult() {
        var result = tokenize("just plain text", DELIMITER, false);
        assertTrue(result.tokens().isEmpty());
    }

    @Test
    void emptyBrackets_producesNoToken() {
        var result = tokenize("[]text", DELIMITER, false);
        assertTrue(result.tokens().isEmpty());
    }

    @Test
    void unknownStyle_lenientMode_isIgnored() {
        var result = tokenize("[unknownstyle]text[/]", DELIMITER, false);
        // unknown tag should produce no token, not throw
        assertDoesNotThrow(() -> tokenize("[unknownstyle]text[/]", DELIMITER, false));
        assertEquals(1, result.tokens().size()); //RESET TAG, unknown tag is ignored
        assertEquals(StyleCode.RESET, result.tokens().getFirst().styles().getFirst());
    }

    @Test
    void unknownStyle_strictMode_throws() {
        assertThrows(UnidentifiedStyleException.class,
                () -> tokenize("[bol]text[/]", DELIMITER, true));
    }

    @Test
    void unclosedTag_doesNotProduceToken() {
        // [red with no closing ] — should not blow up, just no token
        assertDoesNotThrow(() -> tokenize("[red hello", DELIMITER, false));
        var result = tokenize("[red hello", DELIMITER, false);
        assertTrue(result.tokens().isEmpty());
    }

    @Test
    void unopenedClosingBracket_isIgnored() {
        var result = tokenize("text]more text", DELIMITER, false);
        assertTrue(result.tokens().isEmpty());
    }

    @Test
    void nestedBrackets_outerTagSkipped() {
        // [[red]] — nested, should be skipped entirely
        var result = tokenize("[[red]]text", DELIMITER, false);
        assertTrue(result.tokens().isEmpty());
    }

    @Test
    void nestedBrackets_subsequentTagsAreIgnored() {
        // After a nested/bad tag, valid tags after it should still work
        var result = tokenize("[[red]] [bold]text[/]", DELIMITER, false);
        Clique.parser().print("[[red]] [bold]text[/]");
    }

    @Test
    void deeplyNestedBrackets_handledGracefully() {
        assertDoesNotThrow(() -> tokenize("[[[[red]]]]text", DELIMITER, false));
    }

    // ── Whitespace handling ───────────────────────────────────────────────────

    @Test
    void stylesWithExtraWhitespace_stillResolve() {
        var result = tokenize("[ red , bold ]text[/]", DELIMITER, false);
        assertEquals(2, result.tokens().size());
        assertEquals(2, result.tokens().getFirst().styles().size());
    }

    @Test
    void uppercaseStyles_resolveCorrectly() {
        // parser lowercases internally, so [RED] should work
        var result = tokenize("[RED]text[/]", DELIMITER, false);
        assertEquals(2, result.tokens().size());
    }

    // ── Stress / volume ───────────────────────────────────────────────────────

    @Test
    void manyTagsInOneString_allResolved() {
        String input = "[red]a[/][bold]b[/][cyan]c[/][ul]d[/][italic]e[/]".repeat(100);
        var result = tokenize(input, DELIMITER, false);
        assertEquals(1000, result.tokens().size()); // 5 tags * 2 ([open] + [/]) * 100
    }

    @Test
    void veryLongStringNoTags_handledEfficiently() {
        String input = "a".repeat(100_000);
        assertDoesNotThrow(() -> tokenize(input, DELIMITER, false));
    }

    @Test
    void veryLongStringWithTags_parsesCorrectly() {
        String chunk = "[red]hello world[/] ";
        String input = chunk.repeat(10_000);
        var result = tokenize(input, DELIMITER, false);
        assertEquals(20_000, result.tokens().size());
    }

    // ── Token position correctness ────────────────────────────────────────────

    @Test
    void tokenStartPosition_isCorrect() {
        var result = tokenize("[red]text[/]", DELIMITER, false);
        assertEquals(0, result.tokens().getFirst().start());
    }

    @Test
    void tokenEndPosition_isCorrect() {
        // [red] ends at index 4 (inclusive)
        var result = tokenize("[red]text[/]", DELIMITER, false);
        assertEquals(4, result.tokens().getFirst().end());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "bold", "dim", "italic", "ul", "rv", "inv", "/", "dbl_ul", "strike",
            "red", "green", "yellow", "blue", "magenta", "cyan", "white", "black",
            "*red", "*green", "*blue", "*cyan", "*magenta", "*yellow", "*white", "*black",
            "bg_red", "bg_green", "bg_blue", "bg_yellow", "bg_magenta", "bg_cyan", "bg_white", "bg_black",
            "*bg_red", "*bg_green", "*bg_blue"
    })
    void allBuiltInStyles_resolveWithoutError(String style) {
        String input = "[%s]text[/]".formatted(style);
        assertDoesNotThrow(() -> tokenize(input, DELIMITER, false));
        var result = tokenize(input, DELIMITER, false);
        assertFalse(result.tokens().isEmpty(), "Expected token for style: " + style);
    }
}