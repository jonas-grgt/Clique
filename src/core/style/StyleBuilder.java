package core.style;

import core.ansi.interfaces.AnsiCode;

public sealed interface StyleBuilder permits StyleBuilderImpl {
    String format(String text, AnsiCode ansiCode);
    String format(String text, AnsiCode... ansiCodes);
    String formatReset(String text, AnsiCode ansiCode);
    String formatReset(String text, AnsiCode... ansiCodes);

    StyleBuilder stack(String text, AnsiCode ansiCode);
    StyleBuilder stack(String text, AnsiCode... ansiCodes);

    StyleBuilder append(String text, AnsiCode ansiCode);
    StyleBuilder append(String text, AnsiCode... ansiCodes);
    void print();
    String get();

}
