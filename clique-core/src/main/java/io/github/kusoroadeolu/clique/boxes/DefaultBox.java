package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.config.BorderStyle;
import io.github.kusoroadeolu.clique.config.BoxConfiguration;
import io.github.kusoroadeolu.clique.core.structures.BorderChars;

import static io.github.kusoroadeolu.clique.core.utils.BoxUtils.*;

class DefaultBox extends AbstractBox implements CustomizableBox {
    private final BorderChars borderChars;

    DefaultBox(BorderChars borderChars, BoxConfiguration configuration) {
        super();
        this.borderChars = borderChars;
        this.boxConfiguration = configuration;
        this.styleBorders();
    }

    public String get() {
        if (cachedString != null) return cachedString;
        return (cachedString = handleDimensionsEx(() -> {
            this.wrapWord();
            final var chars = this.borderChars;
            final StringBuilder sb = new StringBuilder();
            final BoxWrapper wrapper = new BoxWrapper(
                    this.width, this.height, this.boxConfiguration,
                    this.contentWrap, chars.hLine(), chars.vLine(),
                    chars.topLeft(), chars.topRight(), chars.bottomRight(), chars.bottomLeft()
            );
            drawBox(sb, wrapper);
            return sb.toString();
        }));
    }

    public CustomizableBox customizeEdge(char edge) {
        borderChars.setEdges(String.valueOf(edge));
        return this;
    }

    public CustomizableBox customizeVerticalLine(char vLine) {
        borderChars.setVLine(String.valueOf(vLine));
        return this;
    }

    public CustomizableBox customizeHorizontalLine(char hLine) {
        borderChars.setHLine(String.valueOf(hLine));
        return this;
    }

    void styleBorders() {
        if (this.boxConfiguration.getBorderStyle() != null) {
            final BorderStyle borderStyle = this.boxConfiguration.getBorderStyle();
            applyAnsiToBorders(borderChars, borderStyle);
        }
    }

    @Override
    public CustomizableBox customize() {
        return this;
    }
}

