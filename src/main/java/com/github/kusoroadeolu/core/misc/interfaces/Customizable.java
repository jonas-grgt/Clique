package com.github.kusoroadeolu.core.misc.interfaces;

public interface Customizable<T extends Customizable<T>> {
    T customizeEdge(char edge);
    T customizeVerticalLine(char vLine);
    T customizeHorizontalLine(char hLine);
}
