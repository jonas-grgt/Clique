package com.github.kusoroadeolu.clique.parser;

import com.github.kusoroadeolu.clique.config.ParserConfiguration;
import com.github.kusoroadeolu.clique.core.exceptions.DeprecatedMethodException;

public class AnsiStringParserImpl implements AnsiStringParser {

     private final StyleApplicator applicator;
     private final TokenExtractor tokenExtractor;
     private final ParserConfiguration parserConfiguration;
     private String stringToParse;
     private ParseResult parseResult;

     public AnsiStringParserImpl(){
        this(ParserConfiguration.immutableBuilder().build());
    }

    public AnsiStringParserImpl(ParserConfiguration parserConfiguration){
        this.parserConfiguration = parserConfiguration;
        this.applicator = new StyleApplicator();
        this.tokenExtractor = new TokenExtractor();
        this.updateConfiguration();
    }

    @Deprecated
    public AnsiStringParser configuration(ParserConfiguration configuration) {
        throw new DeprecatedMethodException("Deprecated method. Use constructor configurations instead");

    }

    public String parse(String string){
        final ParseResult result = this.tokenExtractor
                .getParseResult(string);

        this.stringToParse = string;
        this.parseResult = result;
        return this.applicator.restyleString(result.tokens(), string);
    }

    public String parse(Object object){
         return this.parse(object.toString());
    }

    public String getOriginalString(){
         if(this.parseResult == null){
             throw new IllegalArgumentException("No string has been parsed by the parser yet");
         }

         String originalString = this.stringToParse;
         int size = this.parseResult.extractedFormTags().size();
         for (int i = 0; i < size; i++){
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
