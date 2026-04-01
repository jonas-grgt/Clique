package io.github.kusoroadeolu.clique.core.parser;

import io.github.kusoroadeolu.clique.core.documentation.InternalApi;

import static io.github.kusoroadeolu.clique.core.utils.Constants.*;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.nextCharEquals;

@InternalApi(since = "3.2.0")
public class MarkupPreProcessor {
    private static final char ESCAPE_CHAR = '\\';
    private static final char OPENING_BRACKET = '[';
    private static final char PLACEHOLDER = '\uE000';

    /*
     * ANSI escape codes embedded in pre-parsed strings interfere with markup tag detection
     * when a styled string is concatenated with a new markup string and fed back into the parser.
     *
     * To handle this, we close any open ANSI sequences with a closing bracket followed by
     * U+FFFF — a Unicode non-character guaranteed never to appear in valid text. This acts
     * as a sentinel that lets us distinguish ANSI brackets from markup tag brackets during
     * parsing. Once markup resolution is complete, we strip the sentinel back out.
     *
     * U+FFFF was chosen specifically because Unicode explicitly defines it as a non-character,
     * meaning no real-world string will ever contain it, making accidental collisions impossible.
     */

    private static final String ANSI_SENTINEL = "]\uFFFF";


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

        final int len = input.length();
        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {
            char c = input.charAt(i);

            if (c == ']' && i + 1 < len && input.charAt(i + 1) == '\uFFFF') {
                i++;
                continue;
            }

            if (c == PLACEHOLDER) {
                sb.append(OPENING_BRACKET);
                continue;
            }

            sb.append(c);
        }

        return sb.toString();
    }


    private StringBuilder replaceEscapeTags(String input){
        final int len = input.length();
        final var sb = new StringBuilder();

        int escIdx = input.indexOf(ESCAPE_CHAR);
        if (escIdx >= 0 && escIdx < len - 1) {
            for (int i = 0; i < len; i++) {
                char c = input.charAt(i);
                int nextIdx = i + 1;
                if (c == ESCAPE_CHAR && nextIdx < len && input.charAt(nextIdx) == OPENING_BRACKET) {
                    sb.append(PLACEHOLDER);
                    i++;
                } else sb.append(c);
            }
        } else {
            sb.append(input);
        }

        return sb;
    }
}