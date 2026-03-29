package io.github.kusoroadeolu.clique.core.parser;


import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.List;

@InternalApi(since = "3.1.3")
public record ParseToken(
        int start,
        int end,
        List<AnsiCode> styles
) {
}
