package io.github.kusoroadeolu.clique.parser;


import io.github.kusoroadeolu.clique.config.ParserConfiguration;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.core.parser.*;

import java.util.ArrayList;
import java.util.List;

import static io.github.kusoroadeolu.clique.core.utils.StringUtils.stripAnsi;

@InternalApi(since = "3.2.0")
public record AnsiStringParserImpl(ParserConfiguration parserConfiguration) implements AnsiStringParser {
    private static final StyleResolver RESOLVER = new StyleResolver();
    private static final Tokenizer TOKENIZER = new Tokenizer();
    private static final MarkupPreProcessor PROCESSOR = new MarkupPreProcessor();


    public AnsiStringParserImpl() {
        this(ParserConfiguration.DEFAULT);
    }

    public String parse(String stringToParse) {
        if (stringToParse == null || stringToParse.isBlank()) return stringToParse;
        String processed = PROCESSOR.preProcess(stringToParse);
        final ParseResult result = this.getParseResult(processed);
        String styled = RESOLVER.resolve(result.tokens(), processed, this.parserConfiguration.getEnableAutoCloseTags());
        return PROCESSOR.postProcess(styled);

    }

    public String parse(Object object) {
        return this.parse(object.toString());
    }

    public String getOriginalString(String tokenedString) {
        if (tokenedString == null || tokenedString.isBlank()) return tokenedString;
        String processed = PROCESSOR.preProcess(tokenedString);
        ParseResult result = this.getParseResult(processed);

        if (!result.isPresent()) return stripAnsi(PROCESSOR.postProcess(tokenedString));

        final List<ParseToken> tokens = result.tokens();
        final StringBuilder sb = new StringBuilder(processed.length());
        int cursor = 0;

        for (ParseToken token : tokens) {
            sb.append(processed, cursor, token.start());
            cursor = token.end() + 1;
        }
        sb.append(processed, cursor, processed.length());

        return stripAnsi(PROCESSOR.postProcess(sb.toString()));
    }

    ParseResult getParseResult(String input) {
        return TOKENIZER.tokenize(
                input,
                parserConfiguration.getDelimiter(),
                parserConfiguration.getEnableStrictParsing()
        );
    }
}
