package com.github.kusoroadeolu.clique.style;

import com.github.kusoroadeolu.clique.ansi.AnsiCode;
import com.github.kusoroadeolu.clique.ansi.StyleCode;

import java.io.PrintStream;

import static com.github.kusoroadeolu.clique.core.utils.AnsiDetector.ansiEnabled;
import static com.github.kusoroadeolu.clique.core.utils.StringUtils.clearStringBuilder;

public non-sealed class DefaultStyleBuilder implements StyleBuilder {

    private final StringBuilder textBuilder;
    private final static AnsiCode RESET = StyleCode.RESET;

    public DefaultStyleBuilder(){
        this.textBuilder = new StringBuilder();
    }

    /**
     * Applies multiple ANSI codes to this text
     * @param text The text we're applying the ansiCode to
     * @param ansiCodes The ANSI code we're applying to the text
     * @return The styled text
     * */
    @Override
    public String format(String text, AnsiCode... ansiCodes) {
        return this.style(text, ansiCodes)
                .toString();
    }


    /**
     * Applies an ANSI Codes to this text and resets the terminal style
     * @param text The text we're applying the ansiCode to
     * @param ansiCodes The ANSI code we're applying to the text
     * @return The styled text
     * */
    @Override
    public String formatReset(String text, AnsiCode... ansiCodes) {
        return this.format(text, ansiCodes) + RESET;
    }



    /**
     * Appends multiple ANSI Codes to this text
     * @param text The text we're appending to the style builder
     * @param ansiCodes The ansiCodes we're applying to the text
     * @return The instance of this class
     * */
    @Override
    public StyleBuilder stack(String text, AnsiCode... ansiCodes) {
        this.textBuilder.append(this.style(text, ansiCodes));
        return this;
    }

    /**
     * Appends an ANSI code to this text and then resets the terminal
     * @param text The text we're applying the ansiCode to
     * @param ansiCodes The ANSI code we're applying to the text
     * @return The styled text
     * */
    @Override
    public StyleBuilder append(String text, AnsiCode... ansiCodes) {
        this.stack(text, ansiCodes);
        this.textBuilder.append(RESET);
        return this;
    }

    /**
     * @return the content of the string builder
     * */
    public String get(){
        return this.textBuilder.toString();
    }

    /**
     * Flushes the content of the string builder to the terminal and clears the string builder
     * */
    @Override
    public void print(PrintStream stream) {
        this.textBuilder.append(RESET); //Reset all styles
        stream.println(this.textBuilder);
    }

    //A helper method to style text with the given codes
    private StringBuilder style(String text, AnsiCode... ansiCodes){
        final StringBuilder sb = new StringBuilder();

        //Check if ansi is enabled
        if(ansiCodes == null || !ansiEnabled()){
            return sb.append(text);
        }

        for (AnsiCode code : ansiCodes){
            if (code == null) continue;
            sb.append(code);
        }

        return sb.append(text);
    }

    public void flush() {
        clearStringBuilder(textBuilder);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        DefaultStyleBuilder that = (DefaultStyleBuilder) object;
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
