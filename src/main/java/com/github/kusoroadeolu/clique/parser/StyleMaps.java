package com.github.kusoroadeolu.clique.parser;

import com.github.kusoroadeolu.clique.ansi.AnsiCode;
import com.github.kusoroadeolu.clique.ansi.BackgroundCode;
import com.github.kusoroadeolu.clique.ansi.ColorCode;
import com.github.kusoroadeolu.clique.ansi.StyleCode;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//A simple class which holds the maps of the syntax
final class StyleMaps {

    private StyleMaps(){
        throw new AssertionError();
    }

    /**
     * A static, unmodifiable map that associates lowercase color names (keys)
     * with their corresponding ColorCode enum constants (values).
     */
     static final Map<String, ColorCode> COLOR_CODES = Map.ofEntries(
            // Standard Colors
            Map.entry("black", ColorCode.BLACK),
            Map.entry("red", ColorCode.RED),
            Map.entry("green", ColorCode.GREEN),
            Map.entry("yellow", ColorCode.YELLOW),
            Map.entry("blue", ColorCode.BLUE),
            Map.entry("magenta", ColorCode.MAGENTA),
            Map.entry("cyan", ColorCode.CYAN),
            Map.entry("white", ColorCode.WHITE),

            // Bright Colors
            Map.entry("*black", ColorCode.BRIGHT_BLACK),
            Map.entry("*red", ColorCode.BRIGHT_RED),
            Map.entry("*green", ColorCode.BRIGHT_GREEN),
            Map.entry("*yellow", ColorCode.BRIGHT_YELLOW),
            Map.entry("*blue", ColorCode.BRIGHT_BLUE), // Kept your original example format for bright colors
            Map.entry("*magenta", ColorCode.BRIGHT_MAGENTA),
            Map.entry("*cyan", ColorCode.BRIGHT_CYAN),
            Map.entry("*white", ColorCode.BRIGHT_WHITE)
    );

    /**
     * A static, unmodifiable map that associates lowercase background color names (keys)
     * with their corresponding BackgroundCode enum constants (values).
     */
     static final Map<String, BackgroundCode> BACKGROUND_CODES = Map.ofEntries(
            // Standard Background Colors
            Map.entry("bg_black", BackgroundCode.BLACK),
            Map.entry("bg_red", BackgroundCode.RED),
            Map.entry("bg_green", BackgroundCode.GREEN),
            Map.entry("bg_yellow", BackgroundCode.YELLOW),
            Map.entry("bg_blue", BackgroundCode.BLUE),
            Map.entry("bg_magenta", BackgroundCode.MAGENTA),
            Map.entry("bg_cyan", BackgroundCode.CYAN),
            Map.entry("bg_white", BackgroundCode.WHITE),

            // Bright Background Colors
            Map.entry("*bg_black", BackgroundCode.BRIGHT_BLACK),
            Map.entry("*bg_red", BackgroundCode.BRIGHT_RED),
            Map.entry("*bg_green", BackgroundCode.BRIGHT_GREEN),
            Map.entry("*bg_yellow", BackgroundCode.BRIGHT_YELLOW),
            Map.entry("*bg_blue", BackgroundCode.BRIGHT_BLUE),
            Map.entry("*bg_magenta", BackgroundCode.BRIGHT_MAGENTA),
            Map.entry("*bg_cyan", BackgroundCode.BRIGHT_CYAN),
            Map.entry("*bg_white", BackgroundCode.BRIGHT_WHITE)
    );

    /**
     * A static, unmodifiable map that associates lowercase style names (keys)
     * with their corresponding StyleCode enum constants (values).
     */
     static final Map<String, StyleCode> STYLE_CODES = Map.ofEntries(
            Map.entry("bold", StyleCode.BOLD),
            Map.entry("dim", StyleCode.DIM),
            Map.entry("italic", StyleCode.ITALIC),
            Map.entry("ul", StyleCode.UNDERLINE),
            Map.entry("rv", StyleCode.REVERSE_VIDEO),
            Map.entry("inv", StyleCode.INVISIBLE_TEXT),
            Map.entry("/", StyleCode.RESET),
            Map.entry("dbl_ul", StyleCode.DOUBLE_UNDERLINE),
            Map.entry("strike", StyleCode.STRIKETHROUGH)
    );

     static final Map<String, AnsiCode> GLOBAL_CUSTOM_CODES = new ConcurrentHashMap<>();
}
