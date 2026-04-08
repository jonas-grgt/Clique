package io.github.kusoroadeolu.clique.style;


import io.github.kusoroadeolu.clique.ansi.StyleCode;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.io.PrintStream;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.StringUtils.style;

@InternalApi(since = "3.2.0")
public class StyleBuilder {

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


    public String get() {
        return this.textBuilder.toString();
    }


    public void print(PrintStream stream) {
        Objects.requireNonNull(stream, "Print stream cannot be null");
        this.textBuilder.append(RESET); //Reset all styles
        stream.println(this.textBuilder);
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

    @Override
    public String toString() {
        return "StyleBuilder[" +
                "text" + textBuilder +
                ']';
    }
}
