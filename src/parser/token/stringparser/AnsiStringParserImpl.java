package parser.token.stringparser;

import parser.token.StyleApplicator;
import parser.token.TokenExtractor;
import parser.configuration.ParserConfiguration;
import parser.token.ParserToken;
import parser.token.stringparser.interfaces.AnsiStringParser;

import java.util.List;

public class AnsiStringParserImpl implements AnsiStringParser {

     private final StyleApplicator applicator;
     private final TokenExtractor tokenExtractor;
     private ParserConfiguration parserConfiguration;

     public AnsiStringParserImpl(){
        this.parserConfiguration = ParserConfiguration.builder();
        this.applicator = new StyleApplicator();
        this.tokenExtractor = new TokenExtractor();
        this.updateConfiguration();
    }

    public AnsiStringParser configuration(ParserConfiguration configuration){
        this.parserConfiguration = configuration;
        this.updateConfiguration();
         return this;
    }

    public String parse(String stringToParse){
        final List<ParserToken> extractedTokens = this.tokenExtractor.extractTokens(stringToParse);
        return this.applicator.restyleString(extractedTokens, stringToParse);
    }

    private void updateConfiguration(){
        this.tokenExtractor
                .setDelimiter(this.parserConfiguration.getDelimiter())
                .setEnableStrictParsing(this.parserConfiguration.getEnableStrictParsing());
        this.applicator
                .setEnableAutoCloseTags(this.parserConfiguration.getEnableAutoCloseTags());
    }


}
