package io.github.kusoroadeolu.clique.core.display;

import java.io.PrintStream;
import java.util.Objects;

public interface Generated extends Component{
    default void render(){
        this.render(System.out);
    }
    default void render(PrintStream stream){
        Objects.requireNonNull(stream, "Print stream cannot be null");
        stream.println(get());
    }
}
