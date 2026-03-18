package io.github.kusoroadeolu.clique.ansi;

import io.github.kusoroadeolu.clique.spi.RGBAnsiCode;

public record RGBAnsiColor(int red, int green, int blue, boolean isBackground) implements RGBAnsiCode {
    
    public RGBAnsiColor {
        if (red < 0 || red > 255) throw new IllegalArgumentException("Red must be 0-255");
        if (green < 0 || green > 255) throw new IllegalArgumentException("Green must be 0-255");
        if (blue < 0 || blue > 255) throw new IllegalArgumentException("Blue must be 0-255");
    }
    
    public String toString() {
        int type = isBackground ? 48 : 38;
        return "\u001B[%d;2;%d;%d;%dm".formatted(type, red, green, blue);
    }
}