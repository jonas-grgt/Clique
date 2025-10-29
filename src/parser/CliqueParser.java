package parser;

import core.ansi.interfaces.AnsiCode;
import core.clique.Clique;
import core.style.builder.StyleBuilder;
import parser.token.ParserToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static parser.maps.CliqueParserMaps.*;

/**
 * Note that this class will ignore malformed tags and print them as is
 * */
public final class CliqueParser {

    private final static char FORM_START = '[';
    private final static char FORM_CLOSE = ']';
    private final static String DELIMITER = ",";
    private final static String EMPTY = "";

    public String parse(String stringToParse){
        final List<ParserToken> extractedTokens = this.extractTokens(stringToParse);
        return this.restyleString(extractedTokens, stringToParse);
    }

    /**
     * Extracts valid token
     * */
    public List<ParserToken> extractTokens(String stringToParse){
        List<ParserToken> tokens = new ArrayList<>(); //List to store the tokens gotten from this string
        if(stringToParse == null || stringToParse.isEmpty())return tokens;

        final int len = stringToParse.length();
        int fs = 0; //The start of the tag

        for (int i = 0; i < len; i++){
            final char c = stringToParse.charAt(i);
            if (c == FORM_START){ //This will always switch the form start, if it finds another [ after this
                fs = i;
            }

            if (c == FORM_CLOSE){ //We're always looking for the close form tag
                String extractedStr = stringToParse.substring(fs, i + 1); //Parse the extracted string and skip the braces
                List<AnsiCode> validStyles = this.getValidStyles(extractedStr);

                if(validStyles != null && !validStyles.isEmpty()){ //Check if there are any valid styles in the list
                    tokens.add(new ParserToken(fs, i, validStyles));
                }
            }
        }

        return tokens;
    }


    //Restyle the extracted string with the given colors
    private String restyleString(List<ParserToken> tokens, String extractedString) {
        final StringBuilder sb = new StringBuilder();
        final StyleBuilder stb = Clique.printer();
        String val;
        final int size = tokens.size();

        if (tokens.isEmpty()){
            return extractedString;
        }


        //Check if the styling starts from the beginning of the string
        if(tokens.getFirst().start() != 0){
            sb.append(extractedString, 0, tokens.getFirst().start());
        }

        for (int i = 0; i < size; i++){
            ParserToken curr = tokens.get(i);
            ParserToken next = i != (size - 1) ? tokens.get(i + 1) :
                    new ParserToken(extractedString.length(), 0, null); //if we're at the end of the loop, we apply the current style to the rem of the string

            final AnsiCode[] codes = curr.validStyles().toArray(new AnsiCode[0]);
            final int start = curr.end() + 1;
            final int end = next.start();
            val = extractedString.substring(start , end);
            sb.append(stb.apply(val, codes)); //Append the styled string to the builder. Does not reset the terminal
        }

        return sb.toString();
    }

    //Check if there are valid styles in the extracted string
    private List<AnsiCode> getValidStyles(String extractedStr) {
        if (extractedStr.length() <= 2) return null;  //Check if the extracted string is probably empty braces

        extractedStr = this.cleanString(extractedStr); //Clean the string

        String[] styles = extractedStr.split(DELIMITER);
        List<AnsiCode> validStyles = new ArrayList<>();
        for (String s : styles){
            s = s.toLowerCase(Locale.ROOT).trim();
            this.addExistingValidStyles(s, validStyles);

        }

        return validStyles;
    }


    // A helper method that checks if each map contains a key of s
    private void addExistingValidStyles(String s, List<AnsiCode> validStyles){
        if(colorCodes.containsKey(s)){
            validStyles.add(colorCodes.get(s));
            return;
        }

        if(backgroundCodes.containsKey(s)){
            validStyles.add(backgroundCodes.get(s));
            return;
        }

        if(styleCodes.containsKey(s)){
            validStyles.add(styleCodes.get(s));
        }
    }

    private String cleanString(String s){
        return s.replace(String.valueOf(FORM_START), EMPTY).replace(String.valueOf(FORM_CLOSE), EMPTY);
    }


}
