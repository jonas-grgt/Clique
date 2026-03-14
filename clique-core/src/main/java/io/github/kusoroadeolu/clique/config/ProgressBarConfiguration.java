package io.github.kusoroadeolu.clique.config;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;
import io.github.kusoroadeolu.clique.progressbar.ProgressBarPredicate;
import io.github.kusoroadeolu.clique.progressbar.ProgressBarPreset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class ProgressBarConfiguration {
    public static final ProgressBarConfiguration DEFAULT = ProgressBarPreset.BLOCKS.getConfiguration();

    private final int length;
    private final char complete;
    private final char incomplete;
    private final String format;
    private final AnsiStringParser parser;
    private final List<ProgressBarPredicate> styles;
    private final EasingConfiguration easingConfiguration;


    private ProgressBarConfiguration(ProgressBarConfigurationBuilder builder) {
        this.length = builder.length;
        this.complete = builder.complete;
        this.incomplete = builder.incomplete;
        this.format = builder.format;
        this.parser = builder.parser;
        this.styles = Collections.unmodifiableList(builder.styles);
        this.easingConfiguration = builder.easing;
    }

    public static ProgressBarConfigurationBuilder immutableBuilder() {
        return new ProgressBarConfigurationBuilder();
    }

    // Get the format based on current percent
    public String getFormatForPercent(int percent) {
        return styles.stream()
                .filter(style -> style.matches(percent))
                .findFirst()
                .map(ProgressBarPredicate::format)
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

    public EasingConfiguration getEasing() {
        return easingConfiguration;
    }

    public static class ProgressBarConfigurationBuilder {
        private int length = 40;
        private char complete = '█';
        private char incomplete = '░';
        private String format = ":bar :percent% [:elapsed/:remaining]";
        private AnsiStringParser parser = Clique.parser();
        private List<ProgressBarPredicate> styles = new ArrayList<>();
        private EasingConfiguration easing = EasingConfiguration.DEFAULT;

        public ProgressBarConfigurationBuilder length(int length) {
            if (length < 0) throw new IllegalArgumentException("Length must be positive");
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
            requireNonNull(format, "Format cannot be null");
            this.format = format;
            return this;
        }

        public ProgressBarConfigurationBuilder parser(AnsiStringParser parser) {
            this.parser = parser;
            return this;
        }

        public ProgressBarConfigurationBuilder styleWhen(Predicate<Integer> condition, String format) {
            requireNonNull(format, "Format cannot be null");
            requireNonNull(condition, "Condition cannot be null");
            this.styles.add(new ProgressBarPredicate(condition, format));
            return this;
        }

        public ProgressBarConfigurationBuilder styleRange(int min, int max, String format) {
            requireNonNull(format, "Format cannot be null");
            if (min < 0) throw new IllegalArgumentException("Min must be positive");
            return this.styleWhen(p -> p >= min && p < max, format);
        }

        public ProgressBarConfigurationBuilder styles(Collection<ProgressBarPredicate> styles) {
            requireNonNull(styles, "Styles cannot be null");
            this.styles = new ArrayList<>(styles);
            return this;
        }

        public ProgressBarConfigurationBuilder easing(EasingConfiguration easing) {
            requireNonNull(easing, "Easing configuration cannot be null");
            this.easing = easing;
            return this;
        }

        public ProgressBarConfiguration build() {
            return new ProgressBarConfiguration(this);
        }
    }
}