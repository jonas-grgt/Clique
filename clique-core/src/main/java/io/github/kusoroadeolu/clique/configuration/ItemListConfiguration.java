package io.github.kusoroadeolu.clique.configuration;


import io.github.kusoroadeolu.clique.internal.documentation.Stable;
import io.github.kusoroadeolu.clique.parser.MarkupParser;

import java.util.Objects;

/**
 * @since 1.1.0
 * */
@Stable(since = "3.2.0")
public class ItemListConfiguration {
    public static final ItemListConfiguration DEFAULT = new ItemListConfiguration();

    private final MarkupParser parser;
    private final int indentSize;
    private final int symbolSpacing;


    private ItemListConfiguration() {
        this(new ItemListConfigurationBuilder());
    }

    private ItemListConfiguration(ItemListConfigurationBuilder builder) {
        this.indentSize = builder.indentSize;
        this.parser = builder.parser;
        this.symbolSpacing = builder.symbolSpacing;
    }

    public static ItemListConfigurationBuilder builder() {
        return new ItemListConfigurationBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ItemListConfiguration that = (ItemListConfiguration) o;
        return indentSize == that.indentSize && symbolSpacing == that.symbolSpacing && Objects.equals(parser, that.parser);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(parser);
        result = 31 * result + indentSize;
        result = 31 * result + symbolSpacing;
        return result;
    }

    @Override
    public String toString() {
        return "ItemListConfiguration[" +
                "parser=" + parser +
                ", indentSize=" + indentSize +
                ", symbolSpacing=" + symbolSpacing +
                ']';
    }

    public MarkupParser getParser() {
        return parser;
    }

    public int getIndentSize() {
        return indentSize;
    }

    public int getSymbolSpacing() {
        return symbolSpacing;
    }

    public static class ItemListConfigurationBuilder {
        private int indentSize = 2;
        private MarkupParser parser = new MarkupParser();
        private int symbolSpacing = 1;

        public ItemListConfigurationBuilder indentSize(int indentSize) {
            if (indentSize < 1) throw new IllegalArgumentException("Indent size cannot be less than 1");
            this.indentSize = indentSize;
            return this;
        }

        public ItemListConfigurationBuilder parser(MarkupParser parser) {
            this.parser = parser;
            return this;
        }

        public ItemListConfigurationBuilder symbolSpacing(int spacing) {
            if (spacing < 0) throw new IllegalArgumentException("Symbol spacing cannot be negative");
            this.symbolSpacing = spacing;
            return this;
        }


        public ItemListConfiguration build() {
            return new ItemListConfiguration(this);
        }
    }
}