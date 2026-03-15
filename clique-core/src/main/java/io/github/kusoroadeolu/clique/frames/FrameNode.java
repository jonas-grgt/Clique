package io.github.kusoroadeolu.clique.frames;

import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.config.FrameAlign;
import io.github.kusoroadeolu.clique.core.display.Component;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;
import io.github.kusoroadeolu.clique.core.structures.Cell;

import java.util.List;

sealed interface FrameNode permits FrameNode.StringNode, FrameNode.ComponentNode {
    List<Cell> lines();
    int maxWidth();
    FrameAlign align();
    AnsiStringParser PARSER = Clique.parser();

    static int findMaxWidth(List<Cell> lines) {
        return lines
                .stream()
                .mapToInt(Cell::width)
                .max()
                .getAsInt();
    }

    static List<Cell> splitComponentLines(String str){
        return str.lines().map(s -> new Cell(PARSER.getOriginalString(s), s)).toList(); //Original to styled string for components, we actually need to parse here with a default parser
    }

    //For raw strings, we need to handle the case in which the string has markup, however for components, when we call the get method, they apply their markup so it's good
    static List<Cell> splitLines(String str, AnsiStringParser parser){
        return str.lines().map(s -> new Cell(s, parser.parse(s))).toList();
    }

    static List<Cell> splitLines(String str){
        return str.lines().map(s -> new Cell(s, s)).toList();
    }


    non-sealed class ComponentNode implements FrameNode {
        private final List<Cell> lines;
        private final int maxWidth;
        private final FrameAlign align;

        public ComponentNode(Component component, FrameAlign align) {
            this.lines = splitComponentLines(component.get());
            this.maxWidth = findMaxWidth(lines);
            this.align = align;
        }

        @Override
        public List<Cell> lines() {
            return lines;
        }

        @Override
        public int maxWidth() {
            return maxWidth;
        }

        @Override
        public FrameAlign align() {
            return align;
        }
    }

    non-sealed class StringNode implements FrameNode {
        private final List<Cell> lines;
        private final int maxWidth;
        private final FrameAlign align;

        public StringNode(String str, FrameAlign align, AnsiStringParser parser) {
            this.lines = split(str, parser);
            this.maxWidth = findMaxWidth(lines);
            this.align = align;
        }

        @Override
        public List<Cell> lines() {
            return this.lines;
        }

        @Override
        public int maxWidth() {
            return maxWidth;
        }

        @Override
        public FrameAlign align() {
            return align;
        }

        static List<Cell> split(String str, AnsiStringParser parser){
            if (parser == null) return FrameNode.splitLines(str);
            else return FrameNode.splitLines(str, parser);
        }
    }
}

