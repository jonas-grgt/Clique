package io.github.kusoroadeolu.clique.frames;

import io.github.kusoroadeolu.clique.boxes.*;

record BorderChars(String hLine, String vLine, String topLeft, String topRight, String bottomLeft, String bottomRight) {
    static BorderChars from(BoxType type) {
        return switch (type){
            case DEFAULT -> new BorderChars("-", "|", "+", "+", "+", "+");
            case DOUBLE_LINE -> new BorderChars("═", "║",  "╔", "╗", "╚", "╝");
            case CLASSIC -> new BorderChars("─", "│", "┌", "┐", "└", "┘");
            case ROUNDED -> new BorderChars("─", "│", "╭", "╮", "╰", "╯");
        };
    }
}