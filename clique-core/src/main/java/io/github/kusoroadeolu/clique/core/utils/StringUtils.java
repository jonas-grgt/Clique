package io.github.kusoroadeolu.clique.core.utils;


import io.github.kusoroadeolu.clique.core.structures.Cell;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;

import java.util.Arrays;
import java.util.List;

import static io.github.kusoroadeolu.clique.core.utils.Constants.*;

public final class StringUtils {

    public static void clearStringBuilder(StringBuilder sb) {
        sb.setLength(ZERO);
    }

    public static Cell parseToCell(String text, AnsiStringParser parser) {
        if (parser != null) return new Cell(parser.getOriginalString(text), parser.parse(text));
        else return new Cell(text, text);
    }

    public static String stripAnsi(String styled) {
        int i = 0;
        boolean inAnsi = false;
        var clean = new StringBuilder();

        while (i < styled.length()) {
            char c;
            if ((c = styled.charAt(i)) == ANSI_BEGIN) {
                inAnsi = true;
            } else if (inAnsi && (c = styled.charAt(i)) == ANSI_END) {
                inAnsi = false;
                i++;
                continue;
            }

            if (!inAnsi){
                clean.append(c);
            }
            i++;
        }

        return clean.toString();
    }
}
