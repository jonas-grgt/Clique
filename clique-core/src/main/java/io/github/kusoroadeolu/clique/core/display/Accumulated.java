package io.github.kusoroadeolu.clique.core.display;

import java.io.PrintStream;
import java.util.Objects;

public interface Accumulated extends Component {
    default void print() {
        this.print(System.out);
    }

    default void print(PrintStream stream) {
        Objects.requireNonNull(stream, "Print stream cannot be null");
        stream.println(get());
    }

    void flush();
}
