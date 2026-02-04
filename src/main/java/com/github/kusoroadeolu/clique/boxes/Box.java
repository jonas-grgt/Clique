package com.github.kusoroadeolu.clique.boxes;

import com.github.kusoroadeolu.clique.config.BoxConfiguration;
import com.github.kusoroadeolu.clique.core.display.Generated;

import java.io.PrintStream;

public interface Box extends Generated {
    Box content(String content);
}
