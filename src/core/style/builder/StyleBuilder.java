package core.style.builder;

import core.ansi.interfaces.AnsiCode;

public sealed interface StyleBuilder permits StyleBuilderImpl {
    String apply(String text, AnsiCode ansiCode);
    StyleBuilder append(String text, AnsiCode ansiCode);
    String apply(String text, AnsiCode... ansiCodes);
    StyleBuilder append(String text, AnsiCode... ansiCodes);

    StyleBuilder appendAndReset(String text, AnsiCode ansiCode);
    StyleBuilder appendAndReset(String text, AnsiCode... ansiCodes);
    void print();
    String get();

}
