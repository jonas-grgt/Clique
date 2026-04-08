package io.github.kusoroadeolu.clique.parser;

import io.github.kusoroadeolu.clique.ansi.CompositeColor;
import io.github.kusoroadeolu.clique.core.documentation.Experimental;
import io.github.kusoroadeolu.clique.spi.AnsiCode;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Experimental(since = "4.0.0")
public final class StyleContext {
    private final Map<String, AnsiCode> localStyles;

    public static StyleContextBuilder builder(){
        return new StyleContextBuilder();
    }

    public static StyleContext from(Map<String, AnsiCode> codes){
        return new StyleContextBuilder().add(codes).build();
    }

    public AnsiCode get(String s){
        return localStyles.get(s);
    }


    StyleContext(StyleContextBuilder builder) {
        this.localStyles = builder.localStyles;
    }

    public static class StyleContextBuilder {
        private final Map<String, AnsiCode> localStyles;

        private StyleContextBuilder() {
            this.localStyles = new HashMap<>();
        }

        public StyleContextBuilder add(String markup, AnsiCode code){
            Objects.requireNonNull(markup, "Markup cannot be null");
            Objects.requireNonNull(code, "Ansi code cannot be null");
            localStyles.put(markup, code);
            return this;
        }

        public StyleContextBuilder add(String markup, AnsiCode... code){
            Objects.requireNonNull(markup, "Markup cannot be null");
            Objects.requireNonNull(code, "Ansi codes cannot be null");
            localStyles.put(markup, new CompositeColor(code));
            return this;
        }

        public StyleContextBuilder add(String markup, Collection<AnsiCode> code){
            Objects.requireNonNull(markup, "Markup cannot be null");
            Objects.requireNonNull(code, "Ansi codes cannot be null");
            localStyles.put(markup, new CompositeColor(code));
            return this;
        }

        public StyleContextBuilder add(Map<String, AnsiCode> codes){
            Objects.requireNonNull(codes, "Map cannot be null");
            localStyles.putAll(codes);
            return this;
        }

        public StyleContextBuilder add(StyleContext context){
            Objects.requireNonNull(context, "Style context cannot be null");
            this.localStyles.putAll(context.localStyles);
            return this;
        }


        public StyleContext build(){
            return new StyleContext(this);
        }
    }


}
