package io.github.kusoroadeolu.clique.core.display;

import io.github.kusoroadeolu.clique.core.documentation.Stable;

import java.io.PrintStream;

import static java.util.Objects.requireNonNull;

/**
 * @since 3.1.0
 * */
@Stable(since = "3.2.0")
public interface Bordered extends Component {
    default void render() {
        this.render(System.out);
    }

    default void render(PrintStream stream) {
        requireNonNull(stream, "Print stream cannot be null");
        stream.println(get());
    }
}
