package io.github.kusoroadeolu.clique.config;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;

import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.Constants.EMPTY;

public class TreeConfiguration {
    private final AnsiStringParser parser;
    private final String guideStyle;
    public static final TreeConfiguration DEFAULT = new TreeConfiguration();

    private TreeConfiguration() {
        this(new TreeConfigurationBuilder());
    }

    private TreeConfiguration(TreeConfigurationBuilder builder) {
        this.parser = builder.parser;
        this.guideStyle = builder.guideStyle;
    }


    public static TreeConfigurationBuilder immutableBuilder() {
        return new TreeConfigurationBuilder();
    }

    public AnsiStringParser getParser() {
        return parser;
    }

    public String getGuideStyle() {
        return guideStyle;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        TreeConfiguration that = (TreeConfiguration) object;
        return Objects.equals(parser, that.parser) && Objects.equals(guideStyle, that.guideStyle);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(parser);
        result = 31 * result + Objects.hashCode(guideStyle);
        return result;
    }

    @Override
    public String toString() {
        return "TreeConfiguration[" +
                "parser=" + parser +
                ", guideStyle='" + guideStyle + '\'' +
                ']';
    }

    public static class TreeConfigurationBuilder {
        private String guideStyle = EMPTY; //A reset flag, because we'll wrap this in markup tags
        private AnsiStringParser parser = Clique.parser().DEFAULT;


        public TreeConfigurationBuilder parser(AnsiStringParser parser) {
            this.parser = parser;
            return this;
        }

        public TreeConfigurationBuilder guideStyle(String guideStyle){
            Objects.requireNonNull(guideStyle, "Guide style cannot be null");
            if (!guideStyle.isBlank()) this.guideStyle = "[%s]".formatted(guideStyle);
            return this;
        }


        public TreeConfiguration build() {
            return new TreeConfiguration(this);
        }
    }
}
