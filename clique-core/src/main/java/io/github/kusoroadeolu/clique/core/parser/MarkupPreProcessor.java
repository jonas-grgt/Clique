package io.github.kusoroadeolu.clique.core.parser;

import io.github.kusoroadeolu.clique.core.documentation.InternalApi;

import static io.github.kusoroadeolu.clique.core.utils.Constants.*;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.nextCharEquals;

@InternalApi(since = "3.2.0")
public class MarkupPreProcessor {
    private static final char ESCAPE_CHAR = '\\';
    private static final char OPENING_BRACKET = '[';
    private static final String PLACEHOLDER = "\uE000";
    private static final String ANSI_SENTINEL = "\uFFFF";

    public String preProcess(String input) {
        if (input == null || input.isEmpty()) return input;

        StringBuilder sb = replaceEscapeTags(input);

        int i = 0;
        boolean inAnsi = false;
        var processed = new StringBuilder();

        while (i < sb.length()) {
            char c = sb.charAt(i);

            if (c == ESC && nextCharEquals(sb, i + 1, OPENING_BRACKET)) {
                inAnsi = true;
                processed.append(c);
                processed.append(sb.charAt(i + 1));
                i += 2;
                continue;
            }

            if (inAnsi && c == ANSI_END) {
                inAnsi = false;
                processed.append(c).append(ANSI_SENTINEL);
                i++;
                continue;
            }

            processed.append(c);
            i++;
        }

        return processed.toString();
    }

    public String postProcess(String input) {
        if (input == null || input.isEmpty()) return input;
        return input
                .replace(PLACEHOLDER, String.valueOf(OPENING_BRACKET))
                .replace(ANSI_SENTINEL, EMPTY);
    }

    private StringBuilder replaceEscapeTags(String input){
        final int len = input.length();
        final var sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {
            final char c = input.charAt(i);
            if (c == ESCAPE_CHAR && (i + 1) < len && input.charAt(i + 1) == OPENING_BRACKET) {
                sb.append(PLACEHOLDER);
                i++;
            } else {
                sb.append(c);
            }
        }
        return sb;
    }
}