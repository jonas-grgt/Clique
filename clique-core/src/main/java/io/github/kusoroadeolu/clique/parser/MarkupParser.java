package io.github.kusoroadeolu.clique.parser;


import io.github.kusoroadeolu.clique.configuration.ParserConfiguration;
import io.github.kusoroadeolu.clique.internal.documentation.Stable;
import io.github.kusoroadeolu.clique.internal.markup.ParseResult;
import io.github.kusoroadeolu.clique.internal.markup.ParseToken;
import io.github.kusoroadeolu.clique.internal.markup.StyleResolver;
import io.github.kusoroadeolu.clique.internal.markup.Tokenizer;

import java.util.List;

import static io.github.kusoroadeolu.clique.internal.markup.MarkupPostProcessor.postProcess;
import static io.github.kusoroadeolu.clique.internal.utils.StringUtils.stripAnsi;

/**
 * @since 1.0.0
 * */
@Stable(since = "3.2.0")
public record MarkupParser(ParserConfiguration parserConfiguration) {
    public static final MarkupParser DEFAULT = new MarkupParser();

    public MarkupParser() {
        this(ParserConfiguration.DEFAULT);
    }

    public String parse(String stringToParse) {
        if (stringToParse == null || stringToParse.isBlank()) return stringToParse;
        final ParseResult result = this.getParseResult(stringToParse);
        String styled = StyleResolver.resolve(result.tokens(), stringToParse, this.parserConfiguration.getEnableAutoReset());
        return postProcess(styled);

    }

    public void print(String string) {
        System.out.println(parse(string));
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
                parserConfiguration.getEnableStrictParsing(),
                parserConfiguration.getStyleContext()
        );
    }
}
