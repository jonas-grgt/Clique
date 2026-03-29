package io.github.kusoroadeolu.clique.parser;


import io.github.kusoroadeolu.clique.config.ParserConfiguration;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.core.parser.ParseResult;
import io.github.kusoroadeolu.clique.core.parser.StyleApplicator;
import io.github.kusoroadeolu.clique.core.parser.TokenExtractor;

import static io.github.kusoroadeolu.clique.core.utils.Constants.EMPTY;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.stripAnsi;

@InternalApi(since = "3.1.3")
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

        if (result.isPresent()){
            var piped = result.extractedFormTags().stream()
                    .reduce(tokenedString, (s, tag) -> s.replace(tag, EMPTY));
            return stripAnsi(piped);

        }

        return stripAnsi(tokenedString);
    }

    ParseResult getParseResult(String input) {
        return TOKEN_EXTRACTOR.getParseResult(
                input,
                parserConfiguration.getDelimiter(),
                parserConfiguration.getEnableStrictParsing()
        );
    }
}
