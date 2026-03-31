package io.github.kusoroadeolu.clique.parser;

import io.github.kusoroadeolu.clique.core.documentation.Stable;

/**
 * @since 1.0.0
 * */
@Stable(since = "3.2.0")
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
