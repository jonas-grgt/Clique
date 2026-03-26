package io.github.kusoroadeolu.clique.config;

@FunctionalInterface
public interface BorderSpec {
    String value();

    static BorderSpec of(String value){
        return () -> value;
    }
}
