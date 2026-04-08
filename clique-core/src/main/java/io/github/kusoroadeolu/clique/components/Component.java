package io.github.kusoroadeolu.clique.components;

import io.github.kusoroadeolu.clique.internal.documentation.Stable;

import java.io.PrintStream;

import static java.util.Objects.requireNonNull;

/**
 * @since 2.0.0
 * */
@Stable(since = "3.2.0")
public interface Component {
    /**
     * @return the content of the component
     *
     */
    String get();

    default void render() {
        this.render(System.out);
    }

    default void render(PrintStream stream) {
        requireNonNull(stream, "Print stream cannot be null");
        stream.println(get());
    }
}
