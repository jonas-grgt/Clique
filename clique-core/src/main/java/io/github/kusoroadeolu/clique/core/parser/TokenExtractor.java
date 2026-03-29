package io.github.kusoroadeolu.clique.core.parser;

import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.core.exceptions.ParseProblemException;
import io.github.kusoroadeolu.clique.core.exceptions.UnidentifiedStyleException;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * This class extracts valid token forms from the given string
 * Note that this class will ignore malformed tags and print them as is
 *
 */
@InternalApi(since = "3.1.3")
public final class TokenExtractor {
    private static final char FORM_START = '[';
    private static final char FORM_CLOSE = ']';
    private static final String MARKUP_ESCAPE = "[/]";

    /**
     * Extracts valid tokens and form tags from the given string
     *
     * @param stringToParse The string to parse
     * @return A parse result containing the form tags and the parser tokens
     *
     */
    public ParseResult getParseResult(String stringToParse, String delimiter, boolean enableStrictParsing) {
        final List<ParseToken> tokens = new ArrayList<>(); //List to store the tokens gotten from this string
        final List<String> formTags = new ArrayList<>(); //Tracks the form tags extracted from this string

        if (stringToParse == null || stringToParse.isEmpty()) return new ParseResult(List.of(), List.of());

        final int len = stringToParse.length();
        int tagStart = 0; //The start of the tag
        boolean inMarkupTag = false; //Tracking boolean to keep track of if we're currently tracking a style or not

        for (int i = 0; i < len; i++) {
            final char c = stringToParse.charAt(i);
            if (c == FORM_START) { //This will always switch the form start, if it finds another [ after this
                if (inMarkupTag && enableStrictParsing) { //If we're still tracking, this means we have nested form start
                    int j = i + 3;
                    if (j < len){
                        String shorthand = stringToParse.substring(i,  i + 3);
                        if (!shorthand.equals(MARKUP_ESCAPE)){
                            throw new ParseProblemException("Nested tag detected at index: " + i);
                        }
                    }
                }
                tagStart = i;
                inMarkupTag = true;
            }

            if (c == FORM_CLOSE && inMarkupTag) { //Only parse the string if we're still tracking the valid tag
                final String markupTag = stringToParse.substring(tagStart, i + 1); //Parse the extracted string and skip the braces
                final List<AnsiCode> validStyles = this.getValidStyles(markupTag, delimiter, enableStrictParsing);
                if (validStyles != null && !validStyles.isEmpty()) {
                    tokens.add(new ParseToken(tagStart, i, validStyles));
                    formTags.add(markupTag);
                    inMarkupTag = false;
                }
            }
        }

        return new ParseResult(tokens, formTags);
    }


    //Check if there are valid styles in the extracted string
    //ESP -> Enable strict parsing
    private List<AnsiCode> getValidStyles(String extractedStr, String delimiter, boolean esp) {
        if (extractedStr.length() <= 2) return null;  //Check if the extracted string is just empty braces
        extractedStr = this.cleanString(extractedStr); //Clean the string

        final String[] styles = extractedStr.split(Pattern.quote(delimiter));
        final List<AnsiCode> validStyles = new ArrayList<>();
        for (String s : styles) {
            s = s.toLowerCase(Locale.ROOT).trim();
            this.addValidStyles(s, validStyles, esp);
        }

        return validStyles;
    }


    //Replaces forms with empty strings
    private String cleanString(String s) {
        return s.substring(1, s.length() - 1);
    }

    // A helper method that checks if each map contains a key of the given string
    private void addValidStyles(String s, List<AnsiCode> validStyles, boolean enableStrictParsing) {
        AnsiCode code = StyleMaps.CUSTOM_STYLE_CODES.get(s);
        if (code != null) {
            validStyles.add(code);
            return;
        }

        code = StyleMaps.COLOR_CODES.get(s);
        if (code != null) {
            validStyles.add(code);
            return;
        }

        code = StyleMaps.BACKGROUND_CODES.get(s);
        if (code != null) {
            validStyles.add(code);
            return;
        }

        code = StyleMaps.STYLE_CODES.get(s);
        if (code != null) {
            validStyles.add(code);
            return;
        }

        if (enableStrictParsing) {
            throw new UnidentifiedStyleException("Failed to find style: `%s` in given string".formatted(s));
        }
    }
}
