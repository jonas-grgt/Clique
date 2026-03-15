package io.github.kusoroadeolu.clique.frames;

import io.github.kusoroadeolu.clique.config.FrameAlign;
import io.github.kusoroadeolu.clique.config.FrameConfiguration;
import io.github.kusoroadeolu.clique.core.display.Component;

import java.util.ArrayList;
import java.util.List;

import static io.github.kusoroadeolu.clique.ansi.StyleCode.RESET;
import static io.github.kusoroadeolu.clique.core.utils.Constants.EMPTY;
import static java.util.Objects.requireNonNull;

public final class FrameBuilder{
        final List<FrameNode> nodes;
        String title = EMPTY;
        final FrameType type;
        FrameAlign titleAlign;
        private static final String NULL_FRAME_ALIGN = "Frame align cannot be null";
        final FrameConfiguration configuration;
        int width;

        FrameBuilder(FrameConfiguration configuration, FrameType type) {
            requireNonNull(configuration, "Configuration cannot be null");
            requireNonNull(type, "Frame type cannot be null");
            this.nodes = new ArrayList<>();
            this.type = type;
            this.configuration = configuration;
        }

        FrameBuilder(){
            this(FrameConfiguration.DEFAULT, FrameType.DEFAULT);
        }

        FrameBuilder(FrameType type) {
            this(FrameConfiguration.DEFAULT, type);
        }

        FrameBuilder(FrameConfiguration configuration) {
            this(configuration, FrameType.DEFAULT);
        }


    public FrameBuilder title(String title, FrameAlign titleAlign){
            requireNonNull(title, "Title cannot be null");
            requireNonNull(titleAlign, NULL_FRAME_ALIGN);
            this.title = title;
            this.titleAlign = titleAlign;
            return this;
        }

        public FrameBuilder title(String title){
            return title(title, FrameAlign.CENTER);
        }

        public FrameBuilder width(int width) {
            if (width < 0) throw new IllegalArgumentException("Width cannot be negative");
            this.width = width;
            return this;
        }

        public FrameBuilder nest(String str){
            return nest(str, configuration.getFrameAlign());
        }



        public FrameBuilder nest(Component component, FrameAlign align){
            requireNonNull(component, "Component cannot be null");
            requireNonNull(align, NULL_FRAME_ALIGN);
            nodes.add(new FrameNode.ComponentNode(component, align));
            return this;
        }


        public FrameBuilder nest(Component component){
            return nest(component, configuration.getFrameAlign());
        }

        public FrameBuilder nest(String str, FrameAlign align){
            requireNonNull(str, "String cannot be null");
            if (configuration.getParser() != null) str = str + RESET;
            nodes.add(new FrameNode.StringNode(str, align, configuration.getParser()));
            return this;
        }

        public DefaultFrame build(){
            return new DefaultFrame(this);
        }

    }