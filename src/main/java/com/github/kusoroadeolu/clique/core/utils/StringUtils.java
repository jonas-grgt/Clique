package com.github.kusoroadeolu.clique.core.utils;

import com.github.kusoroadeolu.clique.parser.AnsiStringParser;
import com.github.kusoroadeolu.clique.tables.structures.Cell;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.github.kusoroadeolu.clique.core.utils.Constants.*;

public final class StringUtils {

    public static void clearStringBuilder(StringBuilder sb){
        sb.setLength(ZERO);
    }

    public static Cell parseCell(String text, AnsiStringParser parser){
        if(parser != null){
            final String styled = parser.parse(text);
            return new Cell(parser.getOriginalString(), styled);
        }else{
            return new Cell(text, text);
        }
    }


    //Automatically get a suitable width when the width is not given
    public static int getDynamicCharsPerLine(String content){
        if (content.isBlank()) return content.length();
        String[] arr = content.split(NEWLINE_PATTERN.pattern());
        int longest = arr[0].length();
        for (String s : arr){
            if (s.length() > longest) longest = s.length();
        }

        return longest;
    }

    public static String[] filterWhitespace(String[] arr){
        return Arrays.stream(arr)
                .filter(s -> {
                    // Remove all ANSI codes and check if anything remains
                    String withoutAnsi = s.replaceAll(WHITESPACE_PATTERN.pattern(), EMPTY);
                    return !withoutAnsi.isBlank();
                })
                .toArray(String[]::new);
    }

    public static void wrapLongString(StringBuilder currentOriginal, StringBuilder currentStyled, List<Cell> wordWrap, int maxCharsPerLine){
        if(currentOriginal.length() > maxCharsPerLine){
            String activeAnsi = ""; // Track ANSI codes to carry forward

            while (currentOriginal.length() > maxCharsPerLine){
                // Take first maxCharsPerLine from original
                String originalChunk = currentOriginal.substring(ZERO, maxCharsPerLine);

                // Find corresponding styled text by counting visible characters
                int styledEnd = getStyledEndIndex(currentStyled.toString(), maxCharsPerLine);
                String styledChunk = activeAnsi + currentStyled.substring(ZERO, styledEnd);

                wordWrap.add(new Cell(originalChunk, styledChunk));

                // Update active ANSI codes by looking at the full chunk
                activeAnsi = getActiveAnsiCodes(styledChunk);

                // Remove the processed chunk
                currentOriginal.delete(ZERO, maxCharsPerLine);
                currentStyled.delete(ZERO, styledEnd);
            }

            if (!currentOriginal.isEmpty()) {
                String lastStyled = activeAnsi + currentStyled;
                wordWrap.add(new Cell(currentOriginal.toString(), lastStyled));
                currentOriginal.setLength(0);
                currentStyled.setLength(0);
            }
        }
    }

    // Helper to find where to cut styled text based on visible character count
    private static int getStyledEndIndex(String styled, int visibleChars) {
        int visible = 0;
        int i = 0;
        boolean inAnsi = false;

        while (i < styled.length() && visible < visibleChars) {
            if (styled.charAt(i) == ANSI_BEGIN) {
                inAnsi = true;
            } else if (inAnsi && styled.charAt(i) == ANSI_END) {
                inAnsi = false;
                i++;
                continue;
            }

            if (!inAnsi) {
                visible++;
            }
            i++;
        }

        return i;
    }

    // Extract all ANSI codes that are currently active
    private static String getActiveAnsiCodes(String styled) {
        StringBuilder ansiCodes = new StringBuilder();
        boolean inAnsi = false;
        int ansiStart = 0;

        for (int i = 0; i < styled.length(); i++) {
            if (styled.charAt(i) == ANSI_BEGIN) {
                inAnsi = true;
                ansiStart = i;
            } else if (inAnsi && styled.charAt(i) == ANSI_END) {
                String code = styled.substring(ansiStart, i + 1);
                if (!code.contains("0m")) {
                    ansiCodes.append(code);
                } else {
                    // If we hit a reset, clear all previous codes
                    clearStringBuilder(ansiCodes);
                }
                inAnsi = false;
            }
        }

        return ansiCodes.toString();
    }



}
