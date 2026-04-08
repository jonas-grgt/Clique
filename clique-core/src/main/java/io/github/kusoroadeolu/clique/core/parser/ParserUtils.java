package io.github.kusoroadeolu.clique.core.parser;

import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.parser.MarkupParser;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.Arrays;

import static io.github.kusoroadeolu.clique.core.parser.PredefinedStyleContext.getOrThrow;

@InternalApi(since = "3.2.0")
public class ParserUtils {
    private static final AnsiCode[] NONE = new AnsiCode[0];

    private ParserUtils(){}

    public static AnsiCode[] getAnsiCodes(String string) {
        return getAnsiCodes(string, MarkupParser.DEFAULT);
    }

    public static AnsiCode[] getAnsiCodes(String string, MarkupParser parser) {
        if (string.isBlank()) return NONE;
        return Arrays.stream(string.split(parser.parserConfiguration().getDelimiter()))
                .map(s -> getOrThrow(s.trim(), parser.parserConfiguration().getStyleContext()))
                .toArray(AnsiCode[]::new);
    }
}
