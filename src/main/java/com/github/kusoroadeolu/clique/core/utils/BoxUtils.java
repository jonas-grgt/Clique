package com.github.kusoroadeolu.clique.core.utils;

import com.github.kusoroadeolu.clique.ansi.StyleCode;
import com.github.kusoroadeolu.clique.boxes.BoxWrapper;
import com.github.kusoroadeolu.clique.config.TextAlign;
import com.github.kusoroadeolu.clique.core.exceptions.ExceptionSupplier;
import com.github.kusoroadeolu.clique.core.exceptions.InvalidDimensionException;
import com.github.kusoroadeolu.clique.tables.structures.Cell;

import java.util.ArrayList;
import java.util.List;

import static com.github.kusoroadeolu.clique.core.utils.Constants.BLANK;
import static com.github.kusoroadeolu.clique.core.utils.StringUtils.clearStringBuilder;

public class BoxUtils {

    private final static String RESET = StyleCode.RESET.toString();
    private final static char ANSI_END = 'm';
    private final static char ANSI_BEGIN = '\u001b';

    public static void validateDimensions(int width, int len){
        if(width <= 0 || len <= 0){
            throw new InvalidDimensionException("Width or length cannot be 0");
        }
    }

    public static void alignText(StringBuilder sb, int idx, TextAlign textAlign, String spaces, List<Cell> wordWrap, String vLine){
        final String s = wordWrap.get(idx).text();
        final String ss = wordWrap.get(idx).styledText();
        final int totalPadding = spaces.length() - s.length();

        switch (textAlign){
            case TOP_LEFT, CENTER_LEFT, BOTTOM_LEFT -> {
                final String padding = BLANK.repeat(Math.max(0, totalPadding));
                sb.append(vLine)
                        .append(ss)
                        .append(RESET)  //Appending RESET here to prevent colors from bleeding into the lines
                        .append(padding)
                        .append(vLine)
                        .append("\n");
            }

            case TOP_RIGHT, CENTER_RIGHT, BOTTOM_RIGHT -> {

                final String padding = BLANK.repeat(Math.max(0, totalPadding));
                sb.append(vLine)
                        .append(padding)
                        .append(ss)
                        .append(RESET)
                        .append(vLine)
                        .append("\n");
            }

            case TOP_CENTER, CENTER, BOTTOM_CENTER -> {

                if(s.length() >= spaces.length()){
                    sb.append(RESET).append(vLine).append(ss).append(RESET).append(vLine).append(Constants.NEWLINE);
                    return;
                }

                final int leftPadding = totalPadding / 2;
                final int rightPadding = totalPadding - leftPadding;

                sb.append(vLine)
                        .append(BLANK.repeat(leftPadding))
                        .append(ss)
                        .append(RESET)
                        .append(BLANK.repeat(rightPadding))
                        .append(vLine)
                        .append(Constants.NEWLINE);
            }
        }
    }


    public static void drawBox(StringBuilder sb, BoxWrapper boxWrapper){
        final int centerPadding = boxWrapper.boxConfiguration().getCenterPadding();
        final String spaces = BLANK.repeat(boxWrapper.width() - centerPadding);
        final String hLines = sb.repeat(boxWrapper.hLine(), boxWrapper.width() - centerPadding).toString();
        final TextAlign textAlign = boxWrapper.boxConfiguration().getTextAlign();
        clearStringBuilder(sb);
        sb.append(boxWrapper.tLeft()).append(hLines).append(boxWrapper.tRight()).append(Constants.NEWLINE);

        int startLine = 1;
        final int availableLines = boxWrapper.length() - centerPadding;
        final int textLines = boxWrapper.wordWrap().size();

        if(textAlign == TextAlign.CENTER || textAlign == TextAlign.CENTER_LEFT || textAlign == TextAlign.CENTER_RIGHT){
            startLine = 1 + (availableLines - textLines) / 2;
        } else if(textAlign == TextAlign.BOTTOM_LEFT || textAlign == TextAlign.BOTTOM_CENTER || textAlign == TextAlign.BOTTOM_RIGHT){
            startLine = 1 + (availableLines - textLines);
        }

        for (int i = 1; i < boxWrapper.length() - 1; i++){
            if(i >= startLine && i < (startLine + textLines)){
                int textIndex = i - startLine;
                alignText(sb, textIndex, textAlign, spaces, boxWrapper.wordWrap(), boxWrapper.vLine());
            } else {
                sb.append(boxWrapper.vLine()).append(spaces).append(boxWrapper.vLine()).append(Constants.NEWLINE);
            }
        }

        sb.append(boxWrapper.bLeft()).append(hLines).append(boxWrapper.bRight());
    }

    public static String[] splitPreservingAnsi(String styledContent) {
        final List<String> words = new ArrayList<>();
        final StringBuilder currentWord = new StringBuilder();
        final StringBuilder activeAnsiCodes = new StringBuilder(); // Track active formatting
        boolean inEscapeSequence = false;
        final StringBuilder currentEscape = new StringBuilder();

        for (int i = 0; i < styledContent.length(); i++) {
            final char c = styledContent.charAt(i);

            if (c == ANSI_BEGIN) {
                inEscapeSequence = true;
                clearStringBuilder(currentEscape);
                currentEscape.append(c);
            } else if (inEscapeSequence) {
                currentEscape.append(c);
                if (c == ANSI_END) { //End of ansi seq
                    inEscapeSequence = false;
                    final String escapeSeq = currentEscape.toString();

                    // Add to current word
                    currentWord.append(escapeSeq);

                    // Track active codes
                    if (escapeSeq.equals(RESET)) {
                        clearStringBuilder(activeAnsiCodes); // Reset
                    } else {
                        activeAnsiCodes.append(escapeSeq); // Accumulate
                    }
                }

            } else if (Character.isWhitespace(c)) { //Check if a char is a whitespace
                if (!currentWord.isEmpty()) {
                    // Close any active formatting
                    if (!activeAnsiCodes.isEmpty()) {
                        currentWord.append(RESET);
                    }

                    words.add(currentWord.toString());
                    // Start new word with active formatting
                    clearStringBuilder(currentWord);
                    if (!activeAnsiCodes.isEmpty()) {
                        currentWord.append(activeAnsiCodes);
                    }
                }
            } else {
                currentWord.append(c);
            }
        }

        if (!currentWord.isEmpty()) {
            if (!activeAnsiCodes.isEmpty()) {
                currentWord.append(RESET);
            }
            words.add(currentWord.toString());
        }

        return words.toArray(String[]::new);
    }

    public static <T> T handleDimensionsEx(ExceptionSupplier<T> e){
        try {
             return e.supply();
        }catch (IllegalArgumentException | StringIndexOutOfBoundsException ex){
            throw new InvalidDimensionException("The dimensions of this box are too small to wrap around the given content. You can prevent this by using the `autoSize` box configuration", ex);
        }
    }

}
