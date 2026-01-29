package com.github.kusoroadeolu.clique.style;

import com.github.kusoroadeolu.clique.ansi.AnsiCode;
import com.github.kusoroadeolu.clique.core.display.Printable;

import java.io.PrintStream;

public sealed interface StyleBuilder extends Printable permits StyleBuilderImpl {
    String format(String text, AnsiCode... ansiCodes);
    String formatReset(String text, AnsiCode... ansiCodes);
    StyleBuilder stack(String text, AnsiCode... ansiCodes);

    StyleBuilder append(String text, AnsiCode... ansiCodes);
    void print(PrintStream stream);
    String get();
    void flush();

}
