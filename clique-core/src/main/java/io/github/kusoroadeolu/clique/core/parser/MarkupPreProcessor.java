package io.github.kusoroadeolu.clique.core.parser;

import io.github.kusoroadeolu.clique.core.documentation.InternalApi;

import static io.github.kusoroadeolu.clique.core.utils.Constants.*;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.nextCharEquals;

@InternalApi(since = "3.2.0")
public class MarkupPreProcessor {
    private static final char ESCAPE_CHAR = '\\';
    private static final String ESCAPE_PLACEHOLDER = "\uE000";


    private static final String ANSI_SENTINEL = "]\uFFFF";
    //uFFFF is an invalid UNICODE char meaning it can never appear in any valid strings hence, why it's our placeholder here. Also, we use the ] to close the ansi code, so other tags after it are not treated as invalid/nested tags by the parser


    public String preProcess(String input) {
        if (input == null || input.isEmpty()) return input;

        return replaceEscapeTags(input).toString();
    }

    public String postProcess(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.replace(ESCAPE_PLACEHOLDER, String.valueOf(LBRACKET));
    }

    private StringBuilder replaceEscapeTags(String input){
        final int len = input.length();
        final var sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {
            final char c = input.charAt(i);
            int nextIdx =  i + 1;
            if (c == ESCAPE_CHAR && nextCharEquals(input, nextIdx, LBRACKET)) {
                sb.append(ESCAPE_PLACEHOLDER);
                i++;
            } else {
                sb.append(c);
            }
        }
        return sb;
    }
}