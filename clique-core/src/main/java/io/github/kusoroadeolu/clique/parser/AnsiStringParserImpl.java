package io.github.kusoroadeolu.clique.parser;


import io.github.kusoroadeolu.clique.config.ParserConfiguration;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.core.parser.MarkupPreProcessor;
import io.github.kusoroadeolu.clique.core.parser.ParseResult;
import io.github.kusoroadeolu.clique.core.parser.StyleApplicator;
import io.github.kusoroadeolu.clique.core.parser.TokenExtractor;

import static io.github.kusoroadeolu.clique.core.utils.Constants.EMPTY;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.stripAnsi;

@InternalApi(since = "3.1.3")
public record AnsiStringParserImpl(ParserConfiguration parserConfiguration) implements AnsiStringParser {
    private static final StyleApplicator STYLE_APPLICATOR = new StyleApplicator();
    private static final TokenExtractor TOKEN_EXTRACTOR = new TokenExtractor();
    private static final MarkupPreProcessor PROCESSOR = new MarkupPreProcessor();


    public AnsiStringParserImpl() {
        this(ParserConfiguration.DEFAULT);
    }

    public String parse(String stringToParse) {
        if (stringToParse == null || stringToParse.isBlank()) return stringToParse;
        String processed = PROCESSOR.preProcess(stringToParse);
        final ParseResult result = this.getParseResult(processed);
        String styled = STYLE_APPLICATOR.restyleString(result.tokens(), processed, this.parserConfiguration.getEnableAutoCloseTags());
        return PROCESSOR.postProcess(styled);

    }

    public String parse(Object object) {
        return this.parse(object.toString());
    }

    public String getOriginalString(String tokenedString) {
        if (tokenedString == null || tokenedString.isBlank()) return tokenedString;
        String processed = PROCESSOR.preProcess(tokenedString);
        ParseResult result = this.getParseResult(processed);

        String piped;
        if (result.isPresent()){
            piped = result.extractedFormTags().stream().reduce(processed, (s, tag) -> s.replace(tag, EMPTY));
        }else {
            piped = tokenedString;
        }

        return stripAnsi(PROCESSOR.postProcess(piped));
    }

    ParseResult getParseResult(String input) {
        return TOKEN_EXTRACTOR.getParseResult(
                input,
                parserConfiguration.getDelimiter(),
                parserConfiguration.getEnableStrictParsing()
        );
    }
}
