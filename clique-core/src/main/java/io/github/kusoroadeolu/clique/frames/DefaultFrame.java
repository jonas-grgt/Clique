package io.github.kusoroadeolu.clique.frames;

import io.github.kusoroadeolu.clique.boxes.BoxType;
import io.github.kusoroadeolu.clique.config.BorderStyle;
import io.github.kusoroadeolu.clique.config.FrameAlign;
import io.github.kusoroadeolu.clique.config.FrameConfiguration;
import io.github.kusoroadeolu.clique.core.display.Component;
import io.github.kusoroadeolu.clique.core.display.Generated;
import io.github.kusoroadeolu.clique.core.structures.Cell;
import io.github.kusoroadeolu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.style.StyleBuilder;

import java.util.ArrayList;
import java.util.List;

import static io.github.kusoroadeolu.clique.ansi.StyleCode.RESET;
import static io.github.kusoroadeolu.clique.core.utils.Constants.*;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.parseCell;
import static java.util.Objects.requireNonNull;

public class DefaultFrame implements Generated {
    private final List<FrameNode> nodes;
    private final int maxWidth;
    private final int titleWidth;
    private final FrameConfiguration configuration;
    private final Cell title;
    private final BorderChars borderChar;
    private static final String RESET = "[/]";

     DefaultFrame(FrameBuilder builder) {
        this.nodes = builder.nodes;
        this.configuration = builder.configuration;
        this.maxWidth = builder.width <= 0 ? findNodesMaxWidth() : builder.width;
        String appendedTitle = builder.title;
        if (configuration.getParser() != null) appendedTitle = builder.title + RESET;
        this.title = parseCell(appendedTitle, configuration.getParser());
        this.titleWidth = title.width() + 2;
        this.borderChar = BorderChars.from(builder.type);
        styleBorders();
    }

     public String get(){
        var sb = new StringBuilder();
        int innerWidth = maxWidth + (configuration.getPadding() * 2); //Space between two vlines

        if (!title.isBlank()){
            int leftWidth = findBlockOffset(innerWidth, titleWidth , FrameAlign.LEFT); //Hardcoded for now

            sb.append(borderChar.topLeft())
                    .append(borderChar.hLine().repeat(leftWidth))
                    .append(BLANK).append(title.styledText()).append(BLANK)
                    .append(borderChar.hLine().repeat(innerWidth - titleWidth - leftWidth))
                    .append(borderChar.topRight())
                    .append(NEWLINE);
        }
        else sb.append(borderChar.topLeft()).append(borderChar.hLine().repeat(innerWidth)).append(borderChar.topRight()).append(NEWLINE);

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
        int contentWidth = node.maxWidth(); // longest line in the component

        int blockOffset = findBlockOffset(innerWidth, contentWidth, node.align()); //2


         for (Cell line : node.lines()) {
            String content = line.styledText();
            int rightPad = innerWidth - blockOffset - line.width(); //Given inner-width as 20, align(left = 0), content width = 5, right pad = 20-5-0 =15;
            sb.append(borderChar.vLine())
                    .append(BLANK.repeat(blockOffset))
                    .append(content)
                    .append(BLANK.repeat(Math.max(ZERO, rightPad)))
                    .append(borderChar.vLine())
                    .append(NEWLINE);
        }
    }


    static int findBlockOffset(int innerWidth, int contentWidth, FrameAlign align){
        return switch (align) {
            case LEFT -> ZERO;
            case RIGHT -> innerWidth - contentWidth;
            case CENTER -> (innerWidth - contentWidth) / 2;
        };
    }

    protected void styleBorders() {
        if (this.configuration.getBorderStyle() != null) {
            final BorderStyle borderStyle = this.configuration.getBorderStyle();
            final StyleBuilder sb = borderStyle.styleBuilder();
            final AnsiCode[] horizontalStyles = borderStyle.getHorizontalBorderStyles();
            final AnsiCode[] verticalStyles = borderStyle.getVerticalBorderStyles();
            final AnsiCode[] edgeStyles = borderStyle.getEdgeBorderStyles();

            borderChar.setHLine(sb.formatAndReset(borderChar.hLine(), horizontalStyles));
            borderChar.setVLine(sb.formatAndReset(borderChar.vLine(), verticalStyles));
            borderChar.setTopLeft(sb.formatAndReset(borderChar.topLeft(), edgeStyles));
            borderChar.setTopRight(sb.formatAndReset(borderChar.topRight(), edgeStyles));
            borderChar.setBottomLeft(sb.formatAndReset(borderChar.bottomLeft(), edgeStyles));
            borderChar.setBottomRight(sb.formatAndReset(borderChar.bottomRight(), edgeStyles));
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
        private final BoxType type;
        private static final String NULL_FRAME_ALIGN = "Frame align cannot be null";
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
            if (configuration.getParser() != null) str = str + RESET;
            nodes.add(new FrameNode.StringNode(str, align, configuration.getParser()));
            return this;
        }

        public DefaultFrame build(){
            return new DefaultFrame(this);
        }

    }
}
