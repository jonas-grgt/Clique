package io.github.kusoroadeolu.clique.style;


import io.github.kusoroadeolu.clique.ansi.StyleCode;
import io.github.kusoroadeolu.clique.core.display.Borderless;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.io.PrintStream;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.AnsiDetector.ansiEnabled;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.clearStringBuilder;

@InternalApi(since = "3.2.0")
public class StyleBuilder implements Borderless {

    private static final AnsiCode RESET = StyleCode.RESET;
    private final StringBuilder textBuilder;

    public StyleBuilder() {
        this.textBuilder = new StringBuilder();
    }

    @InternalApi(since = "4.0.0")
    public static String format(StringBuilder sb, String text, AnsiCode... ansiCodes) {
        style(text, sb, ansiCodes);
        String result = sb.toString();
        clearStringBuilder(sb);
        return result;
    }


    @InternalApi(since = "4.0.0")
    public static String formatAndReset(StringBuilder sb, String text, AnsiCode... ansiCodes) {
        style(text, sb, ansiCodes);
        sb.append(RESET);
        String result = sb.toString();
        clearStringBuilder(sb);
        return result;
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
    private static StringBuilder style(String text, StringBuilder sb ,AnsiCode... ansiCodes) {
        Objects.requireNonNull(text, "Text cannot be null");
        Objects.requireNonNull(ansiCodes, "Ansi codes cannot be null");

        //Check if ansi is enabled
        if (ansiEnabled()) {
            for (AnsiCode code : ansiCodes) {
                if (code != null) sb.append(code);

            }
        }

        return sb.append(text);
    }


    /**
     * Clears the string builder
     *
     */
    @Override
    public void flush() {
        clearStringBuilder(textBuilder);
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
