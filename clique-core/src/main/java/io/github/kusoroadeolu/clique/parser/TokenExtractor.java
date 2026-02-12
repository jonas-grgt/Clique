package io.github.kusoroadeolu.clique.parser;

import io.github.kusoroadelu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.core.exceptions.ParseProblemException;
import io.github.kusoroadeolu.clique.core.exceptions.UnidentifiedStyleException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static io.github.kusoroadeolu.clique.core.utils.Constants.EMPTY;
import static io.github.kusoroadeolu.clique.parser.StyleMaps.*;

/**
 * This class extracts valid token forms from the given string
 * Note that this class will ignore malformed tags and print them as is
 * */
public final class TokenExtractor {
    private final static char FORM_START = '[';
    private final static char FORM_CLOSE = ']';

    /**
     * Extracts valid tokens and form tags from the given string
     * @param stringToParse The string to parse
     * @return A parse result containing the form tags and the parser tokens
     * */
    public ParseResult getParseResult(String stringToParse, String delimiter, boolean enableStrictParsing){
        final List<ParserToken> tokens = new ArrayList<>(); //List to store the tokens gotten from this string
        final List<String> formTags = new ArrayList<>(); //Tracks the form tags extracted from this string

        if(stringToParse == null || stringToParse.isEmpty()) return new ParseResult(List.of(), List.of());

        final int len = stringToParse.length();
        int fs = 0; //The start of the tag
        boolean isTracking = false; //Tracking boolean to keep track of if we're currently tracking a style or not

        for (int i = 0; i < len; i++){
            final char c = stringToParse.charAt(i);
            if (c == FORM_START){ //This will always switch the form start, if it finds another [ after this
                if(isTracking && enableStrictParsing){ //If we're still tracking, this means we have nested form starts
                    throw new ParseProblemException("Nested tag detected without closure at char: " + i);
                }

                fs = i;
                isTracking = true;
            }

            if (c == FORM_CLOSE && isTracking){ //Only parse the string if we're still tracking the valid tag
                final String fullTag = stringToParse.substring(fs, i + 1); //Parse the extracted string and skip the braces
                final List<AnsiCode> validStyles = this.getValidStyles(fullTag, delimiter, enableStrictParsing);
                if(validStyles != null && !validStyles.isEmpty()){
                    tokens.add(new ParserToken(fs, i, validStyles));
                    formTags.add(fullTag);
                    isTracking = false;
                }
            }
        }

        return new ParseResult(tokens, formTags);
    }


    //Check if there are valid styles in the extracted string
    private List<AnsiCode> getValidStyles(String extractedStr, String delimiter, boolean esp) {
        if (extractedStr.length() <= 2) return null;  //Check if the extracted string is probably empty braces
        extractedStr = this.cleanString(extractedStr); //Clean the string

        final String[] styles = extractedStr.split(Pattern.quote(delimiter));
        final List<AnsiCode> validStyles = new ArrayList<>();
        for (String s : styles){
            s = s.toLowerCase(Locale.ROOT).trim();
            this.addValidStyles(s, validStyles, esp);
        }

        return validStyles;
    }


    //Replaces forms with empty strings
    private String cleanString(String s){
        return s.replace(String.valueOf(FORM_START), EMPTY)
                .replace(String.valueOf(FORM_CLOSE), EMPTY);
    }

    // A helper method that checks if each map contains a key of the given string
    private void addValidStyles(String s, List<AnsiCode> validStyles, boolean enableStrictParsing){
        AnsiCode code = GLOBAL_CUSTOM_CODES.get(s);
        if (code != null){
            validStyles.add(code);
            return;
        }

        code = COLOR_CODES.get(s);
        if(code != null){
            validStyles.add(code);
            return;
        }

        code = BACKGROUND_CODES.get(s);
        if(code != null){
            validStyles.add(code);
            return;
        }

        code = STYLE_CODES.get(s);
        if(code != null){
            validStyles.add(code);
            return;
        }

        if(enableStrictParsing){
            throw new UnidentifiedStyleException("Failed to find style: `%s` in given string".formatted(s));
        }
    }
}
