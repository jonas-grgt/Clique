package com.github.kusoroadeolu.clique.parser;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.ansi.ColorCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenExtractorTest {

    @Test
    void testTokenExtraction() {
        String input = "[red, bold]Text[/]";
        var tokenExtractor = new TokenExtractor();
        ParseResult result = tokenExtractor.getParseResult(input);
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
        AnsiStringParser parser = Clique.parser();
        parser.parse("[red]Hello[/] World");
        assertEquals("Hello World", parser.getOriginalString());
    }

}