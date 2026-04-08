package io.github.kusoroadeolu.clique.config;


import io.github.kusoroadeolu.clique.core.documentation.Stable;
import io.github.kusoroadeolu.clique.core.parser.ParserUtils;
import io.github.kusoroadeolu.clique.indent.Flag;
import io.github.kusoroadeolu.clique.parser.MarkupParser;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.Arrays;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.Constants.BLANK;

/**
 * @since 1.1.0
 * */
@Stable(since = "3.2.0")
public class IndenterConfiguration {
    public static final IndenterConfiguration DEFAULT = new IndenterConfiguration();

    private final MarkupParser parser;
    private final int indentLevel;
    private final String defaultFlag;
    private final AnsiCode[] flagColor;


    private IndenterConfiguration() {
        this(new IndenterConfigurationBuilder());
    }

    private IndenterConfiguration(IndenterConfigurationBuilder builder) {
        this.indentLevel = builder.indentLevel;
        this.parser = builder.parser;
        this.flagColor = builder.flagColor;
        this.defaultFlag = builder.defaultFlag;
    }

    public static IndenterConfigurationBuilder builder() {
        return new IndenterConfigurationBuilder();
    }

    @Override
    public String toString() {
        return "IndenterConfiguration[" +
                "parser=" + parser +
                ", indentLevel=" + indentLevel +
                ", defaultFlag='" + defaultFlag + '\'' +
                ", flagColor=" + Arrays.toString(flagColor) +
                ']';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        IndenterConfiguration that = (IndenterConfiguration) object;
        return indentLevel == that.indentLevel && parser.equals(that.parser) && defaultFlag.equals(that.defaultFlag) && Arrays.equals(flagColor, that.flagColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parser, indentLevel, defaultFlag, Arrays.hashCode(flagColor));
    }

    public MarkupParser getParser() {
        return parser;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public int getIndentLevel() {
        return indentLevel;
    }

    public AnsiCode[] getFlagColor() {
        return flagColor;
    }

    public static class IndenterConfigurationBuilder {
        private int indentLevel = 1;
        private MarkupParser parser = new MarkupParser(ParserConfiguration.builder().enableAutoReset().build());
        private String defaultFlag = BLANK;
        private AnsiCode[] flagColor = {};

        public IndenterConfigurationBuilder indentLevel(int indentLevel) {
            if (indentLevel < 1) throw new IllegalArgumentException("Indent level cannot be less than 1");
            this.indentLevel = indentLevel;
            return this;
        }

        public IndenterConfigurationBuilder parser(MarkupParser parser) {
            this.parser = parser;
            return this;
        }

        public IndenterConfigurationBuilder defaultFlag(String flag) {
            Objects.requireNonNull(defaultFlag, "Default flag cannot be null");
            this.defaultFlag = flag;
            return this;
        }

        public IndenterConfigurationBuilder flagColor(String flagColor) {
            Objects.requireNonNull(flagColor, "Default flag color cannot be null");
            this.flagColor = ParserUtils.getAnsiCodes(flagColor, parser);
            return this;
        }


        public IndenterConfigurationBuilder flagColor(AnsiCode... flagColor) {
            Objects.requireNonNull(flagColor, "Default flag color cannot be null");
            this.flagColor = flagColor;
            return this;
        }


        public IndenterConfigurationBuilder defaultFlag(Flag flag) {
            return defaultFlag(flag.flag());
        }

        public IndenterConfiguration build() {
            return new IndenterConfiguration(this);
        }
    }
}