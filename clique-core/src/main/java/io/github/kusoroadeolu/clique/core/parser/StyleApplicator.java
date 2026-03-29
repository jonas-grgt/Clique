package io.github.kusoroadeolu.clique.core.parser;


import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.core.exceptions.ParseProblemException;
import io.github.kusoroadeolu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.style.StyleBuilder;

import java.util.List;

@InternalApi(since = "3.1.3")
public final class StyleApplicator {

    //Restyle the extracted string with the given colors
    public String restyleString(List<ParseToken> tokens, String extractedString, boolean enableAutoCloseTags) {
        final StringBuilder sb = new StringBuilder();
        final StyleBuilder stb = Clique.styleBuilder();
        String val;
        final int size = tokens.size();

        if (tokens.isEmpty()) {
            return extractedString;
        }

        //Check if the styling starts from the beginning of the string
        if (tokens.getFirst().start() != 0) {
            sb.append(extractedString, 0, tokens.getFirst().start());
        }

        try {
            for (int i = 0; i < size; i++) {
                final ParseToken curr = tokens.get(i);
                final ParseToken next = i != (size - 1) ? tokens.get(i + 1) :
                        new ParseToken(extractedString.length(), 0, null); //if we're at the end of the loop, we apply the current style to the rem of the string

                final AnsiCode[] codes = curr.validStyles().toArray(AnsiCode[]::new);
                final int start = curr.end() + 1;
                final int end = next.start();
                val = extractedString.substring(start, end);

                if (enableAutoCloseTags) sb.append(stb.formatAndReset(val, codes));
                else sb.append(stb.format(val, codes));
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw new ParseProblemException("Failed to parse string. This often happens when style tags have nested brackets like '[[red]]'.", e);
        }

        return sb.toString();
    }
}
