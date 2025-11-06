package com.github.kusoroadeolu.parser.token;

import com.github.kusoroadeolu.core.ansi.interfaces.AnsiCode;

import java.util.List;

public record ParserToken(
        int start,
        int end,
        List<AnsiCode> validStyles
) {
}
