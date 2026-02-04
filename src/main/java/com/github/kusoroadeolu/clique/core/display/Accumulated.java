package com.github.kusoroadeolu.clique.core.display;

import java.io.PrintStream;

public interface Accumulated extends Component{
    default void print(){
        this.print(System.out);
    }
    default void print(PrintStream stream){
        stream.println(get());
    }
    void flush();
}
