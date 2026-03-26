package io.github.kusoroadeolu.clique.config;

@FunctionalInterface
public interface BorderSpec {
    String style();

    static BorderSpec of(String value){
        return () -> value;
    }
}
