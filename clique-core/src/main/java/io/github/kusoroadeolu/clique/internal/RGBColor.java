package io.github.kusoroadeolu.clique.internal;

import io.github.kusoroadeolu.clique.internal.documentation.InternalApi;
import io.github.kusoroadeolu.clique.spi.RGBAnsiCode;

@InternalApi(since = "3.2.0")

//"
public class RGBColor implements RGBAnsiCode {
    private final int red;
    private final int green;
    private final int blue;
    private final boolean isBackground;
    private final String ansiSequence;

    public RGBColor(int red, int green, int blue, boolean isBackground) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.isBackground = isBackground;
        int type = isBackground ? 48 : 38;
        ansiSequence = "\u001B[%d;2;%d;%d;%dm".formatted(type, red, green, blue);
    }

    public RGBColor(int red, int green, int blue) {
        this(red, green, blue, false);
    }

    public String ansiSequence() {
        return ansiSequence;
    }

    public int red() {
        return red;
    }

    public int green() {
        return green;
    }

    public int blue() {
        return blue;
    }

    public boolean isBackground() {
        return isBackground;
    }
}
