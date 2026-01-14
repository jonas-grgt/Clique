package com.github.kusoroadeolu.clique.tables.structures;

public record Cell(String text, String styledText) {

    public int displayWidth() {
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

}
