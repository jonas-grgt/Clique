package io.github.kusoroadeolu.clique.core.parser;

import io.github.kusoroadeolu.clique.core.documentation.InternalApi;

@InternalApi(since = "3.1.3")
public class MarkupPreProcessor {
    private static final char ESCAPE_CHAR = '\\';
    private static final char BRACKET = '[';
    private static final char PLACEHOLDER = '\uE000';

    public String preProcess(String input) {
        if (input == null || input.isEmpty()) return input;

        int idx = input.indexOf(ESCAPE_CHAR);
        final int len = input.length();
        if (idx < 0 || idx == len - 1) return input;

        final var sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            final char c = input.charAt(i);
            int nextIdx = i + 1;
            if (c == ESCAPE_CHAR && nextIdx < len && input.charAt(nextIdx) == BRACKET) {
                sb.append(PLACEHOLDER);
                i++;
            } else sb.append(c);

        }

        return sb.toString();
    }

    public String postProcess(String output) {
        if (output == null || output.isEmpty()) return output;

        if (output.indexOf(PLACEHOLDER) < 0) return output;

        return output.replace(PLACEHOLDER, BRACKET);
    }
}