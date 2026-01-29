package com.github.kusoroadeolu.clique.core.display;

import java.io.PrintStream;

public interface Printable {
    default void print(){
        this.print(System.out);
    }
    void print(PrintStream stream);
    String get();
    void flush();
}
