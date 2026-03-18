package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.config.BorderStyle;
import io.github.kusoroadeolu.clique.config.BoxConfiguration;
import io.github.kusoroadeolu.clique.core.structures.BorderChars;

import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.BoxUtils.*;

//TODO fix BoxConfig#padding to apply padding at both sides r
class DefaultBox extends AbstractBox {
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
            if (this.height <= this.contentWrap.size()) {
                throw new IllegalArgumentException();
            }
            final var contentLs = this.contentWrap;
            final var chars = this.borderChars;
            final StringBuilder sb = new StringBuilder();
            final BoxWrapper wrapper = new BoxWrapper(
                    this.width, this.height, this.boxConfiguration,
                    contentLs, chars.hLine(), chars.vLine(),
                    chars.topLeft(), chars.topRight(), chars.bottomRight(), chars.bottomLeft()
            );
            drawBox(sb, wrapper);
            return sb.toString();
        }));
    }

    public Box customizeEdge(char edge) {
        borderChars.setEdges(String.valueOf(edge));
        cachedString = null;
        return this;
    }

    public Box customizeVerticalLine(char vLine) {
        borderChars.setVLine(String.valueOf(vLine));
        cachedString = null;
        return this;
    }

    public Box customizeHorizontalLine(char hLine) {
        borderChars.setHLine(String.valueOf(hLine));
        cachedString = null;
        return this;
    }

    void styleBorders() {
        if (this.boxConfiguration.getBorderStyle() != null) {
            final BorderStyle borderStyle = this.boxConfiguration.getBorderStyle();
            applyAnsiToBorders(borderChars, borderStyle);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        DefaultBox that = (DefaultBox) object;
        return Objects.equals(borderChars, that.borderChars);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(borderChars);
        return result;
    }

    @Override
    public String toString() {
        return "DefaultBox[" +
                "borderChars=" + borderChars +
                ']';
    }
}

