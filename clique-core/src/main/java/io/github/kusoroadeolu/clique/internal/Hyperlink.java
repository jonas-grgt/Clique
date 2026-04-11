package io.github.kusoroadeolu.clique.internal;

public class Hyperlink {
    private final String url;
    private static final String HYPER_WRAPPER = "\033]8;;%s\033\\%s\033]8;;\033\\";

    public Hyperlink(String url){
        this.url = url;
    }

    public String apply(String text) {
        return HYPER_WRAPPER.formatted(url, text);
    }
}
