package io.github.kusoroadeolu.clique.frames;

import io.github.kusoroadeolu.clique.config.FrameConfiguration;
import io.github.kusoroadeolu.clique.core.display.Generated;

public interface Frame extends Generated {
    static FrameBuilder builder(){
        return new FrameBuilder();
    }

    static FrameBuilder builder(FrameConfiguration configuration, FrameType type){
        return new FrameBuilder(configuration, type);
    }

    static FrameBuilder builder(FrameConfiguration configuration){
        return new FrameBuilder(configuration);
    }

    static FrameBuilder builder(FrameType type){
        return new FrameBuilder(type);
    }
}
