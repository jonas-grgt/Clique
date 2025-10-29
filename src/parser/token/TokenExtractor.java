package parser.token;

import core.ansi.interfaces.AnsiCode;
import parser.token.dtos.ParserToken;
import parser.token.exceptions.UnidentifiedStyleException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static parser.map.StyleMaps.*;

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
     * Extracts valid tokens from the given string
     * @param stringToParse The string to parse
     * @return A list of parser tokens
     * */
    public List<ParserToken> extractTokens(String stringToParse){
        final List<ParserToken> tokens = new ArrayList<>(); //List to store the tokens gotten from this string
        if(stringToParse == null || stringToParse.isEmpty()) return tokens;

        final int len = stringToParse.length();
        int fs = 0; //The start of the tag

        for (int i = 0; i < len; i++){
            final char c = stringToParse.charAt(i);
            if (c == FORM_START){ //This will always switch the form start, if it finds another [ after this
                fs = i;
            }

            if (c == FORM_CLOSE){
                String extractedStr = stringToParse.substring(fs, i + 1); //Parse the extracted string and skip the braces
                List<AnsiCode> validStyles = this.getValidStyles(extractedStr);

                if(validStyles != null && !validStyles.isEmpty()){ //Check if there are any valid styles in the list
                    tokens.add(new ParserToken(fs, i, validStyles));
                }
            }
        }

        return tokens;
    }


    //Check if there are valid styles in the extracted string
    private List<AnsiCode> getValidStyles(String extractedStr) {
        if (extractedStr.length() <= 2) return null;  //Check if the extracted string is probably empty braces

        extractedStr = this.cleanString(extractedStr); //Clean the string

        final String[] styles = extractedStr.split(delimiter);
        final List<AnsiCode> validStyles = new ArrayList<>();
        for (String s : styles){
            s = s.toLowerCase(Locale.ROOT).trim();
            this.addExistingValidStyles(s, validStyles);

        }

        return validStyles;
    }

    private String cleanString(String s){
        return s.replace(String.valueOf(FORM_START), EMPTY).replace(String.valueOf(FORM_CLOSE), EMPTY);
    }

    // A helper method that checks if each map contains a key of s
    private void addExistingValidStyles(String s, List<AnsiCode> validStyles){
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

    public void setDelimiter(char delimiter) {
        this.delimiter = String.valueOf(delimiter);
    }

    public void setEnableStrictParsing(boolean enableStrictParsing) {
        this.enableStrictParsing = enableStrictParsing;

    }
}
