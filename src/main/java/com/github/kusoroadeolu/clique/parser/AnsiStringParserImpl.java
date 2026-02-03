package com.github.kusoroadeolu.clique.parser;

import com.github.kusoroadeolu.clique.config.ParserConfiguration;
import com.github.kusoroadeolu.clique.core.exceptions.DeprecatedMethodException;

import java.util.Objects;

import static com.github.kusoroadeolu.clique.core.utils.Constants.EMPTY;

public class AnsiStringParserImpl implements AnsiStringParser {

     private final StyleApplicator applicator;
     private final TokenExtractor tokenExtractor;
     private final ParserConfiguration parserConfiguration;

     public AnsiStringParserImpl(){
        this(ParserConfiguration.DEFAULT);
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

    public String parse(String tokenedString){
        final ParseResult result = this.tokenExtractor.getParseResult(tokenedString);
        return this.applicator.restyleString(result.tokens(), tokenedString, this.parserConfiguration.getEnableAutoCloseTags());
    }

    public String parse(Object object){
         return this.parse(object.toString());
    }

    public String getOriginalString(String tokenedString){
         if(tokenedString == null || tokenedString.isBlank()){
             throw new IllegalArgumentException("No string has been parsed by the parser yet");
         }
        final ParseResult result = this.tokenExtractor.getParseResult(tokenedString);
         int size = result.extractedFormTags().size();
         for (int i = 0; i < size; i++){
              tokenedString = tokenedString.replace(result.extractedFormTags().get(i), EMPTY);
         }
         return tokenedString;
    }

    private void updateConfiguration(){
        this.tokenExtractor
                .setDelimiter(this.parserConfiguration.getDelimiter())
                .setEnableStrictParsing(this.parserConfiguration.getEnableStrictParsing());
    }


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        AnsiStringParserImpl that = (AnsiStringParserImpl) object;
        return Objects.equals(parserConfiguration, that.parserConfiguration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parserConfiguration);
    }

    @Override
    public String toString() {
        return "AnsiStringParserImpl[" +
                "parserConfiguration=" + parserConfiguration +
                ']';
    }
}
