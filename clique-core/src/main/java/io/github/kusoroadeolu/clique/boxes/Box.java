package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.core.display.Generated;

public interface Box extends Generated {
    Box content(String content);
    Box content(Object object);
}
