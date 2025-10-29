package parser.token.dtos;

import core.ansi.interfaces.AnsiCode;

import java.util.List;

public record ParserToken(
        int start,
        int end,
        List<AnsiCode> validStyles
) {
}
