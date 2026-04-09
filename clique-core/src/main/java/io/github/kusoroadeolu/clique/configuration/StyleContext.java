package io.github.kusoroadeolu.clique.configuration;

import io.github.kusoroadeolu.clique.internal.CompositeColor;
import io.github.kusoroadeolu.clique.internal.documentation.Experimental;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A container for custom markup-to-ANSI mappings used during the parsing process.
 *
 * <p>A {@code StyleContext} allows users to define custom tags (e.g., "error]")
 * that map to specific {@link AnsiCode} sequences. These mappings are checked
 * during the style resolution phase of parsing.
 *
 * <p><b>Resolution Order:</b> Local styles defined in this context take
 * precedence over global or default styles during parsing. When multiple
 * {@code AnsiCode} values are mapped to a single tag via the builder, they
 * are resolved as a composite style.
 *
 * <p><b>Thread Safety:</b> This class is immutable and thread-safe. The
 * internal map is fixed upon construction.
 *
 * @since 4.0.0
 */
@Experimental(since = "4.0.0")
public final class StyleContext {
    private final Map<String, AnsiCode> localStyles;

    /**
     * An empty style context containing no local mappings.
     */
    public static final StyleContext NONE = new StyleContext();

    /**
     * Creates a new builder for constructing a {@code StyleContext}.
     *
     * @return a new builder instance
     */
    public static StyleContextBuilder builder(){
        return new StyleContextBuilder();
    }

    /**
     * Creates a {@code StyleContext} from the provided map of mappings.
     *
     * @param codes a map where keys are markup tags and values are ANSI codes;
     * must not be {@code null}
     * @return a new style context
     * @throws NullPointerException if {@code codes} is {@code null}
     */
    public static StyleContext from(Map<String, AnsiCode> codes){
        return new StyleContextBuilder().add(codes).build();
    }

    /**
     * Retrieves the {@link AnsiCode} associated with the specified markup tag.
     *
     * @param s the markup tag to look up
     * @return the associated ANSI code, or {@code null} if no mapping exists
     */
    public AnsiCode get(String s){
        return localStyles.get(s);
    }


    StyleContext(StyleContextBuilder builder) {
        this.localStyles = new HashMap<>(builder.localStyles);
    }

    StyleContext() {
        this.localStyles = new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        StyleContext that = (StyleContext) o;
        return Objects.equals(localStyles, that.localStyles);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(localStyles);
    }

    @Override
    public String toString() {
        return "StyleContext[" +
                "localStyles=" + localStyles +
                ']';
    }

    /**
     * Builder for {@link StyleContext} instances.
     *
     * <p>Mappings added to the builder will overwrite existing mappings
     * for the same markup tag. This builder is <b>not thread-safe</b>.
     */
    public static class StyleContextBuilder {
        private final Map<String, AnsiCode> localStyles;

        private StyleContextBuilder() {
            this.localStyles = new HashMap<>();
        }

        /**
         * Adds a single mapping to the context.
         *
         * @param markup the tag name (e.g., "myStyle")
         * @param code the ANSI code to associate with the tag
         * @return this builder
         * @throws NullPointerException if either parameter is {@code null}
         */
        public StyleContextBuilder add(String markup, AnsiCode code){
            Objects.requireNonNull(markup, "Markup cannot be null");
            Objects.requireNonNull(code, "Ansi code cannot be null");
            localStyles.put(markup, code);
            return this;
        }

        /**
         * Adds a mapping that resolves to multiple ANSI codes.
         *
         * @param markup the tag name
         * @param code the ANSI codes to associate with the tag
         * @return this builder
         * @throws NullPointerException if either parameter is {@code null}
         */
        public StyleContextBuilder add(String markup, AnsiCode... code){
            Objects.requireNonNull(markup, "Markup cannot be null");
            Objects.requireNonNull(code, "Ansi codes cannot be null");
            localStyles.put(markup, new CompositeColor(code));
            return this;
        }

        /**
         * Adds a mapping that resolves to a collection of ANSI codes.
         *
         * @param markup the tag name
         * @param code the collection of ANSI codes; must not be {@code null}
         * @return this builder
         * @throws NullPointerException if either parameter is {@code null}
         */
        public StyleContextBuilder add(String markup, Collection<AnsiCode> code){
            Objects.requireNonNull(markup, "Markup cannot be null");
            Objects.requireNonNull(code, "Ansi codes cannot be null");
            localStyles.put(markup, new CompositeColor(code));
            return this;
        }

        /**
         * Adds all mappings from the specified map to the builder.
         *
         * @param codes the map of mappings to add
         * @return this builder
         * @throws NullPointerException if {@code codes} is {@code null}
         */
        public StyleContextBuilder add(Map<String, AnsiCode> codes){
            Objects.requireNonNull(codes, "Map cannot be null");
            localStyles.putAll(codes);
            return this;
        }

        /**
         * Merges the mappings from an existing {@code StyleContext} into this builder.
         *
         * @param context the context to merge; must not be {@code null}
         * @return this builder
         * @throws NullPointerException if {@code context} is {@code null}
         */
        public StyleContextBuilder add(StyleContext context){
            Objects.requireNonNull(context, "Style context cannot be null");
            this.localStyles.putAll(context.localStyles);
            return this;
        }

        /**
         * Builds a new {@link StyleContext} instance.
         *
         * @return a new style context
         */
        public StyleContext build(){
            return new StyleContext(this);
        }
    }
}