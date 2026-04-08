package io.github.kusoroadeolu.clique.configuration;

import io.github.kusoroadeolu.clique.internal.documentation.Stable;
import io.github.kusoroadeolu.clique.internal.markup.ParserUtils;
import io.github.kusoroadeolu.clique.parser.MarkupParser;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.Arrays;
import java.util.Objects;

/**
 * @since 3.1.0
 * */
@Stable(since = "3.2.0")
public class TreeConfiguration {
    private final MarkupParser parser;
    private final AnsiCode[] connectorColor;
    public static final TreeConfiguration DEFAULT = new TreeConfiguration();

    private TreeConfiguration() {
        this(new TreeConfigurationBuilder());
    }

    private TreeConfiguration(TreeConfigurationBuilder builder) {
        this.parser = builder.parser;
        this.connectorColor = builder.connectorColor;
    }

    public static TreeConfigurationBuilder builder() {
        return new TreeConfigurationBuilder();
    }

    public MarkupParser getParser() {
        return parser;
    }

    public AnsiCode[] getConnectorColor() {
        return connectorColor;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        TreeConfiguration that = (TreeConfiguration) object;
        return Objects.equals(parser, that.parser) && Arrays.equals(connectorColor, that.connectorColor);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(parser);
        result = 31 * result + Objects.hashCode(connectorColor);
        return result;
    }

    @Override
    public String toString() {
        return "TreeConfiguration[" +
                "parser=" + parser +
                ", guideStyle='" + Arrays.toString(connectorColor) + '\'' +
                ']';
    }

    public static class TreeConfigurationBuilder {
        private AnsiCode[] connectorColor = {};
        private MarkupParser parser = MarkupParser.DEFAULT;


        public TreeConfigurationBuilder parser(MarkupParser parser) {
            this.parser = parser;
            return this;
        }

        public TreeConfigurationBuilder connectorColor(AnsiCode... connectorColor){
            Objects.requireNonNull(connectorColor, "Connector color cannot be null");
            this.connectorColor = connectorColor;
            return this;
        }

        public TreeConfigurationBuilder connectorColor(String connectorColor){
            return connectorColor(ParserUtils.getAnsiCodes(connectorColor, parser));
        }

        public TreeConfiguration build() {
            return new TreeConfiguration(this);
        }
    }
}
