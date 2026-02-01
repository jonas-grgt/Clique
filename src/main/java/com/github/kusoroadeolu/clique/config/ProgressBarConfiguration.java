package com.github.kusoroadeolu.clique.config;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.parser.AnsiStringParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class ProgressBarConfiguration {
    private final int length;
    private final char complete;
    private final char incomplete;
    private final String format;
    private final AnsiStringParser parser;
    private final List<ProgressBarStyle> styles;

    public static final ProgressBarConfiguration DEFAULT = new ProgressBarConfiguration();

    private ProgressBarConfiguration() {
        this.length = 40;
        this.complete = '█';
        this.incomplete = '░';
        this.format = ":bar :percent% [:elapsed/:remaining]";
        this.parser = Clique.parser();
        this.styles = List.of();
    }

    private ProgressBarConfiguration(ProgressBarConfigurationBuilder builder) {
        this.length = builder.length;
        this.complete = builder.complete;
        this.incomplete = builder.incomplete;
        this.format = builder.format;
        this.parser = builder.parser;
        this.styles = Collections.unmodifiableList(builder.styles);
    }

    public static ProgressBarConfigurationBuilder immutableBuilder() {
        return new ProgressBarConfigurationBuilder();
    }

    // Get the format based on current percent
    public String getFormatForPercent(int percent) {
        return styles.stream()
                .filter(style -> style.matches(percent))
                .findFirst()
                .map(ProgressBarStyle::format)
                .orElse(format);  // Fall back to default format
    }

    public AnsiStringParser parser() {
        return parser;
    }

    public int getLength() {
        return length;
    }

    public char getComplete() {
        return complete;
    }

    public char getIncomplete() {
        return incomplete;
    }

    public String getFormat() {
        return format;
    }

    public static class ProgressBarConfigurationBuilder {
        private int length = 40;
        private char complete = '█';
        private char incomplete = '░';
        private String format = ":bar :percent% [:elapsed/:remaining]";
        private AnsiStringParser parser = Clique.parser();
        private List<ProgressBarStyle> styles = new ArrayList<>();

        public ProgressBarConfigurationBuilder length(int length) {
            this.length = length;
            return this;
        }

        public ProgressBarConfigurationBuilder complete(char complete) {
            this.complete = complete;
            return this;
        }

        public ProgressBarConfigurationBuilder incomplete(char incomplete) {
            this.incomplete = incomplete;
            return this;
        }

        public ProgressBarConfigurationBuilder format(String format) {
            this.format = format;
            return this;
        }

        public ProgressBarConfigurationBuilder parser(AnsiStringParser parser) {
            this.parser = parser;
            return this;
        }

        public ProgressBarConfigurationBuilder styleWhen(Predicate<Integer> condition, String format) {
            this.styles.add(new ProgressBarStyle(condition, format));
            return this;
        }

        public ProgressBarConfigurationBuilder styleRange(int min, int max, String format) {
            return this.styleWhen(p -> p >= min && p < max, format);
        }

        public ProgressBarConfigurationBuilder styles(Collection<ProgressBarStyle> styles) {
            this.styles = new ArrayList<>(styles);
            return this;
        }


        public ProgressBarConfiguration build() {
            return new ProgressBarConfiguration(this);
        }
    }
}