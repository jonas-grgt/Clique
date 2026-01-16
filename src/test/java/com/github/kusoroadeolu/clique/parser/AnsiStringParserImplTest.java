package com.github.kusoroadeolu.clique.parser;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.ansi.AnsiCode;
import com.github.kusoroadeolu.clique.ansi.ColorCode;
import com.github.kusoroadeolu.clique.ansi.StyleCode;
import com.github.kusoroadeolu.clique.config.ParserConfiguration;
import com.github.kusoroadeolu.clique.core.exceptions.ParseProblemException;
import com.github.kusoroadeolu.clique.core.exceptions.UnidentifiedStyleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnsiStringParserImplTest {

    @Test
    void testCustomDelimiter() {
        ParserConfiguration config = ParserConfiguration
                .immutableBuilder()
                .delimiter(' ')
                .build();
        AnsiStringParser parser = new AnsiStringParserImpl(config);
        String output = parser.parse("[red bold]Text[/]");
        assertTrue(output.contains(ColorCode.RED.toString()));
        assertTrue(output.contains(StyleCode.BOLD.toString()));
    }

    @Test
    void testStrictParsingThrows() {
        ParserConfiguration config = ParserConfiguration
                .immutableBuilder()
                .enableStrictParsing()
                .build();
        AnsiStringParser parser = new AnsiStringParserImpl(config);
        assertThrows(UnidentifiedStyleException.class,
                () -> parser.parse("[invalid]Text[/]"));
    }

    @Test
    void testAutoCloseTags() {
        ParserConfiguration config = ParserConfiguration
                .immutableBuilder()
                .enableAutoCloseTags()
                .build();
        AnsiStringParser parser = new AnsiStringParserImpl(config);
        String output = parser.parse("[red]Text");
        assertTrue(output.endsWith(StyleCode.RESET.toString()));
    }

    @Test
    void testEmptyInput() {
        assertEquals("", Clique.parser().parse(""));
    }

    @Test
    void testNestedBrackets() {
        ParserConfiguration config = ParserConfiguration
                .immutableBuilder()
                .enableStrictParsing()
                .build();
        assertThrows(ParseProblemException.class,
                () -> new AnsiStringParserImpl(config).parse("[[[red]]]"));
    }

    @Test
    void testInvalidStyleIgnored() {
        String output = Clique.parser().parse("[notacolor]Text");
        assertFalse(output.contains("\u001B["));
    }

    @Test
    void testCustomStyleRegistration() {
        Clique.registerStyle("custom", ColorCode.BLUE);
        String output = Clique.parser().parse("[custom]Text[/]");
        assertTrue(output.contains(ColorCode.BLUE.toString()));
    }

    @Test
    void testCompositeStyleRegistration() {
        AnsiCode composite = new CompositeStyle(
                ColorCode.RED,
                StyleCode.BOLD
        );
        Clique.registerStyle("error", composite);
        String output = Clique.parser().parse("[error]Text[/]");
        assertTrue(output.contains(ColorCode.RED.toString()));
        assertTrue(output.contains(StyleCode.BOLD.toString()));
    }

}
class CompositeStyle implements AnsiCode {
    private final AnsiCode[] codes;
    private final String compositeCode;

    public CompositeStyle(AnsiCode... codes) {
        this.codes = codes;
        // Pre-compute the composite code
        StringBuilder sb = new StringBuilder();
        for (AnsiCode code : codes) {
            sb.append(code.toString());
        }
        this.compositeCode = sb.toString();
    }


    @Override
    public String toString() {
        return compositeCode;
    }
}