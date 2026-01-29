package com.github.kusoroadeolu.clique.core.display;

import java.io.PrintStream;

public interface Renderable {
    default void render(){
        this.render(System.out);
    }
    void render(PrintStream stream);
}
