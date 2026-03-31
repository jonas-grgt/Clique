package io.github.kusoroadeolu.clique.style;


import io.github.kusoroadeolu.clique.ansi.StyleCode;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.io.PrintStream;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.AnsiDetector.ansiEnabled;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.clearStringBuilder;

@InternalApi(since = "3.2.0")
public non-sealed class DefaultStyleBuilder implements StyleBuilder {

    private static final AnsiCode RESET = StyleCode.RESET;
    private final StringBuilder textBuilder;

    public DefaultStyleBuilder() {
        this.textBuilder = new StringBuilder();
    }


    @Override
    public String format(String text, AnsiCode... ansiCodes) {
        return this.style(text, ansiCodes).toString();
    }



    @Override
    public String formatAndReset(String text, AnsiCode... ansiCodes) {
        return this.format(text, ansiCodes) + RESET;
    }


    @Override
    public StyleBuilder stack(String text, AnsiCode... ansiCodes) {
        this.textBuilder.append(this.style(text, ansiCodes));
        return this;
    }


    @Override
    public StyleBuilder append(String text, AnsiCode... ansiCodes) {
        this.stack(text, ansiCodes);
        this.textBuilder.append(RESET);
        return this;
    }


    @Override
    public String get() {
        return this.textBuilder.toString();
    }


    @Override
    public void print(PrintStream stream) {
        Objects.requireNonNull(stream, "Print stream cannot be null");
        this.textBuilder.append(RESET); //Reset all styles
        stream.println(this.textBuilder);
    }

    //A helper method to style text with the given codes
    private StringBuilder style(String text, AnsiCode... ansiCodes) {
        Objects.requireNonNull(text, "Text cannot be null");
        Objects.requireNonNull(ansiCodes, "Ansi codes cannot be null");
        final StringBuilder sb = new StringBuilder();

        //Check if ansi is enabled
        if (!ansiEnabled()) return sb.append(text);

        for (AnsiCode code : ansiCodes) {
            if (code != null){
                sb.append(code);
            }
        }

        return sb.append(text);
    }

    @Override
    public void flush() {
        clearStringBuilder(textBuilder);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        var that = (DefaultStyleBuilder) object;
        return textBuilder.compareTo(that.textBuilder) == 0;
    }

    @Override
    public int hashCode() {
        return textBuilder.hashCode();
    }

    @Override
    public String toString() {
        return "DefaultStyleBuilder[" +
                "text" + textBuilder +
                ']';
    }
}
