package io.github.kusoroadeolu.clique.frame;

import io.github.kusoroadeolu.clique.boxes.BoxType;
import io.github.kusoroadeolu.clique.config.BorderStyle;
import io.github.kusoroadeolu.clique.config.FrameAlign;
import io.github.kusoroadeolu.clique.config.FrameConfiguration;
import io.github.kusoroadeolu.clique.core.display.Component;
import io.github.kusoroadeolu.clique.core.exceptions.InvalidDimensionException;
import io.github.kusoroadeolu.clique.core.structures.BorderChars;
import io.github.kusoroadeolu.clique.core.structures.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.ansi.StyleCode.RESET;
import static io.github.kusoroadeolu.clique.core.utils.BoxUtils.applyAnsiToBorders;
import static io.github.kusoroadeolu.clique.core.utils.Constants.*;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.parseToCell;

public class DefaultFrame implements  Frame {
    private final List<FrameNode> nodes;
    private final FrameConfiguration configuration;
    private final BoxType type;
    private String title;
    private FrameAlign titleAlign;
    private int width;
    private final BorderChars borderChars;
    private static final int NO_WIDTH_SET = 0;
    private static final String NULL_FRAME_ALIGN = "DefaultFrame align cannot be null";

    public DefaultFrame(FrameConfiguration configuration, BoxType type) {
        Objects.requireNonNull(configuration, "Configuration cannot be null");
        Objects.requireNonNull(type, "DefaultFrame type cannot be null");
        this.nodes = new ArrayList<>();
        this.configuration = configuration;
        this.type = type;
        this.title = EMPTY;
        this.titleAlign = FrameAlign.CENTER;
        this.width = NO_WIDTH_SET;
        borderChars = BorderChars.from(type);
        this.styleBorders(borderChars);
    }

    public DefaultFrame() {
        this(FrameConfiguration.DEFAULT, BoxType.ROUNDED);
    }

    public DefaultFrame(BoxType type) {
        this(FrameConfiguration.DEFAULT, type);
    }

    public DefaultFrame(FrameConfiguration configuration) {
        this(configuration, BoxType.ROUNDED);
    }

    @Override
    public DefaultFrame title(String title, FrameAlign titleAlign) {
        Objects.requireNonNull(title, "Title cannot be null");
        Objects.requireNonNull(titleAlign, NULL_FRAME_ALIGN);
        this.title = title;
        this.titleAlign = titleAlign;
        return this;
    }

    @Override
    public DefaultFrame title(String title) {
        return title(title, FrameAlign.CENTER);
    }

    @Override
    public DefaultFrame width(int width) {
        if (width <= 0) throw new InvalidDimensionException("Width cannot be zero or negative");
        this.width = width;
        return this;
    }

    @Override
    public DefaultFrame nest(String str) {
        return nest(str, configuration.getFrameAlign());
    }

    @Override
    public DefaultFrame nest(String str, FrameAlign align) {
        Objects.requireNonNull(str, "String cannot be null");
        Objects.requireNonNull(align, NULL_FRAME_ALIGN);
        if (configuration.getParser() != null) str = str + RESET;
        nodes.add(new FrameNode.StringNode(str, align, configuration.getParser()));
        return this;
    }

    @Override
    public DefaultFrame nest(Component component) {
        return nest(component, configuration.getFrameAlign());
    }

    @Override
    public DefaultFrame nest(Component component, FrameAlign align) {
        Objects.requireNonNull(component, "Component cannot be null");
        Objects.requireNonNull(align, NULL_FRAME_ALIGN);
        nodes.add(new FrameNode.ComponentNode(component, align));
        return this;
    }

