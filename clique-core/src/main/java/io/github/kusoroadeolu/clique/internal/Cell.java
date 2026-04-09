package io.github.kusoroadeolu.clique.internal;

import io.github.kusoroadeolu.clique.internal.documentation.InternalApi;
import io.github.kusoroadeolu.clique.internal.utils.CharWidth;

@InternalApi(since = "3.2.0")
public final class Cell {
    private final String text;
    private final String styledText;
    private final int width;

    public Cell(String text, String styledText) {
        this.text = text;
        this.styledText = styledText;
        this.width = CharWidth.of(text);
    }

    public String text() {
        return text;
    }

    public String styledText() {
        return styledText;
    }

    public int width() {
        return width;
    }

    public boolean isEmpty() {
        return width() == 0;
    }

    @Override
    public String toString() {
        return "Cell[" +
                "styledText='" + styledText + '\'' +
                ", text='" + text + '\'' +
                ", width=" + width +
                ']';
    }
}