package io.github.kusoroadeolu.clique.core.parser;

import io.github.kusoroadeolu.clique.core.documentation.InternalApi;

import java.util.List;

@InternalApi(since = "3.1.3")
public record ParseResult(
        List<ParseToken> tokens,
        List<String> extractedFormTags
) {
    public boolean isPresent(){
        return !tokens.isEmpty();
    }
}
