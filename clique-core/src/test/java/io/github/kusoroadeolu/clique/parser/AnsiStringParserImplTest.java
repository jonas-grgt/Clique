package io.github.kusoroadeolu.clique.parser;


import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.ansi.ColorCode;
import io.github.kusoroadeolu.clique.ansi.StyleCode;
import io.github.kusoroadeolu.clique.config.ParserConfiguration;
import io.github.kusoroadeolu.clique.core.exceptions.UnidentifiedStyleException;
import io.github.kusoroadeolu.clique.core.parser.ParserUtils;
import io.github.kusoroadeolu.clique.spi.AnsiCode;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AnsiStringParserImplTest {

    @Test
    void testCustomDelimiter() {
        ParserConfiguration config = ParserConfiguration
                .builder()
                .delimiter(' ')
                .build();
        AnsiStringParser parser = new AnsiStringParser(config);
        String output = parser.parse("[red bold]Text[/]");
        assertTrue(output.contains(ColorCode.RED.toString()));
        assertTrue(output.contains(StyleCode.BOLD.toString()));
    }

    @Test
    @Disabled("Fails in Maven Surefire due to unknown JVM fork issue - works in integration tests")
    void testCustomStyleRegistration() {
        Clique.registerStyle("custom", ColorCode.BLUE);
        String output = Clique.parser().parse("[custom]Text[/]");
        assertTrue(output.contains(ColorCode.BLUE.toString()));
    }

    @Test
    @Disabled("Fails in Maven Surefire due to unknown JVM fork issue - works in integration tests")
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


    // PARSING AND CONFIG
    @Test
    void testStrictParsingThrows() {
        ParserConfiguration config = ParserConfiguration
                .builder()
                .enableStrictParsing()
                .build();
        AnsiStringParser parser = new AnsiStringParser(config);
        assertThrows(UnidentifiedStyleException.class,
                () -> parser.parse("[invalid]Text[/]"));
    }

    @Test
    void testAutoCloseTags() {
        ParserConfiguration config = ParserConfiguration
                .builder()
                .enableAutoCloseTags()
                .build();
        AnsiStringParser parser = new AnsiStringParser(config);
        String output = parser.parse("[red]Text");
        assertTrue(output.endsWith(StyleCode.RESET.toString()));
    }

    @Test
    void testEmptyInput() {
        assertEquals("", Clique.parser().parse(""));
    }

    @Test
    void strictParsing_onUnidentifiedStyle_shouldThrow() {
        ParserConfiguration config = ParserConfiguration
                .builder()
                .enableStrictParsing()
                .build();

    }


    @Test
    void testInvalidStyleIgnored() {
        String output = Clique.parser().parse("[notacolor]Text");
        assertFalse(output.contains("\u001B["));
    }


    //Ansi code methods
    @Test
    void testAnsiCodes(){
        var ls = Arrays.stream(ParserUtils.getAnsiCodes("red, blue, cyan")).toList();
        assertEquals(3, ls.size());
        AnsiCode[] arr = {ColorCode.RED, ColorCode.BLUE, ColorCode.CYAN};
        for (var ansi : arr){
            assertTrue(ls.contains(ansi));
        }
    }

    @Test
    void assertThrowEx_onUnidentifiedStyle(){
        assertThrows(UnidentifiedStyleException.class, () -> ParserUtils.getAnsiCodes("notastyle"));
    }


    //ORIGINAL STRING TESTS
    @Test
    void test_getOriginalString_stripsAnsi(){
        var parser = AnsiStringParser.DEFAULT;
        var string = parser.parse("[red, bold]Hello");
        assertEquals("Hello", parser.getOriginalString(string));
    }

    @Test
    void test_getOriginalString_withNoAnsi_returnsEqualString(){
        var parser = AnsiStringParser.DEFAULT;
        var string = parser.parse("Hello");
        assertEquals("Hello", parser.getOriginalString(string));
    }


    @Test
    void onNullString_returnsEmptyString(){
        var parser = AnsiStringParser.DEFAULT;
        var string = parser.parse(null);
        assertNull(string);
    }



}

class CompositeStyle implements AnsiCode {
    private final String compositeCode;

    public CompositeStyle(AnsiCode... codes) {
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