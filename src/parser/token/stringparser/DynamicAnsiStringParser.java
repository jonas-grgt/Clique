package parser.token.stringparser;

import parser.token.StyleApplicator;
import parser.token.TokenExtractor;
import parser.token.dtos.ParserToken;
import parser.token.stringparser.interfaces.AnsiStringParser;

import java.util.List;

public class DynamicAnsiStringParser implements AnsiStringParser {

    private final StyleApplicator applicator;
    private final TokenExtractor tokenExtractor;

    public DynamicAnsiStringParser(){
        this.applicator = new StyleApplicator();
        this.tokenExtractor = new TokenExtractor();
    }

    public String parse(String stringToParse){
        final List<ParserToken> extractedTokens = this.tokenExtractor.extractTokens(stringToParse);
        return this.applicator.restyleString(extractedTokens, stringToParse);
    }

    public AnsiStringParser enableStrictParsing(){
        this.tokenExtractor.setEnableStrictParsing(true);
        return this;
    }

    public AnsiStringParser setDelimiter(char delimiter){
        this.tokenExtractor.setDelimiter(delimiter);
        return this;
    }


}
