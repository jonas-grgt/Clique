package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.config.BorderStyle;
import io.github.kusoroadeolu.clique.config.BoxConfiguration;
import io.github.kusoroadeolu.clique.core.structures.BorderChars;

import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.BoxUtils.*;

//TODO fix BoxConfig#padding to apply padding at both sides r
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

    void customizeCorner(char corner) {
        var str = String.valueOf(corner);
        if (!str.isBlank()){
            borderChars.setCorners(str);
            cachedString = null;
        }
    }

    void customizeVLine(char vLine) {
        var str = Character.toString(vLine);
        if (!str.isBlank()){
            borderChars.setVLine(str);
            cachedString = null;
        }
    }

    void customizeHLine(char hLine) {
        var str = Character.toString(hLine);
        if (!str.isBlank()){
            borderChars.setHLine(str);
            cachedString = null;
        }
    }

    void styleBorders() {
        final BorderStyle borderStyle = this.boxConfiguration.getBorderStyle();
        if (borderStyle != null) {
            applyAnsiToBorders(borderChars, borderStyle);
            customizeHLine(borderStyle.getHorizontalChar());
            customizeVLine(borderStyle.getVerticalChar());
            customizeCorner(borderStyle.getCornerChar());
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

    @Override
    public CustomizableBox customizeEdge(char edge) {
        customizeCorner(edge);
        return this;
    }

    @Override
    public CustomizableBox customizeVerticalLine(char vLine) {
        customizeVLine(vLine);
        return this;
    }

    @Override
    public CustomizableBox customizeHorizontalLine(char hLine) {
        customizeHLine(hLine);
        return this;
    }
}

