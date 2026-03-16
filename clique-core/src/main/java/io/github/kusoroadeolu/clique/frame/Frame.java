package io.github.kusoroadeolu.clique.frame;

import io.github.kusoroadeolu.clique.config.BorderStyle;
import io.github.kusoroadeolu.clique.config.FrameAlign;
import io.github.kusoroadeolu.clique.config.FrameConfiguration;
import io.github.kusoroadeolu.clique.core.display.Bordered;
import io.github.kusoroadeolu.clique.core.display.Component;
import io.github.kusoroadeolu.clique.core.structures.Cell;
import io.github.kusoroadeolu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.style.StyleBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.ansi.StyleCode.RESET;
import static io.github.kusoroadeolu.clique.core.utils.Constants.*;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.parseToCell;

public class Frame implements Bordered {
    private final List<FrameNode> nodes;
    private final FrameConfiguration configuration;
    private final FrameType type;
    private String title;
    private String cachedFrame = null;
    private FrameAlign titleAlign;
    private int width;
    private final BorderChars borderChar;
    private static final String NULL_FRAME_ALIGN = "Frame align cannot be null";

    public Frame(FrameConfiguration configuration, FrameType type) {
        Objects.requireNonNull(configuration, "Configuration cannot be null");
        Objects.requireNonNull(type, "Frame type cannot be null");
        this.nodes = new ArrayList<>();
        this.configuration = configuration;
        this.type = type;
        this.title = EMPTY;
        this.titleAlign = FrameAlign.CENTER;
        this.width = 0;
        borderChar = BorderChars.from(type);
        this.styleBorders(borderChar);
    }

    public Frame() {
        this(FrameConfiguration.DEFAULT, FrameType.DEFAULT);
    }

    public Frame(FrameType type) {
        this(FrameConfiguration.DEFAULT, type);
    }

    public Frame(FrameConfiguration configuration) {
        this(configuration, FrameType.DEFAULT);
    }

    public Frame title(String title, FrameAlign titleAlign) {
        Objects.requireNonNull(title, "Title cannot be null");
        Objects.requireNonNull(titleAlign, NULL_FRAME_ALIGN);
        this.title = title;
        this.titleAlign = titleAlign;
        return this;
    }

    public Frame title(String title) {
        return title(title, FrameAlign.CENTER);
    }

    public Frame width(int width) {
        if (width < 0) throw new IllegalArgumentException("Width cannot be negative");
        this.width = width;
        nullCachedFrame();
        return this;
    }

    public Frame nest(String str) {
        return nest(str, configuration.getFrameAlign());
    }

    public Frame nest(String str, FrameAlign align) {
        Objects.requireNonNull(str, "String cannot be null");
        Objects.requireNonNull(align, NULL_FRAME_ALIGN);
        if (configuration.getParser() != null) str = str + RESET;
        nodes.add(new FrameNode.StringNode(str, align, configuration.getParser()));
        nullCachedFrame();
        return this;
    }

    public Frame nest(Component component) {
        return nest(component, configuration.getFrameAlign());
    }

    public Frame nest(Component component, FrameAlign align) {
        Objects.requireNonNull(component, "Component cannot be null");
        Objects.requireNonNull(align, NULL_FRAME_ALIGN);
        nodes.add(new FrameNode.ComponentNode(component, align));
        nullCachedFrame();
        return this;
    }

    public String get() {
        if (cachedFrame != null) return cachedFrame;
        var appendedTitle = title;

        //If parser is not null
        if (configuration.getParser() != null && !title.isEmpty()) appendedTitle = title + RESET; //Add a reset flag to prevent title colors from bleeding


        var parsedTitle = parseToCell(appendedTitle, configuration.getParser());
        int titleWidth = parsedTitle.width() + 2;

        int resolvedWidth = (width <= 0 ? findNodesMaxWidth() : width) + (configuration.getPadding() * 2);

        if (!parsedTitle.isBlank()) validateTitleWidth(titleWidth, resolvedWidth);

        var sb = new StringBuilder();

        appendTitleToBox(parsedTitle, resolvedWidth, titleWidth, sb);

        for (FrameNode node : nodes) {
            align(node, resolvedWidth, sb, borderChar);
        }

        sb.append(borderChar.bottomLeft())
                .append(borderChar.hLine().repeat(resolvedWidth))
                .append(borderChar.bottomRight())
                .append(NEWLINE);

        return (cachedFrame = sb.toString());
    }

    void appendTitleToBox(Cell parsedTitle, int resolvedWidth, int titleWidth, StringBuilder sb){
        if (!parsedTitle.isBlank()) {
            int leftWidth = findTitleBlockOffset(resolvedWidth, titleWidth, titleAlign);
            sb.append(borderChar.topLeft())
                    .append(borderChar.hLine().repeat(leftWidth))
                    .append(BLANK).append(parsedTitle.styledText()).append(BLANK)
                    .append(borderChar.hLine().repeat(resolvedWidth - titleWidth - leftWidth))
                    .append(borderChar.topRight())
                    .append(NEWLINE);
        } else {
            sb.append(borderChar.topLeft())
                    .append(borderChar.hLine().repeat(resolvedWidth))
                    .append(borderChar.topRight())
                    .append(NEWLINE);
        }
    }

    public void render() {
        System.out.print(get());
    }

    int findNodesMaxWidth() {
        return nodes.stream()
                .mapToInt(FrameNode::maxWidth)
                .max()
                .getAsInt();
    }

    void align(FrameNode node, int innerWidth, StringBuilder sb, BorderChars borderChar) {
        int contentWidth = node.maxWidth();
        if (contentWidth > innerWidth)
            throw new IllegalArgumentException("String with max width %s is greater than frame width %s".formatted(contentWidth, innerWidth));

        int blockOffset = findBlockOffset(innerWidth, contentWidth, node.align());

        for (var line : node.lines()) {
            String content = line.styledText();
            int rightPad = innerWidth - blockOffset - line.width();
            sb.append(borderChar.vLine())
                    .append(BLANK.repeat(blockOffset))
                    .append(content)
                    .append(BLANK.repeat(Math.max(ZERO, rightPad)))
                    .append(borderChar.vLine())
                    .append(NEWLINE);
        }
    }

    static int findBlockOffset(int innerWidth, int contentWidth, FrameAlign align) {
        return switch (align) {
            case LEFT -> ZERO;
            case RIGHT -> innerWidth - contentWidth;
            case CENTER -> (innerWidth - contentWidth) / 2;
        };
    }

    static int findTitleBlockOffset(int innerWidth, int contentWidth, FrameAlign align) {
        return switch (align) {
            case LEFT -> 1;
            case RIGHT -> (innerWidth - contentWidth) - 1; //Just
            case CENTER -> (innerWidth - contentWidth) / 2;
        };
    }

    void validateTitleWidth(int titleWidth, int resolvedWidth) {
        if (titleWidth > resolvedWidth)
            throw new IllegalArgumentException("Title of width %s is greater than Frame of width %s".formatted(titleWidth, resolvedWidth));
    }

    void styleBorders(BorderChars borderChar) {
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

    void nullCachedFrame(){
        cachedFrame = null;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        Frame frame = (Frame) object;
        return width == frame.width && Objects.equals(nodes, frame.nodes) && Objects.equals(configuration, frame.configuration) && type == frame.type && Objects.equals(title, frame.title) && titleAlign == frame.titleAlign && Objects.equals(borderChar, frame.borderChar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, nodes, configuration, type, title, titleAlign, borderChar);
    }
}