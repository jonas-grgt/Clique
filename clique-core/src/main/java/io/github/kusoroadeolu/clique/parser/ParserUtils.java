package io.github.kusoroadeolu.clique.parser;

import io.github.kusoroadeolu.clique.core.exceptions.UnidentifiedStyleException;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.Arrays;
import java.util.List;

public class ParserUtils {
    public static List<AnsiCode> getAnsiCodes(String string) {
        var parser = (AnsiStringParserImpl) AnsiStringParser.DEFAULT;
        return Arrays.stream(string.split(parser.parserConfiguration().getDelimiter()))
                .map(s -> StyleMaps.findStyle(s.trim())
                        .orElseThrow(() -> new UnidentifiedStyleException("Failed to find ansi code mapped to style: %s".formatted(s)))
                )
                .toList();
    }
}
