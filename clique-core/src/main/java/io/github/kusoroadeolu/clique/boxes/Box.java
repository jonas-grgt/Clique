package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.config.TextAlign;
import io.github.kusoroadeolu.clique.core.display.Bordered;
import io.github.kusoroadeolu.clique.core.documentation.Stable;

@Stable(since = "3.2.0")
public interface Box extends Bordered {
    Box content(String content);

    Box content(String content, TextAlign align);

    Box dimensions(int width, int height);
}
