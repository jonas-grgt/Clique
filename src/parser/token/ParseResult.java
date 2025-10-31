package parser.token;

import java.util.List;

public record ParseResult(
        List<ParserToken> tokens,
        List<String> extractedFormTags
) {
}
