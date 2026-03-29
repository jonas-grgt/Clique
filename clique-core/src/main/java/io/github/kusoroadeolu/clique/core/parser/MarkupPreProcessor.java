package io.github.kusoroadeolu.clique.core.parser;

import io.github.kusoroadeolu.clique.core.documentation.InternalApi;

@InternalApi(since = "3.1.3")
public class MarkupPreProcessor {
    private static final String ESCAPE_SEQUENCE = "\\[";
    private static final String ESCAPE_PLACEHOLDER = "[\u0000ESC_BRACKET\u0000";

    public String preProcess(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.replace(ESCAPE_SEQUENCE, ESCAPE_PLACEHOLDER);
    }

    public String postProcess(String output) {
        if (output == null || output.isEmpty()) return output;
        return output.replace(ESCAPE_PLACEHOLDER, "[");
    }


}
