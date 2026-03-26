package io.github.kusoroadeolu.clique.config;

import io.github.kusoroadeolu.clique.parser.AnsiStringParser;
import io.github.kusoroadeolu.clique.progressbar.ProgressBarPredicate;
import io.github.kusoroadeolu.clique.progressbar.ProgressBarPreset;

import java.util.*;
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

    public static ProgressBarConfigurationBuilder builder() {
        return new ProgressBarConfigurationBuilder();
    }

    /**
     * @deprecated As of 3.1.3, use {@link ProgressBarConfiguration#builder()} instead. This will be removed in a future release.
     * */
    @Deprecated(since = "3.1.3", forRemoval = true)
    public static ProgressBarConfigurationBuilder immutableBuilder() {
        return builder();
    }

    public static ProgressBarConfigurationBuilder fromPreset(ProgressBarPreset preset) {
        Objects.requireNonNull(preset, "Preset cannot be null");
        var config = preset.getConfiguration();
        return ProgressBarConfiguration
                .immutableBuilder()
                .length(config.length)
                .complete(config.complete)
                .incomplete(config.incomplete)
                .format(config.format);
    }

    public static ProgressBarConfiguration fromEasing(EasingConfiguration easing) {
        Objects.requireNonNull(easing, "Easing config cannot be null");
        return ProgressBarConfiguration
                .immutableBuilder()
                .easing(easing)
                .build();
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

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        ProgressBarConfiguration that = (ProgressBarConfiguration) object;
        return length == that.length && complete == that.complete && incomplete == that.incomplete && Objects.equals(format, that.format) && Objects.equals(parser, that.parser) && styles.equals(that.styles) && Objects.equals(easingConfiguration, that.easingConfiguration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, complete, incomplete, format, parser, styles, easingConfiguration);
    }

    @Override
    public String toString() {
        return "ProgressBarConfiguration[" +
                "height=" + length +
                ", complete=" + complete +
                ", incomplete=" + incomplete +
                ", format='" + format + '\'' +
                ", parser=" + parser +
                ", styles=" + styles +
                ", easingConfiguration=" + easingConfiguration +
                ']';
    }

    public static class ProgressBarConfigurationBuilder {
        private int length = 40;
        private char complete = '█';
        private char incomplete = '░';
        private String format = ":bar :percent% [:elapsed/:remaining]";
        private AnsiStringParser parser = AnsiStringParser.DEFAULT;
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