package io.github.kusoroadeolu.clique.core.utils;


import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.core.structures.Cell;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;

import static io.github.kusoroadeolu.clique.core.utils.Constants.*;

@InternalApi(since = "3.2.0")
public final class StringUtils {

    public static void clearStringBuilder(StringBuilder sb) {
        sb.setLength(ZERO);
    }

    public static Cell parseToCellIfPresent(String text, AnsiStringParser parser) {
        if (parser != null) return new Cell(parser.getOriginalString(text), parser.parse(text));
        else return new Cell(text, text);
    }

    public static String parseIfPresent(String text, AnsiStringParser parser) {
        if (parser != null) return parser.parse(text);
        else return text;
    }

    public static String stripAnsi(String styled) {
        int i = 0;
        boolean inAnsi = false;
        var clean = new StringBuilder();

        while (i < styled.length()) {
            char c = styled.charAt(i);
            if (c == ESC && nextCharEquals(styled, i + 1, LBRACKET)) {
                inAnsi = true;
                i++;
            } else if (inAnsi && (c = styled.charAt(i)) == ANSI_END) {
                inAnsi = false;
                i++;
            }else if (!inAnsi){
                clean.append(c);
                i++;
            }
        }

        return clean.toString();
    }

    public static boolean nextCharEquals(String s, int pos, char isEqualTo){
        return pos < s.length() && s.charAt(pos) == isEqualTo;
    }

    public static boolean nextCharEquals(StringBuilder s, int pos, char isEqualTo){
        return pos < s.length() && s.charAt(pos) == isEqualTo;
    }
}
