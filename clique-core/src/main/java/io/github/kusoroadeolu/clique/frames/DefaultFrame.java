package io.github.kusoroadeolu.clique.frames;

import io.github.kusoroadeolu.clique.boxes.BoxType;
import io.github.kusoroadeolu.clique.config.FrameAlign;
import io.github.kusoroadeolu.clique.config.FrameConfiguration;
import io.github.kusoroadeolu.clique.core.display.Component;
import io.github.kusoroadeolu.clique.core.display.Generated;
import io.github.kusoroadeolu.clique.core.structures.Cell;
import io.github.kusoroadeolu.clique.core.utils.CharWidth;

import java.util.ArrayList;
import java.util.List;

import static io.github.kusoroadeolu.clique.core.utils.Constants.*;
import static java.util.Objects.requireNonNull;

public class DefaultFrame implements Generated {
    private final List<FrameNode> nodes;
    private final int maxWidth;
    private final int titleWidth;
    private final FrameConfiguration configuration;
    private final String title;
    private BorderChars borderChar;

     DefaultFrame(FrameBuilder builder) {
        this.nodes = builder.nodes;
        this.maxWidth = builder.width <= 0 ? findNodesMaxWidth() : builder.width;
        this.title = builder.title;
        this.titleWidth = CharWidth.of(title);
        this.configuration = builder.configuration;
        this.borderChar = BorderChars.from(builder.type);
    }

     public String get(){
        StringBuilder sb = new StringBuilder();
        int innerWidth = maxWidth + (configuration.getCenterPadding() * 2); //Space between two vlines
        sb.append(borderChar.topLeft()).append(borderChar.hLine().repeat(innerWidth)).append(borderChar.topRight()).append(NEWLINE);

        for (FrameNode node : nodes) {
            align(node, innerWidth, sb);
        }

        sb.append(borderChar.bottomLeft()).append(borderChar.hLine().repeat(innerWidth)).append(borderChar.bottomRight());
        return sb.toString();
     }

     int findNodesMaxWidth(){
         return nodes.stream()
                 .mapToInt(FrameNode::maxWidth)
                 .max()
                 .getAsInt();
     }

     void align(FrameNode node, int innerWidth, StringBuilder sb){
        int blockWidth = node.maxWidth(); // widest line in the component

         int blockOffset = switch (node.align()) {
            case LEFT -> ZERO;
            case RIGHT -> innerWidth - blockWidth;
            case CENTER -> (innerWidth - blockWidth) / 2;
        };

        for (Cell line : node.lines()) {
            String content = line.styledText();
            int rightPad = innerWidth - blockOffset - line.width(); //Given innerwidth as 20, align(left = 0), content width = 5, right pad = 20-5-0 =15;
            sb.append(borderChar.vLine())
                    .append(BLANK.repeat(blockOffset))
                    .append(content)
                    .append(BLANK.repeat(Math.max(ZERO, rightPad)))
                    .append(borderChar.vLine())
                    .append(NEWLINE);
        }
    }


    public static FrameBuilder builder(){
         return new FrameBuilder();
    }

    public static FrameBuilder builder(FrameConfiguration configuration, BoxType boxType){
        return new FrameBuilder(configuration, boxType);
    }



    public static final class FrameBuilder{
        private final List<FrameNode> nodes;
        private String title = EMPTY;
        private BoxType type;
        private static final String NULL_FRAME_ALIGN = "DefaultFrame align cannot be null";
        private final FrameConfiguration configuration;
        private int width;

        public FrameBuilder(FrameConfiguration configuration, BoxType type) {
            this.nodes = new ArrayList<>();
            this.type = type;
            this.configuration = configuration;
        }

        public FrameBuilder(){
            this(FrameConfiguration.DEFAULT, BoxType.ROUNDED);
        }

        public FrameBuilder title(String title){
            requireNonNull(title, "Title cannot be null");
            this.title = title;
            return this;
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
            nodes.add(new FrameNode.StringNode(str, align, configuration.getParser()));
            return this;
        }

        public DefaultFrame build(){
            return new DefaultFrame(this);
        }

    }
}
