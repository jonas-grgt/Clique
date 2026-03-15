package io.github.kusoroadeolu.clique.frames;

import io.github.kusoroadeolu.clique.config.BorderStyle;
import io.github.kusoroadeolu.clique.config.FrameAlign;
import io.github.kusoroadeolu.clique.config.FrameConfiguration;
import io.github.kusoroadeolu.clique.core.structures.Cell;
import io.github.kusoroadeolu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.style.StyleBuilder;

import java.util.List;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.ansi.StyleCode.RESET;
import static io.github.kusoroadeolu.clique.core.utils.Constants.*;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.parseCell;

public class DefaultFrame implements Frame {
    private final List<FrameNode> nodes;
    private final int width;
    private final int titleWidth;
    private final FrameConfiguration configuration;
    private final Cell title;
    private final BorderChars borderChar;
    private String frameString = null;
    private final FrameAlign titleAlign;

     DefaultFrame(FrameBuilder builder) {
        this.nodes = builder.nodes;
        this.configuration = builder.configuration;
        this.width = (builder.width <= 0 ? findNodesMaxWidth() : builder.width) + (configuration.getPadding() * 2);
        var appendedTitle = builder.title;
        if (configuration.getParser() != null) appendedTitle = builder.title + RESET;
        this.title = parseCell(appendedTitle, configuration.getParser());
        this.titleWidth = title.width() + 2;
        validateTitleWidth();
        this.borderChar = BorderChars.from(builder.type);
        this.titleAlign = builder.titleAlign;
        styleBorders();
    }

     public String get(){
         if (frameString != null) return frameString;
        var sb = new StringBuilder();
        if (!title.isBlank()){
            int leftWidth = findTitleBlockOffset(width, titleWidth , titleAlign);

            sb.append(borderChar.topLeft())
                    .append(borderChar.hLine().repeat(leftWidth))
                    .append(BLANK).append(title.styledText()).append(BLANK)
                    .append(borderChar.hLine().repeat(width - titleWidth - leftWidth))
                    .append(borderChar.topRight())
                    .append(NEWLINE);
        }
        else sb.append(borderChar.topLeft()).append(borderChar.hLine().repeat(width)).append(borderChar.topRight()).append(NEWLINE);

        for (FrameNode node : nodes) {
            align(node, width, sb);
        }

        sb.append(borderChar.bottomLeft()).append(borderChar.hLine().repeat(width)).append(borderChar.bottomRight());
        return (frameString = sb.toString());
     }

     int findNodesMaxWidth(){
         return nodes.stream()
                 .mapToInt(FrameNode::maxWidth)
                 .max()
                 .getAsInt();
     }

     void align(FrameNode node, int innerWidth, StringBuilder sb){
        int contentWidth = node.maxWidth(); // longest line in the component
        if (contentWidth > innerWidth) throw new IllegalArgumentException("String with max width %s is greater than frame width %s".formatted(contentWidth, innerWidth));

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

    static int findTitleBlockOffset(int innerWidth, int contentWidth, FrameAlign align){
        return switch (align) {
            case LEFT -> 1;
            case RIGHT -> (innerWidth - contentWidth) - 1;
            case CENTER -> (innerWidth - contentWidth) / 2;
        };
    }


    void validateTitleWidth(){
        if (titleWidth > width) throw new IllegalArgumentException("Title of width %s is greater than Frame of width %s".formatted(titleWidth, width));
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


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        DefaultFrame that = (DefaultFrame) object;
        return width == that.width && titleWidth == that.titleWidth && Objects.equals(nodes, that.nodes) && Objects.equals(configuration, that.configuration) && Objects.equals(title, that.title) && Objects.equals(borderChar, that.borderChar) && Objects.equals(frameString, that.frameString) && titleAlign == that.titleAlign;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, titleWidth, nodes, configuration, title, titleAlign, frameString, borderChar);
    }

    @Override
    public String toString() {
        return "DefaultFrame[" +
                "nodes=" + nodes +
                ", width=" + width +
                ", titleWidth=" + titleWidth +
                ", configuration=" + configuration +
                ", title=" + title +
                ", borderChar=" + borderChar +
                ", frameString='" + frameString + '\'' +
                ", titleAlign=" + titleAlign +
                ']';
    }
}
