package com.github.kusoroadeolu.clique.boxes;

import com.github.kusoroadeolu.clique.config.BoxConfiguration;

public interface Box {
    @Deprecated(since = "1.2.1")
    Box configuration(BoxConfiguration boxConfiguration);

    Box width(int width);

    Box content(String content);

    Box length(int length);

    String buildBox();

    void render();
}
