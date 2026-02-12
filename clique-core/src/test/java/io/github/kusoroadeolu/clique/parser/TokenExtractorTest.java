package io.github.kusoroadeolu.clique.parser;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.ansi.ColorCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TokenExtractorTest {

    @Test
    void testTokenExtraction() {
        String input = "[red, bold]Text[/]";
        var tokenExtractor = new TokenExtractor();
        ParseResult result = tokenExtractor.getParseResult(input, ",", false);
        assertEquals(2, result.tokens().size());
        assertEquals(2, result.tokens().getFirst().validStyles().size());
    }

    @Test
    void testColorCodeOutput() {
        assertEquals("\u001B[31m", ColorCode.RED.toString());
    }

    // Original string extraction
    @Test
    void testGetOriginalString() {
        var parser = Clique.parser();
        assertEquals("Hello World", parser.getOriginalString("[red]Hello[/] World"));
    }

}