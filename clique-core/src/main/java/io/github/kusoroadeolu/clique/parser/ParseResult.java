package io.github.kusoroadeolu.clique.parser;

import java.util.List;

public record ParseResult(
        List<ParserToken> tokens,
        List<String> extractedFormTags
) {
    boolean isEmpty(){
        return tokens.isEmpty() && extractedFormTags.isEmpty();
    }
}
