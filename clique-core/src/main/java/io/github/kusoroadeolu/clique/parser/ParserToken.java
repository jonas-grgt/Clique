package io.github.kusoroadeolu.clique.parser;


import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.List;

public record ParserToken(
        int start,
        int end,
        List<AnsiCode> validStyles
) {
}
