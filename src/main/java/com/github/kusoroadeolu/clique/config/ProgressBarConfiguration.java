package com.github.kusoroadeolu.clique.config;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.parser.AnsiStringParser;

import java.util.Objects;

public final class ProgressBarConfiguration {
    public static final ProgressBarConfiguration DEFAULT = new ProgressBarConfiguration();
    final int length;
    final String format;
    final char complete;
    final char incomplete;
    final AnsiStringParser parser;

    private ProgressBarConfiguration(){
        this(50, ":progress/:total   :percent% [:bar]  ETA: :remaining", '=', ' ', Clique.parser(ParserConfiguration.immutableBuilder().enableAutoCloseTags().build()));
    }

    private ProgressBarConfiguration(
            int length,
            String format,
            char complete,
            char incomplete,
            AnsiStringParser parser
    ) {
        this.length = length;
        this.format = format;
        this.complete = complete;
        this.incomplete = incomplete;
        this.parser = parser;
    }

    private ProgressBarConfiguration(
            ProgressBarBuilder progressBarBuilder
    ) {
        this.length = Objects.requireNonNullElse(progressBarBuilder.length, DEFAULT.length);
        this.format = Objects.requireNonNullElse(progressBarBuilder.format, DEFAULT.format);
        this.complete = Objects.requireNonNullElse(progressBarBuilder.complete, DEFAULT.complete);
        this.incomplete = Objects.requireNonNullElse(progressBarBuilder.incomplete, DEFAULT.incomplete);
        this.parser = progressBarBuilder.parser;
    }

    public static ProgressBarBuilder immutableBuilder() {
        return new ProgressBarBuilder();
    }


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        ProgressBarConfiguration that = (ProgressBarConfiguration) object;
        return length == that.length && complete == that.complete && incomplete == that.incomplete && Objects.equals(format, that.format);
    }

    public int hashCode() {
        return Objects.hash(length, format, complete, incomplete);
    }

    public String toString() {
        return "ProgressBarConfiguration[" +
                "length=" + length +
                ", format='" + format + '\'' +
                ", complete=" + complete +
                ", incomplete=" + incomplete +
                ']';
    }

    public int getLength() {
        return length;
    }

    public String getFormat() {
        return format;
    }

    public char getComplete() {
        return complete;
    }

    public char getIncomplete() {
        return incomplete;
    }

    public static final class ProgressBarBuilder {
        Integer length = null;
        String format = null;
        Character complete = null;
        Character incomplete = null;
        AnsiStringParser parser = null;

        private ProgressBarBuilder() {}

        private ProgressBarBuilder(ProgressBarConfiguration progressBarConfiguration) {
            length = progressBarConfiguration.length;
            format = progressBarConfiguration.format;
            complete = progressBarConfiguration.complete;
            incomplete = progressBarConfiguration.incomplete;
            parser = progressBarConfiguration.parser;
        }

        public ProgressBarBuilder length(int length) {
            this.length = length;
            return this;
        }

        public ProgressBarBuilder parser(AnsiStringParser parser) {
            this.parser = parser;
            return this;
        }

        public ProgressBarBuilder format(String format) {
            this.format = format;
            return this;
        }


        public ProgressBarBuilder complete(char complete) {
            this.complete = complete;
            return this;
        }


        public ProgressBarBuilder incomplete(char incomplete) {
            this.incomplete = incomplete;
            return this;
        }

        public ProgressBarConfiguration build() {
            return new ProgressBarConfiguration(this);
        }
    }
}