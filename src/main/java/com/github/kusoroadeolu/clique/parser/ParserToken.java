package com.github.kusoroadeolu.clique.parser;

import com.github.kusoroadeolu.clique.ansi.AnsiCode;

import java.util.List;

public record ParserToken(
        int start,
        int end,
        List<AnsiCode> validStyles
) {
}
