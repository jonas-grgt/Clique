package com.github.kusoroadeolu.clique.parser;

import com.github.kusoroadeolu.clique.config.ParserConfiguration;
import com.github.kusoroadeolu.clique.core.exceptions.DeprecatedMethodException;

import java.util.Objects;

import static com.github.kusoroadeolu.clique.core.utils.Constants.EMPTY;


public record AnsiStringParserImpl(ParserConfiguration parserConfiguration) implements AnsiStringParser {
    private static final StyleApplicator STYLE_APPLICATOR = new StyleApplicator();
    private static final TokenExtractor TOKEN_EXTRACTOR = new TokenExtractor();

    public AnsiStringParserImpl() {
        this(ParserConfiguration.DEFAULT);
    }

    @Deprecated
    public AnsiStringParser configuration(ParserConfiguration configuration) {
        throw new DeprecatedMethodException("Deprecated method. Use constructor configurations instead");
    }

    public String parse(String tokenedString) {
        final ParseResult result = this.extractTokens(tokenedString);
        return STYLE_APPLICATOR.restyleString(result.tokens(), tokenedString, this.parserConfiguration.getEnableAutoCloseTags());
    }

    public String parse(Object object) {
        return this.parse(object.toString());
    }

    public String getOriginalString(String tokenedString) {
        if (tokenedString == null || tokenedString.isBlank()) return EMPTY;

        String clean = tokenedString;
        var result = this.extractTokens(clean);
        for (String tag : result.extractedFormTags()) {
            clean = clean.replace(tag, EMPTY);
        }

        return clean;
    }

    private ParseResult extractTokens(String input) {
        return TOKEN_EXTRACTOR.getParseResult(
                input,
                parserConfiguration.getDelimiter(),
                parserConfiguration.getEnableStrictParsing()
        );
    }

}
