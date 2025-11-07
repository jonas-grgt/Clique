package com.github.kusoroadeolu.clique.parser;

import com.github.kusoroadeolu.clique.ansi.AnsiCode;
import com.github.kusoroadeolu.clique.core.exceptions.ParseProblemException;
import com.github.kusoroadeolu.clique.core.exceptions.UnidentifiedStyleException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.github.kusoroadeolu.clique.parser.StyleMaps.*;

/**
 * This class extracts valid token forms from the given string
 * Note that this class will ignore malformed tags and print them as is
 * */
public final class TokenExtractor {

    private final static char FORM_START = '[';
    private final static char FORM_CLOSE = ']';
    private String delimiter;
    private boolean enableStrictParsing;
    private final static String EMPTY = "";
    public TokenExtractor(){
        this.delimiter = String.valueOf(',');
        this.enableStrictParsing = false;
    }


    /**
     * Extracts valid tokens and form tags from the given string
     * @param stringToParse The string to parse
     * @return A parse result containing the form tags and the parser tokens
     * */
    public ParseResult getParseResult(String stringToParse){
        final List<ParserToken> tokens = new ArrayList<>(); //List to store the tokens gotten from this string
        final List<String> formTags = new ArrayList<>(); //Tracks the form tags extracted from this string

        if(stringToParse == null || stringToParse.isEmpty()) return new ParseResult(List.of(), List.of());

        final int len = stringToParse.length();
        int fs = 0; //The start of the tag
        boolean isTracking = false; //Tracking boolean to keep track of if we're currently tracking a style or not

        for (int i = 0; i < len; i++){
            final char c = stringToParse.charAt(i);
            if (c == FORM_START){ //This will always switch the form start, if it finds another [ after this
                if(isTracking && this.enableStrictParsing){ //If we're still tracking, this means we have nested form starts
                    throw new ParseProblemException("Nested tag detected without closure at char: " + i);
                }

                fs = i;
                isTracking = true;
            }

            if (c == FORM_CLOSE && isTracking){ //Only parse the string if we're still tracking the valid tag
                final String fullTag = stringToParse.substring(fs, i + 1); //Parse the extracted string and skip the braces
                formTags.add(fullTag);

                List<AnsiCode> validStyles = this.getValidStyles(fullTag);
                if(validStyles != null && !validStyles.isEmpty()){
                    tokens.add(new ParserToken(fs, i, validStyles));
                    isTracking = false;
                }
            }
        }

        return new ParseResult(tokens, formTags);
    }


    //Check if there are valid styles in the extracted string
    private List<AnsiCode> getValidStyles(String extractedStr) {
        if (extractedStr.length() <= 2) return null;  //Check if the extracted string is probably empty braces
        extractedStr = this.cleanString(extractedStr); //Clean the string

        final String[] styles = extractedStr.split(delimiter);
        final List<AnsiCode> validStyles = new ArrayList<>();
        for (String s : styles){
            s = s.toLowerCase(Locale.ROOT).trim();
            this.addValidStyles(s, validStyles);

        }

        return validStyles;
    }


    //Replaces forms with empty strings
    private String cleanString(String s){
        return s.replace(String.valueOf(FORM_START), EMPTY).replace(String.valueOf(FORM_CLOSE), EMPTY);
    }

    // A helper method that checks if each map contains a key of the given string
    private void addValidStyles(String s, List<AnsiCode> validStyles){
        AnsiCode code = colorCodes.get(s);
        if(code != null){
            validStyles.add(code);
            return;
        }

        code = backgroundCodes.get(s);
        if(code != null){
            validStyles.add(code);
            return;
        }

        code = styleCodes.get(s);
        if(code != null){
            validStyles.add(code);
            return;
        }

        if(this.enableStrictParsing){
            throw new UnidentifiedStyleException("Failed to find style: `%s` in given string".formatted(s));
        }
    }

    public TokenExtractor setDelimiter(String delimiter) {
        this.delimiter = String.valueOf(delimiter);
        return this;
    }


    public TokenExtractor setEnableStrictParsing(boolean enableStrictParsing) {
        this.enableStrictParsing = enableStrictParsing;
        return this;

    }
}
