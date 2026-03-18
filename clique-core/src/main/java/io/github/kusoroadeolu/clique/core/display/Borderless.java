package io.github.kusoroadeolu.clique.core.display;

import java.io.PrintStream;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public interface Borderless extends Component {
    default void print() {
        this.print(System.out);
    }

    default void print(PrintStream stream) {
        requireNonNull(stream, "Print stream cannot be null");
        stream.println(get());
    }

    void flush();
}
