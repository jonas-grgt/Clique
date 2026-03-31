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
 */
@InternalApi(since = "3.2.0")
public final class TokenExtractor {
    private static final char FORM_START = '[';
    private static final char FORM_CLOSE = ']';

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
        final String delimiterPattern = Pattern.quote(delimiter);

        if (stringToParse == null || stringToParse.isEmpty()) return new ParseResult(List.of(), List.of());

        final int len = stringToParse.length();
        int idx = 0; //The start of the tag
        int fsDepth = 0; //Tracking boolean to keep track of the number of form starts we've seen
        int fcDepth = 0;

        for (int i = 0; i < len; i++) {
            final char c = stringToParse.charAt(i);

            if (c == FORM_START) { //This will always switch the form start, if it finds another [ after this
                //If we're still tracking, this means we have nested tag, just skip it
                fcDepth = 0; //Next form start, reset fc depth. Basically means we had something like this ]some_string[
                idx = i;
                ++fsDepth;
            }

            if (c == FORM_CLOSE) { //Only parse the string if we're still tracking the valid tag
                ++fcDepth;

                if (fsDepth == 1 && fcDepth == 1){ //Only if we dont have nested tags, with both open and closed tags
                    final String fullTag = stringToParse.substring(idx, i + 1); //Parse the extracted string and skip the braces
                    final List<AnsiCode> validStyles = this.getValidStyles(fullTag, delimiterPattern, enableStrictParsing);
                    if (!validStyles.isEmpty()) {
                        tokens.add(new ParseToken(idx, i, validStyles));
                        formTags.add(fullTag);
                    }
                }

                fsDepth = Math.max(0, --fsDepth);
            }
        }

        return new ParseResult(tokens, formTags);
    }

    /*
    * [red] valid, will get parsed
    * [notastyle] valid, won't get parsed
    * [nested[nested]] won't get parsed
    * [not closed [red, blue] hello    //Read nor blue will get parsed
    * */


    //Check if there are valid styles in the extracted string
    //ESP -> Enable strict parsing
    private List<AnsiCode> getValidStyles(String extractedStr, String delimiterPattern, boolean esp) {
        if (extractedStr.length() <= 2) return List.of();  //Check if the extracted string is just empty braces, or a malformed tag
        extractedStr = this.cleanString(extractedStr); //Clean the string

        final String[] styles = extractedStr.split(delimiterPattern);
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