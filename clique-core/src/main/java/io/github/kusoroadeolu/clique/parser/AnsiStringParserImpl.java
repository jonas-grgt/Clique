package io.github.kusoroadeolu.clique.parser;


import io.github.kusoroadeolu.clique.config.ParserConfiguration;

import static io.github.kusoroadeolu.clique.core.utils.Constants.EMPTY;

public record AnsiStringParserImpl(ParserConfiguration parserConfiguration) implements AnsiStringParser {
    private static final StyleApplicator STYLE_APPLICATOR = new StyleApplicator();
    private static final TokenExtractor TOKEN_EXTRACTOR = new TokenExtractor();

    public AnsiStringParserImpl() {
        this(ParserConfiguration.DEFAULT);
    }

    public String parse(String tokenedString) {
        if (tokenedString == null || tokenedString.isBlank()) return EMPTY;
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
