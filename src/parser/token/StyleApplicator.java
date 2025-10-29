package parser.token;

import core.ansi.interfaces.AnsiCode;
import core.clique.Clique;
import core.style.StyleBuilder;
import parser.token.dtos.ParserToken;
import parser.token.exceptions.ParseProblemException;

import java.util.List;


public final class StyleApplicator {

    //Restyle the extracted string with the given colors
    public String restyleString(List<ParserToken> tokens, String extractedString) {
        final StringBuilder sb = new StringBuilder();
        final StyleBuilder stb = Clique.styleBuilder();
        String val;
        final int size = tokens.size();

        if (tokens.isEmpty()){
            return extractedString;
        }

        //Check if the styling starts from the beginning of the string
        if(tokens.getFirst().start() != 0){
            sb.append(extractedString, 0, tokens.getFirst().start());
        }

        try{
            for (int i = 0; i < size; i++){
                ParserToken curr = tokens.get(i);
                ParserToken next = i != (size - 1) ? tokens.get(i + 1) :
                        new ParserToken(extractedString.length(), 0, null); //if we're at the end of the loop, we apply the current style to the rem of the string

                final AnsiCode[] codes = curr.validStyles().toArray(new AnsiCode[0]);
                final int start = curr.end() + 1;
                final int end = next.start();
                val = extractedString.substring(start , end);
                sb.append(stb.format(val, codes)); //Append the styled string to the builder. Does not reset the terminal
            }
        }catch (StringIndexOutOfBoundsException e){
            throw new ParseProblemException("Failed to parse string. This often happens when style tags have nested brackets like '[[red]]'.", e);
        }

        return sb.toString();
    }

}
