package io.github.kusoroadeolu.clique.style;


import io.github.kusoroadeolu.clique.internal.documentation.InternalApi;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.io.PrintStream;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.internal.utils.StringUtils.style;

@InternalApi(since = "3.2.0")
public final class StyleBuilder {

    private static final AnsiCode RESET = StyleCode.RESET;
    private final StringBuilder textBuilder;

    public StyleBuilder() {
        this.textBuilder = new StringBuilder();
    }

    /**
     * Appends multiple ANSI Codes to this text
     *
     * @param text      The text we're appending to the style builder
     * @param ansiCodes The styles we're applying to the text
     * @return The instance of this class
     *
     */
    public StyleBuilder stack(String text, AnsiCode... ansiCodes) {
        style(text, textBuilder ,ansiCodes);
        return this;
    }


    /**
     * Appends an ANSI code to this text and then resets the terminal
     *
     * @param text      The text we're applying the ansiCode to
     * @param ansiCodes The ANSI code we're applying to the text
     * @return The styled text
     *
     */
    public StyleBuilder append(String text, AnsiCode... ansiCodes) {
        this.stack(text, ansiCodes);
        this.textBuilder.append(RESET.ansiSequence());
        return this;
    }


    public String toString() {
        return this.textBuilder.toString();
    }

    public void print(PrintStream stream) {
        Objects.requireNonNull(stream, "Print stream cannot be null");
        stream.println(this);
    }

    public void print() {
        print(System.out);
    }


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        var that = (StyleBuilder) object;
        return textBuilder.compareTo(that.textBuilder) == 0;
    }

    @Override
    public int hashCode() {
        return textBuilder.hashCode();
    }

}
