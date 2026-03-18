package io.github.kusoroadeolu.clique.spi;

public interface RGBAnsiCode extends AnsiCode{
    int red();
    int blue();
    int green();
    boolean background(); //If this is a background color
}
