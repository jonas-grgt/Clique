package io.github.kusoroadeolu.clique.boxes;

import io.github.kusoroadeolu.clique.config.BorderStyle;
import io.github.kusoroadeolu.clique.config.BoxConfiguration;
import io.github.kusoroadeolu.clique.config.TextAlign;
import io.github.kusoroadeolu.clique.core.structures.BorderChars;

import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.BoxUtils.applyAnsiToBorders;
import static io.github.kusoroadeolu.clique.core.utils.BoxUtils.drawBox;

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
        this.cells = resolveLines();
        this.resolveDimensions(this.cells);
        TextAlign ta = this.align == null ? boxConfiguration.getTextAlign() : this.align;


        final var contentLs = this.cells;
        final var chars = this.borderChars;
        final StringBuilder sb = new StringBuilder();
        final BoxWrapper wrapper = new BoxWrapper(
                this.width, this.height, this.boxConfiguration,
                contentLs.cells(), chars.hLine(), chars.vLine(),
                chars.topLeft(), chars.topRight(), chars.bottomRight(), chars.bottomLeft()
        );
        drawBox(sb, wrapper, ta);
        return (cachedString = sb.toString());
    }

    void customizeCorner(char corner) {
        var str = String.valueOf(corner);
        if (!str.isBlank()){
            borderChars.setCorners(str);
            nullCachedString();
        }
    }

    void customizeVLine(char vLine) {
        var str = Character.toString(vLine);
        if (!str.isBlank()){
            borderChars.setVLine(str);
            nullCachedString();
        }
    }

    void customizeHLine(char hLine) {
        var str = Character.toString(hLine);
        if (!str.isBlank()){
            borderChars.setHLine(str);
            nullCachedString();
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
}

