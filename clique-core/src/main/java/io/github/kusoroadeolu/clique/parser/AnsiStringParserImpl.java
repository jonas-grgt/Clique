package io.github.kusoroadeolu.clique.parser;


import io.github.kusoroadeolu.clique.config.ParserConfiguration;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.core.parser.ParseResult;
import io.github.kusoroadeolu.clique.core.parser.ParseToken;
import io.github.kusoroadeolu.clique.core.parser.StyleResolver;
import io.github.kusoroadeolu.clique.core.parser.Tokenizer;

import java.util.List;

import static io.github.kusoroadeolu.clique.core.parser.MarkupPostProcessor.postProcess;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.stripAnsi;

@InternalApi(since = "3.2.0")
public record AnsiStringParserImpl(ParserConfiguration parserConfiguration) implements AnsiStringParser {


    public AnsiStringParserImpl() {
        this(ParserConfiguration.DEFAULT);
    }

    public String parse(String stringToParse) {
        if (stringToParse == null || stringToParse.isBlank()) return stringToParse;
        final ParseResult result = this.getParseResult(stringToParse);
        String styled = StyleResolver.resolve(result.tokens(), stringToParse, this.parserConfiguration.getEnableAutoCloseTags());
        return postProcess(styled);

    }

    public String parse(Object object) {
        return this.parse(object.toString());
    }

    public String getOriginalString(String tokenedString) {
        if (tokenedString == null || tokenedString.isBlank()) return tokenedString;
        ParseResult result = this.getParseResult(tokenedString);

        if (!result.isPresent()) {
            return stripAnsi(postProcess(tokenedString));
        }

        final List<ParseToken> tokens = result.tokens();
        final StringBuilder sb = new StringBuilder(tokenedString.length());
        int cursor = 0;

        for (ParseToken token : tokens) {
            sb.append(tokenedString, cursor, token.start());
            cursor = token.end() + 1;
        }

        sb.append(tokenedString, cursor, tokenedString.length());

        return stripAnsi(postProcess(sb.toString()));
    }

    ParseResult getParseResult(String input) {
        return Tokenizer.tokenize(
                input,
                parserConfiguration.getDelimiter(),
                parserConfiguration.getEnableStrictParsing()
        );
    }
}
