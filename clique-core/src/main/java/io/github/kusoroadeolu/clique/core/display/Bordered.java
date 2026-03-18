package io.github.kusoroadeolu.clique.core.display;

import java.io.PrintStream;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public interface Bordered extends Component {
    default void render() {
        this.render(System.out);
    }

    default void render(PrintStream stream) {
        requireNonNull(stream, "Print stream cannot be null");
        stream.println(get());
    }
}
