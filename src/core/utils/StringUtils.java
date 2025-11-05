package core.utils;

import parser.token.stringparser.interfaces.AnsiStringParser;
import core.misc.Cell;

import java.util.Arrays;
import java.util.Collection;

public final class StringUtils {

    public final static int ZERO = 0;

    public static void clearStringBuilder(StringBuilder sb){
        sb.setLength(ZERO);
    }

    public static Cell parseCell(String cell, AnsiStringParser parser){
        if(parser != null){
            final String styled = parser.parse(cell);
            return new Cell(parser.getOriginalString(), styled);
        }else{
            return new Cell(cell, cell);
        }
    }

    public static int getLongest(String[] originalWords){
        int longest = 0;
        for (String s : originalWords){
            if (s.length() > longest) {
                longest = s.length();
            }
        }
        return longest;
    }

    public static String[] filterWhitespace(String[] arr){
        return Arrays.stream(arr)
                .filter(s -> {
                    // Remove all ANSI codes and check if anything remains
                    String withoutAnsi = s.replaceAll("\u001b\\[[;\\d]*m", "");
                    return !withoutAnsi.isBlank();
                })
                .toArray(String[]::new);
    }



}
