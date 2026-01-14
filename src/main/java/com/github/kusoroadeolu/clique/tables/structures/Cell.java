package com.github.kusoroadeolu.clique.tables.structures;

public class Cell {
    private String text;
    private String styledText;
    public Cell(String text, String styledText) {
        this.text = text;
        this.styledText = styledText;
    }

    public String styledText() {
        return styledText;
    }

    public Cell setStyledText(String styledText) {
        this.styledText = styledText;
        return this;
    }

    public String text() {
        return text;
    }

    public int displayWidth(){
        int width = 0;
        for (int i = 0; i < text.length(); ) {
            int codePoint = text.codePointAt(i);

            if (Character.isEmojiPresentation(codePoint)) {
                width += 2;
            } else {
                width += 1;
            }

            i += Character.charCount(codePoint);
        }
        return width;
    }

    public Cell setText(String text) {
        this.text = text;
        return this;
    }
}
