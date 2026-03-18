package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.core.display.Bordered;
import io.github.kusoroadeolu.clique.core.display.Customizable;

public interface Box extends Bordered, Customizable<Box> {
    Box content(String content);

    Box content(Object object);
}
