package io.github.kusoroadeolu.clique.style;


import io.github.kusoroadeolu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.core.display.Accumulated;

import java.io.PrintStream;

public sealed interface StyleBuilder extends Accumulated permits DefaultStyleBuilder {
    String format(String text, AnsiCode... ansiCodes);
    String formatAndReset(String text, AnsiCode... ansiCodes);
    StyleBuilder stack(String text, AnsiCode... ansiCodes);
    StyleBuilder append(String text, AnsiCode... ansiCodes);
    void print(PrintStream stream);
    String get();
    void flush();

}
