package com.github.kusoroadeolu.clique.core.display;

import java.io.PrintStream;

public interface Generated extends Component{
    default void render(){
        this.render(System.out);
    }
    default void render(PrintStream stream){
        stream.println(get());
    }
}
