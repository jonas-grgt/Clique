package io.github.kusoroadeolu.clique.frame;

import io.github.kusoroadeolu.clique.config.FrameAlign;
import io.github.kusoroadeolu.clique.core.display.Component;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.core.structures.Cell;
import io.github.kusoroadeolu.clique.parser.AnsiStringParser;

import java.util.List;

import static io.github.kusoroadeolu.clique.core.utils.Constants.ZERO;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.parseToCell;
import static io.github.kusoroadeolu.clique.parser.AnsiStringParser.DEFAULT;

@InternalApi(since = "3.1.3")
sealed interface FrameNode permits FrameNode.StringNode, FrameNode.ComponentNode {
    List<Cell> lines();
    int maxWidth();
    FrameAlign align();

    static int findMaxWidth(List<Cell> lines) {
        return lines
                .stream()
                .mapToInt(Cell::width)
                .max()
                .orElse(ZERO);
    }

    static List<Cell> splitComponentLines(String str){
        return str.lines().map(s -> parseToCell(s, DEFAULT)).toList(); //Original to styled string for components, we actually need to parse here with a default parser
    }

    //For raw strings, we need to handle the case in which the string has markup, however for components, when we call the get method, they apply their markup so it's good
    static List<Cell> splitLines(String str, AnsiStringParser parser){
        return str.lines().map(s -> parseToCell(s, parser)).toList();
    }


    non-sealed class ComponentNode implements FrameNode {
        private final Component component;
        private final FrameAlign align;

        public ComponentNode(Component component, FrameAlign align) {
            this.component = component;
            this.align = align;
        }

        @Override
        public List<Cell> lines() {
            return splitComponentLines(component.get()); // resolved at render time
        }

        @Override
        public int maxWidth() {
            return findMaxWidth(lines());
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
            this.lines = FrameNode.splitLines(str, parser);
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
    }
}

