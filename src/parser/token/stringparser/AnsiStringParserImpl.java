package parser.token.stringparser;

import parser.token.ParseResult;
import parser.token.StyleApplicator;
import parser.token.TokenExtractor;
import parser.configuration.ParserConfiguration;
import parser.token.stringparser.interfaces.AnsiStringParser;

public class AnsiStringParserImpl implements AnsiStringParser {

     private final StyleApplicator applicator;
     private final TokenExtractor tokenExtractor;
     private ParserConfiguration parserConfiguration;
     private String stringToParse;
     private ParseResult parseResult;

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
        final ParseResult result = this.tokenExtractor
                .getParseResult(stringToParse);

        this.stringToParse = stringToParse;
        this.parseResult = result;
        return this.applicator.restyleString(result.tokens(), stringToParse);
    }

    public String getOriginalString(){
         if(this.parseResult == null){
             throw new IllegalArgumentException("No string has been parsed by the parser yet");
         }

         String originalString = this.stringToParse;
         for (int i = 0; i < this.parseResult.extractedFormTags().size(); i++){
              originalString =
                      originalString.replace(this.parseResult.extractedFormTags().get(i), "");
         }

         return originalString;
    }

    private void updateConfiguration(){
        this.tokenExtractor
                .setDelimiter(this.parserConfiguration.getDelimiter())
                .setEnableStrictParsing(this.parserConfiguration.getEnableStrictParsing());
        this.applicator
                .setEnableAutoCloseTags(this.parserConfiguration.getEnableAutoCloseTags());
    }


}
