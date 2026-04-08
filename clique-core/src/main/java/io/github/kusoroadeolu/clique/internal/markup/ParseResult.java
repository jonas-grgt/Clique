package io.github.kusoroadeolu.clique.internal.markup;

import io.github.kusoroadeolu.clique.internal.documentation.InternalApi;

import java.util.List;

@InternalApi(since = "3.2.0")
public record ParseResult(
        List<ParseToken> tokens
) {
    public boolean isPresent(){
        return !tokens.isEmpty();
    }
}