    public String get() {
        var appendedTitle = title;

        //If parser is not null
        if (configuration.getParser() != null && !title.isEmpty()) appendedTitle = title + RESET; //Add a reset flag to prevent title colors from bleeding


        var parsedTitle = parseToCell(appendedTitle, configuration.getParser());
        int titleWidth = parsedTitle.width() + 2;

        int resolvedWidth = (this.width == NO_WIDTH_SET ? findNodesMaxWidth() : this.width);

        if (this.width == NO_WIDTH_SET && !parsedTitle.isBlank()) {
            resolvedWidth = Math.max(resolvedWidth, titleWidth);
        }

        int paddedWidth = resolvedWidth + configuration.getPadding();
        if (!parsedTitle.isBlank()) validateTitleWidth(titleWidth, resolvedWidth);

        var sb = new StringBuilder();

        appendTitleToBox(parsedTitle, paddedWidth, titleWidth, sb);

        for (FrameNode node : nodes) {
            align(node, resolvedWidth ,paddedWidth , sb, borderChars);
        }

        sb.append(borderChars.bottomLeft())
                .append(borderChars.hLine().repeat(paddedWidth))
                .append(borderChars.bottomRight())
                .append(NEWLINE);

        return sb.toString();
    }

    void appendTitleToBox(Cell parsedTitle, int resolvedWidth, int titleWidth, StringBuilder sb){
        if (!parsedTitle.isBlank()) {
            int leftWidth = findTitleBlockOffset(resolvedWidth, titleWidth, titleAlign);
            sb.append(borderChars.topLeft())
                    .append(borderChars.hLine().repeat(leftWidth))
                    .append(BLANK).append(parsedTitle.styledText()).append(BLANK)
                    .append(borderChars.hLine().repeat(resolvedWidth - titleWidth - leftWidth))
                    .append(borderChars.topRight())
                    .append(NEWLINE);
        } else {
            sb.append(borderChars.topLeft())
                    .append(borderChars.hLine().repeat(resolvedWidth))
                    .append(borderChars.topRight())
                    .append(NEWLINE);
        }
    }

    int findNodesMaxWidth() {
        return nodes.stream()
                .mapToInt(FrameNode::maxWidth)
                .max()
                .getAsInt();
    }

    void align(FrameNode node, int resolvedWidth ,int paddedWidth, StringBuilder sb, BorderChars borderChar) {
        int contentWidth = node.maxWidth();
        if (contentWidth > resolvedWidth) throw new IllegalArgumentException("String with width %s is greater than frame width %s".formatted(contentWidth, paddedWidth));

        int blockOffset = findBlockOffset(paddedWidth, contentWidth, node.align(), configuration.getPadding());
        for (var line : node.lines()) {
            String content = line.styledText();
            int rightPad = paddedWidth - blockOffset - line.width();
            sb.append(borderChar.vLine())
                    .append(BLANK.repeat(blockOffset))
                    .append(content)
                    .append(BLANK.repeat(Math.max(ZERO, rightPad)))
                    .append(borderChar.vLine())
                    .append(NEWLINE);
        }
    }

    static int findBlockOffset(int innerWidth, int contentWidth, FrameAlign align, int padding) {
        return switch (align) {
            case LEFT -> ZERO + padding;
            case RIGHT -> innerWidth - contentWidth - padding;
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
            throw new IllegalArgumentException("Title of width %s is greater than DefaultFrame of width %s".formatted(titleWidth, resolvedWidth));
    }


    void customizeCorner(char corner) {
        var str = String.valueOf(corner);
        if (!str.isBlank()){
            borderChars.setCorners(str);
        }
    }

    void customizeVLine(char vLine) {
        var str = Character.toString(vLine);
        if (!str.isBlank()){
            borderChars.setVLine(str);
        }
    }

    void customizeHLine(char hLine) {
        var str = Character.toString(hLine);
        if (!str.isBlank()){
            borderChars.setHLine(str);
        }
    }

    void styleBorders(BorderChars borderChar) {
        final BorderStyle borderStyle = this.configuration.getBorderStyle();
        if (borderStyle != null) {
            applyAnsiToBorders(borderChar, borderStyle);
            customizeHLine(borderStyle.getHorizontalChar());
            customizeVLine(borderStyle.getVerticalChar());
            customizeCorner(borderStyle.getCornerChar());
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        DefaultFrame frame = (DefaultFrame) object;
        return width == frame.width && Objects.equals(nodes, frame.nodes) && Objects.equals(configuration, frame.configuration) && type == frame.type && Objects.equals(title, frame.title) && titleAlign == frame.titleAlign && Objects.equals(borderChars, frame.borderChars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, nodes, configuration, type, title, titleAlign, borderChars);
    }
}