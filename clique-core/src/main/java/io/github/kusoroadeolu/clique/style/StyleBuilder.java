package io.github.kusoroadeolu.clique.style;


import io.github.kusoroadeolu.clique.core.display.Borderless;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.io.PrintStream;

public sealed interface StyleBuilder extends Borderless permits DefaultStyleBuilder {
    /**
     * Applies multiple ANSI codes to this text
     *
     * @param text      The text we're applying the ansiCode to
     * @param ansiCodes The ANSI code we're applying to the text
     * @return The styled text
     *
     */
    String format(String text, AnsiCode... ansiCodes);

    /**
     * Applies an ANSI Codes to this text and resets the terminal style
     *
     * @param text      The text we're applying the ansiCode to
     * @param ansiCodes The ANSI code we're applying to the text
     * @return The styled text
     *
     */
    String formatAndReset(String text, AnsiCode... ansiCodes);

    /**
     * Appends multiple ANSI Codes to this text
     *
     * @param text      The text we're appending to the style builder
     * @param ansiCodes The ansiCodes we're applying to the text
     * @return The instance of this class
     *
     */
    StyleBuilder stack(String text, AnsiCode... ansiCodes);

    /**
     * Appends an ANSI code to this text and then resets the terminal
     *
     * @param text      The text we're applying the ansiCode to
     * @param ansiCodes The ANSI code we're applying to the text
     * @return The styled text
     *
     */
    StyleBuilder append(String text, AnsiCode... ansiCodes);


    @Override
    void print(PrintStream stream);

    /**
     * @return the content of the string builder
     *
     */
    @Override
    String get();

    /**
     * Clears the string builder
     *
     */
    @Override
    void flush();

}
