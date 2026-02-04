package com.github.kusoroadeolu.clique.boxes;

import com.github.kusoroadeolu.clique.config.BoxConfiguration;
import com.github.kusoroadeolu.clique.core.display.Renderable;

import java.io.PrintStream;

public interface Box extends Renderable {
    @Deprecated(since = "1.2.1")
    Box configuration(BoxConfiguration boxConfiguration);
    Box content(String content);
    String buildBox();
    void render(PrintStream stream);
}
