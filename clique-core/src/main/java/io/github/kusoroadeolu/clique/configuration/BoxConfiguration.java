package io.github.kusoroadeolu.clique.configuration;

import io.github.kusoroadeolu.clique.internal.documentation.Stable;
import io.github.kusoroadeolu.clique.internal.utils.ParserUtils;
import io.github.kusoroadeolu.clique.parser.MarkupParser;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.Arrays;
import java.util.Objects;

/**
 * Immutable configuration for {@link io.github.kusoroadeolu.clique.components.Box}
 * and {@link io.github.kusoroadeolu.clique.components.Frame} components.
 *
 * <p>Instances are created via {@link #builder()}. A shared default instance is
 * available via {@link #DEFAULT}, which uses center alignment, a padding of {@code 2},
 * no border color, and the default markup parser.</p>
 *
 * <pre>{@code
 * BoxConfiguration config = BoxConfiguration.builder()
 *     .padding(1)
 *     .textAlign(TextAlign.LEFT)
 *     .borderColor(ColorCode.CYAN)
 *     .build();
 * }</pre>
 *
 * @since 1.1.0
 */
@Stable(since = "3.2.0")
public final class BoxConfiguration {

    /**
     * A shared default configuration with center alignment, padding of {@code 2},
     * no border color, and the default markup parser.
     */
    public static final BoxConfiguration DEFAULT = new BoxConfiguration();

    private final TextAlign textAlign;
    private final MarkupParser parser;
    private final AnsiCode[] borderColor;
    private final int padding;

    private BoxConfiguration() {
        this(new BoxConfigurationBuilder());
    }

    private BoxConfiguration(BoxConfigurationBuilder builder) {
        this.textAlign = builder.textAlign;
        this.parser = builder.parser;
        this.borderColor = builder.borderColor;
        this.padding = builder.padding;
    }

    /**
     * Returns a new {@link BoxConfigurationBuilder} with default values.
     *
     * @return a new builder instance
     */
    public static BoxConfigurationBuilder builder() {
        return new BoxConfigurationBuilder();
    }

    /**
     * Returns the horizontal padding applied to each side of the content.
     *
     * @return the padding value; always non-negative
     */
    public int getPadding() {
        return this.padding;
    }

    /**
     * Returns the ANSI codes applied to the box border, or an empty array if none was set.
     *
     * @return the border color codes; never {@code null}
     */
    public AnsiCode[] getBorderColor() {
        return this.borderColor.clone();
    }

    /**
     * Returns the text alignment applied to content within the box.
     *
     * @return the text alignment
     */
    public TextAlign getTextAlign() {
        return this.textAlign;
    }

    /**
     * Returns the markup parser used to resolve styled markup in content.
     *
     * @return the markup parser; never {@code null}
     */
    public MarkupParser getParser() {
        return this.parser;
    }

    @Override
    public String toString() {
        return "BoxConfiguration[" +
                "textAlign=" + textAlign +
                ", parser=" + parser +
                ", borderColor=" + Arrays.toString(borderColor) +
                ", padding=" + padding +
                ']';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        BoxConfiguration that = (BoxConfiguration) object;
        return textAlign == that.textAlign && parser.equals(that.parser) &&  padding == that.padding && Arrays.equals(borderColor, that.borderColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(padding, textAlign, parser, Arrays.hashCode(borderColor));
    }

    /**
     * Builder for {@link BoxConfiguration}.
     *
     * <p>Default values: {@link TextAlign#CENTER}, padding {@code 2}, no border color,
     * and {@link MarkupParser#DEFAULT}.</p>
     */
    public static class BoxConfigurationBuilder {
        private TextAlign textAlign = TextAlign.CENTER;
        private MarkupParser parser = MarkupParser.DEFAULT;
        private AnsiCode[] borderColor = {};
        private int padding = 2;

        /**
         * Sets the horizontal padding applied to each side of the content.
         *
         * @param padding the padding value; must not be negative
         * @return this builder
         * @throws IllegalArgumentException if {@code padding} is negative
         */
        public BoxConfigurationBuilder padding(int padding) {
            if (padding < 0) throw new IllegalArgumentException("Padding cannot be negative");
            this.padding = padding;
            return this;
        }

        /**
         * Sets the border color from a named or markup-encoded color string,
         * resolved via the currently configured parser e.g "red, bold". Note that the markup brackets are not included here
         *
         * @param borderColor the color name or markup string; must not be {@code null}
         * @return this builder
         */
        public BoxConfigurationBuilder borderColor(String borderColor) {
            return borderColor(ParserUtils.getAnsiCodes(borderColor, parser));
        }

        /**
         * Sets the border color from one or more {@link AnsiCode} values.
         *
         * @param borderColor the ANSI codes to apply to the border; must not be {@code null}
         * @return this builder
         * @throws NullPointerException if {@code borderColor} is {@code null}
         */
        public BoxConfigurationBuilder borderColor(AnsiCode... borderColor) {
            Objects.requireNonNull(borderColor, "Border color cannot be null");
            this.borderColor = borderColor;
            return this;
        }

        /**
         * Sets the text alignment for content within the box.
         *
         * @param textAlign the alignment to apply; must not be {@code null}
         * @return this builder
         * @throws NullPointerException if {@code textAlign} is {@code null}
         */
        public BoxConfigurationBuilder textAlign(TextAlign textAlign) {
            Objects.requireNonNull(textAlign, "Text align cannot be null");
            this.textAlign = textAlign;
            return this;
        }

        /**
         * Sets the markup parser used to resolve styled markup in content.
         *
         * @param parser the parser to use; must not be {@code null}. If {@link MarkupParser#NONE} is passed, valid markups tags will not get consumed
         * @return this builder
         */
        public BoxConfigurationBuilder parser(MarkupParser parser) {
            Objects.requireNonNull(parser, "Parser cannot be null");
            this.parser = parser;
            return this;
        }

        /**
         * Builds and returns a new {@link BoxConfiguration} from the current builder state.
         *
         * @return a new {@code BoxConfiguration} instance
         */
        public BoxConfiguration build() {
            return new BoxConfiguration(this);
        }
    }
}