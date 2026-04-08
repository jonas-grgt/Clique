package io.github.kusoroadeolu.clique.config;

import io.github.kusoroadeolu.clique.core.documentation.Stable;
import io.github.kusoroadeolu.clique.core.parser.ParserUtils;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.Arrays;
import java.util.Objects;

/**
 * @since 3.1.0
 * */
@Stable(since = "3.2.0")
public class TreeConfiguration {
    private final AnsiStringParser parser;
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

    public AnsiStringParser getParser() {
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
        private AnsiStringParser parser = AnsiStringParser.DEFAULT;


        public TreeConfigurationBuilder parser(AnsiStringParser parser) {
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
