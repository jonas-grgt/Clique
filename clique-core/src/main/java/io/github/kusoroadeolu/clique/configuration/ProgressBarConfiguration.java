package io.github.kusoroadeolu.clique.configuration;

import io.github.kusoroadeolu.clique.internal.documentation.InternalApi;
import io.github.kusoroadeolu.clique.internal.documentation.Stable;
import io.github.kusoroadeolu.clique.parser.MarkupParser;

import java.util.*;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * @since 3.0.0
 * */
@Stable(since = "3.2.0")
public class ProgressBarConfiguration {
    public static final ProgressBarConfiguration DEFAULT = ProgressBarPreset.BLOCKS.getConfiguration();

    private final int length;
    private final char complete;
    private final char incomplete;
    private final String format;
    private final MarkupParser parser;
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

    @Stable(since = "3.2.1")
    public static ProgressBarConfigurationBuilder fromPreset(ProgressBarPreset preset) {
        Objects.requireNonNull(preset, "Preset cannot be null");
        var config = preset.getConfiguration();
        return ProgressBarConfiguration
                .builder()
                .length(config.length)
                .complete(config.complete)
                .incomplete(config.incomplete)
                .format(config.format);
    }

    @InternalApi(since = "3.2.1")
    public static ProgressBarConfiguration fromEasing(EasingConfiguration easing) {
        Objects.requireNonNull(easing, "Easing config cannot be null");
        return ProgressBarConfiguration
                .builder()
                .easing(easing)
                .build();
    }

    // Get the format based on current percent
    @InternalApi(since = "3.2.1")
    public String getFormatForPercent(int percent) {
        return styles.stream()
                .filter(style -> style.matches(percent))
                .findFirst()
                .map(ProgressBarPredicate::format)
                .orElse(format);  // Fall back to default format
    }

    public MarkupParser parser() {
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
        private MarkupParser parser = MarkupParser.DEFAULT;
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

        public ProgressBarConfigurationBuilder parser(MarkupParser parser) {
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
            return this.styleWhen(p -> p >= min && p <= max, format);
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