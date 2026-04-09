package io.github.kusoroadeolu.clique.parser;

import io.github.kusoroadeolu.clique.configuration.ParserConfiguration;
import io.github.kusoroadeolu.clique.internal.documentation.Stable;
import io.github.kusoroadeolu.clique.internal.markup.ParseResult;
import io.github.kusoroadeolu.clique.internal.markup.ParseToken;
import io.github.kusoroadeolu.clique.internal.markup.StyleResolver;
import io.github.kusoroadeolu.clique.internal.markup.Tokenizer;

import java.util.List;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.internal.markup.MarkupPostProcessor.postProcess;
import static io.github.kusoroadeolu.clique.internal.utils.StringUtils.stripAnsi;

/**
 * An interface for parsing markup-tagged strings into ANSI-styled output.
 *
 * <p>Transformations follow a strict resolution order:
 * <ol>
 * <li><b>Tokenization:</b> Input is scanned for delimiters defined in {@link ParserConfiguration}.</li>
 * <li><b>Style Resolution:</b> Markup tokens are looked up within the configured style context
 * and resolved to ANSI sequences.</li>
 * <li><b>Post-processing:</b> The resulting string is passed through {@code MarkupPostProcessor}
 * for final normalization.</li>
 * </ol>
 *
 * <p><b>Thread Safety:</b> Internal implementations are immutable and thread-safe.
 * No internal state is modified during parsing operations.
 *
 * @since 4.0.0
 */
@Stable(since = "4.0.0")
public sealed interface MarkupParser permits MarkupParser.MarkupParserInstance, MarkupParser.NoneMarkupParser {

    /**
     * A parser instance using {@link ParserConfiguration#DEFAULT}.
     */
    MarkupParser DEFAULT = new MarkupParserInstance();

    /**
     * A sentinel parser instance used to indicate that markup should remain unparsed.
     *
     * <p>When this reference is used, markup tags are treated as literal text and
     * are not consumed. Operations on this instance generally throw
     * {@link UnsupportedOperationException}.
     */
    MarkupParser NONE = NoneMarkupParser.NONE;

    /**
     * Parses the specified string into an ANSI-styled representation.
     *
     * @param string the markup-tagged string to parse; may be {@code null}
     * @return the styled string, or the original string if {@code null} or blank
     * @throws UnsupportedOperationException if called on {@link #NONE}
     */
    String parse(String string);

    /**
     * Parses the string representation of the specified object.
     *
     * @param object the object to parse; must not be {@code null}
     * @return the styled string
     * @throws NullPointerException if {@code object} is {@code null}
     * @throws UnsupportedOperationException if called on {@link #NONE}
     */
    String parse(Object object);

    /**
     * Parses the specified string and writes the result to {@code System.out}.
     *
     * @param string the markup-tagged string to print; may be {@code null}
     * @throws UnsupportedOperationException if called on {@link #NONE}
     */
    void print(String string);

    /**
     * Parses the string representation of the specified object and writes
     * the result to {@code System.out}.
     *
     * @param object the object to print; must not be {@code null}
     * @throws NullPointerException if {@code object} is {@code null}
     * @throws UnsupportedOperationException if called on {@link #NONE}
     */
    void print(Object object);

    /**
     * Returns the plain-text content of a string by removing markup tags
     * and ANSI escape sequences.
     *
     * @param tokenedString the string to process; may be {@code null}
     * @return the raw text content without styling or tags
     * @throws UnsupportedOperationException if called on {@link #NONE}
     */
    String getOriginalString(String tokenedString);

    /**
     * Internal sentinel implementation that disables parsing.
     */
    enum NoneMarkupParser implements MarkupParser {
        NONE;

        @Override
        public String parse(String string) {
            throw new UnsupportedOperationException("NONE parser does not support parsing");
        }

        @Override
        public String parse(Object object) {
            throw new UnsupportedOperationException("NONE parser does not support parsing");
        }

        @Override
        public void print(String string) {
            throw new UnsupportedOperationException("NONE parser does not support printing");
        }

        @Override
        public void print(Object object) {
            throw new UnsupportedOperationException("NONE parser does not support printing");
        }

        @Override
        public String getOriginalString(String tokenedString) {
            throw new UnsupportedOperationException("NONE parser does not support string extraction");
        }
    }

    /**
     * Standard implementation of {@link MarkupParser}.
     * * <p>This implementation is <b>not thread-safe</b>.
     */
    non-sealed class MarkupParserInstance implements MarkupParser {

        private final ParserConfiguration configuration;

        /**
         * Constructs a parser with the specified configuration.
         *
         * @param configuration the configuration to use; must not be {@code null}
         * @throws NullPointerException if {@code configuration} is {@code null}
         */
        public MarkupParserInstance(ParserConfiguration configuration) {
            this.configuration = Objects.requireNonNull(configuration, "Parser configuration cannot be null");
        }

        /**
         * Constructs a parser using {@link ParserConfiguration#DEFAULT}.
         */
        public MarkupParserInstance() {
            this(ParserConfiguration.DEFAULT);
        }

        /**
         * {@inheritDoc}
         * * <p>Resolution follows the style definitions provided in the
         * {@link ParserConfiguration} associated with this instance.
         */
        @Override
        public String parse(String string) {
            if (string == null || string.isBlank()) return string;
            final ParseResult result = this.getParseResult(string);
            String styled = StyleResolver.resolve(result.tokens(), string, this.configuration.getEnableAutoReset());
            return postProcess(styled);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String parse(Object object) {
            Objects.requireNonNull(object, "Object cannot be null");
            return parse(object.toString());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void print(String string) {
            System.out.println(parse(string));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void print(Object object) {
            Objects.requireNonNull(object, "Object cannot be null");
            print(object.toString());
        }

        /**
         * {@inheritDoc}
         * * <p>Scoping: This method identifies ranges defined by markup tokens and
         * excludes them from the output string. It subsequently strips any
         * hardcoded ANSI sequences.
         * * <p>Example: {@code "[b]Hi[/b]"} results in {@code "Hi"}.
         */
        @Override
        public String getOriginalString(String tokenedString) {
            if (tokenedString == null || tokenedString.isBlank()) return tokenedString;
            ParseResult result = this.getParseResult(tokenedString);

            if (!result.isPresent()) {
                return stripAnsi(postProcess(tokenedString));
            }

            final List<ParseToken> tokens = result.tokens();
            final StringBuilder sb = new StringBuilder(tokenedString.length());
            int cursor = 0;

            for (ParseToken token : tokens) {
                sb.append(tokenedString, cursor, token.start());
                cursor = token.end() + 1;
            }

            sb.append(tokenedString, cursor, tokenedString.length());

            return stripAnsi(postProcess(sb.toString()));
        }

        /**
         * Internal helper to generate a {@link ParseResult} based on current configuration.
         * * @param input the string to tokenize
         * @return the result containing tokens and metadata
         */
        ParseResult getParseResult(String input) {
            return Tokenizer.tokenize(
                    input,
                    configuration.getDelimiter(),
                    configuration.getEnableStrictParsing(),
                    configuration.getStyleContext()
            );
        }
    }
}