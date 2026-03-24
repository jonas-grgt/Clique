package io.github.kusoroadeolu.clique.parser;

public interface AnsiStringParser {
    AnsiStringParser DEFAULT = new AnsiStringParserImpl();


    String parse(String stringToParse);

    default void print(String string) {
        System.out.println(this.parse(string));
    }

    default void print(Object object) {
        this.print(object.toString());
    }

    String parse(Object object);

    String getOriginalString(String string);

}
