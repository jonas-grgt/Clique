package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.config.TextAlign;
import io.github.kusoroadeolu.clique.core.display.Bordered;

public interface Box extends Bordered {
    Box content(String content);

    Box content(Object object);

    Box content(String content, TextAlign align);

    Box content(Object object, TextAlign align);
}
