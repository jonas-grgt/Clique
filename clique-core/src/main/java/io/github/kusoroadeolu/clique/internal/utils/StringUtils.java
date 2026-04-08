package io.github.kusoroadeolu.clique.internal.utils;


import io.github.kusoroadeolu.clique.internal.Cell;
import io.github.kusoroadeolu.clique.internal.documentation.InternalApi;
import io.github.kusoroadeolu.clique.parser.MarkupParser;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.Objects;

import static io.github.kusoroadeolu.clique.internal.Constants.*;
import static io.github.kusoroadeolu.clique.internal.utils.AnsiDetector.ansiEnabled;
import static io.github.kusoroadeolu.clique.style.StyleCode.RESET;

@InternalApi(since = "3.2.0")
public final class StringUtils {

    private StringUtils(){}

    public static void clearStringBuilder(StringBuilder sb) {
        sb.setLength(ZERO);
    }

    public static Cell parseToCellIfPresent(String text, MarkupParser parser) {
        if (parser != null) return new Cell(parser.getOriginalString(text), parser.parse(text));
        else return new Cell(text, text);
    }

    public static String parseIfPresent(String text, MarkupParser parser) {
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
            } else if (inAnsi && (c = styled.charAt(i)) == ANSI_END) {
                inAnsi = false;
            }else if (!inAnsi){
                clean.append(c);
            }

            i++;
        }

        return clean.toString();
    }

    public static boolean nextCharEquals(String s, int pos, char isEqualTo){
        return pos < s.length() && s.charAt(pos) == isEqualTo;
    }

    public static String format(StringBuilder sb, String text, AnsiCode... ansiCodes) {
        style(text, sb, ansiCodes);
        String result = sb.toString();
        clearStringBuilder(sb);
        return result;
    }


    public static String formatAndReset(StringBuilder sb, String text, AnsiCode... ansiCodes) {
        style(text, sb, ansiCodes).append(RESET);
        String result = sb.toString();
        clearStringBuilder(sb);
        return result;
    }

    //A helper method to style text with the given codes
    public static StringBuilder style(String text, StringBuilder sb, AnsiCode... ansiCodes) {
        Objects.requireNonNull(text, "Text cannot be null");
        Objects.requireNonNull(ansiCodes, "Ansi codes cannot be null");

        //Check if ansi is enabled
        if (ansiEnabled()) {
            for (AnsiCode code : ansiCodes) {
                if (code != null) sb.append(code.ansiSequence());

            }
        }

        return sb.append(text);
    }
}
