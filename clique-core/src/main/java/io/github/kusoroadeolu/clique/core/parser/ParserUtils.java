package io.github.kusoroadeolu.clique.core.parser;

import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.core.exceptions.UnidentifiedStyleException;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.Arrays;
import java.util.List;

@InternalApi(since = "3.2.0")
public class ParserUtils {
    public static AnsiCode[] getAnsiCodes(String string) {
        return getAnsiCodes(string, (AnsiStringParser) AnsiStringParser.DEFAULT);
    }

    public static AnsiCode[] getAnsiCodes(String string, AnsiStringParser parser) {
        if (string.isBlank()) return new AnsiCode[0];
        return Arrays.stream(string.split(parser.parserConfiguration().getDelimiter()))
                .map(s -> StyleMaps.findStyle(s.trim())
                        .orElseThrow(() -> new UnidentifiedStyleException("Failed to find ansi code mapped to style: %s".formatted(s)))
                )
                .toArray(AnsiCode[]::new);
    }
}
