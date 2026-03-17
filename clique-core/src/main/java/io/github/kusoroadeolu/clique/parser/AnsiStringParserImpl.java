package io.github.kusoroadeolu.clique.parser;


import io.github.kusoroadeolu.clique.config.ParserConfiguration;
import io.github.kusoroadeolu.clique.core.exceptions.UnidentifiedStyleException;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.Constants.EMPTY;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.skipAnsi;

public record AnsiStringParserImpl(ParserConfiguration parserConfiguration) implements AnsiStringParser {
    private static final StyleApplicator STYLE_APPLICATOR = new StyleApplicator();
    private static final TokenExtractor TOKEN_EXTRACTOR = new TokenExtractor();


    public AnsiStringParserImpl() {
        this(ParserConfiguration.DEFAULT);
    }

    public String parse(String stringToParse) {
        if (stringToParse == null || stringToParse.isBlank()) return EMPTY;
        final ParseResult result = this.getParseResult(stringToParse);
        return STYLE_APPLICATOR.restyleString(result.tokens(), stringToParse, this.parserConfiguration.getEnableAutoCloseTags());
    }

    public String parse(Object object) {
        return this.parse(object.toString());
    }

    public String getOriginalString(String tokenedString) {
        if (tokenedString == null || tokenedString.isBlank()) return EMPTY;
        var result = this.getParseResult(tokenedString);
        var piped = result.extractedFormTags().stream()
                .reduce(tokenedString, (s, tag) -> s.replace(tag, EMPTY));
        return skipAnsi(piped);
    }

    private ParseResult getParseResult(String input) {
        return TOKEN_EXTRACTOR.getParseResult(
                input,
                parserConfiguration.getDelimiter(),
                parserConfiguration.getEnableStrictParsing()
        );
    }

    @Override
    public List<AnsiCode> ansiCodes(String string) {
        return Arrays.stream(string.split(parserConfiguration.getDelimiter()))
                .map(s -> StyleMaps.findStyle(s.trim())
                        .orElseThrow(() -> new UnidentifiedStyleException("Failed to find ansi code mapped to style: %s".formatted(s)))
                )
                .toList();
    }
}
