package io.github.kusoroadeolu.clique.style;


import io.github.kusoroadeolu.clique.core.display.Borderless;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.io.PrintStream;

public sealed interface StyleBuilder extends Borderless permits DefaultStyleBuilder {
    String format(String text, AnsiCode... ansiCodes);

    String formatAndReset(String text, AnsiCode... ansiCodes);

    StyleBuilder stack(String text, AnsiCode... ansiCodes);

    StyleBuilder append(String text, AnsiCode... ansiCodes);

    @Override
    void print(PrintStream stream);

    @Override
    String get();

    @Override
    void flush();

}
