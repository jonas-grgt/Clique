package io.github.kusoroadeolu.clique.style;


import io.github.kusoroadeolu.clique.internal.documentation.Stable;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.io.PrintStream;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.internal.utils.StringUtils.style;

/**
 * A fluent builder for constructing ANSI-styled strings from multiple segments.
 *
 * <p>Text is accumulated via {@link #append} and {@link #appendAndReset}.
 * Use {@link #append} when you want styles to carry over into subsequent segments,
 * and {@link #appendAndReset} when each segment should be visually isolated.
 *
 * <pre>{@code
 * Clique.styleBuilder()
 *     .appendAndReset("Success: ", ColorCode.GREEN, StyleCode.BOLD)
 *     .appendAndReset("Operation completed", ColorCode.WHITE)
 *     .print();
 * }</pre>
 * @since 1.0.0
 */
@Stable(since = "3.2.0")
public final class StyleBuilder {

    private static final AnsiCode RESET = StyleCode.RESET;
    private final StringBuilder textBuilder;

    public StyleBuilder() {
        this.textBuilder = new StringBuilder();
    }

    /**
     * Appends styled text to this builder without resetting the terminal.
     * Styles accumulate across subsequent calls.
     *
     * @param text      the text to style
     * @param ansiCodes the ANSI codes to apply
     * @return this builder
     */
    public StyleBuilder append(String text, AnsiCode... ansiCodes) {
        style(text, textBuilder ,ansiCodes);
        return this;
    }


    /**
     * Appends an ANSI code to this text and then resets the terminal
     *
     * @param text      The text we're applying the ansiCode to
     * @param ansiCodes The ANSI code we're applying to the text
     * @return An instance of this
     *
     */
    public StyleBuilder appendAndReset(String text, AnsiCode... ansiCodes) {
        this.append(text, ansiCodes);
        this.textBuilder.append(RESET.ansiSequence());
        return this;
    }

    /**
     * Returns the accumulated styled string.
     *
     * @return the styled string
     */
    public String toString() {
        return this.textBuilder.toString();
    }

    /**
     * Prints the accumulated styled string to the given stream.
     *
     * @param stream the stream to print to
     * @throws NullPointerException if {@code stream} is null
     */
    public void print(PrintStream stream) {
        Objects.requireNonNull(stream, "Print stream cannot be null");
        stream.println(this);
    }

    /**
     * Prints the accumulated styled string to {@code System.out}.
     */
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
