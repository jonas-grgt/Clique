package io.github.kusoroadeolu.clique.tree;

import io.github.kusoroadeolu.clique.config.TreeConfiguration;
import io.github.kusoroadeolu.clique.core.display.Accumulated;

import java.util.ArrayList;
import java.util.List;

import static io.github.kusoroadeolu.clique.core.utils.Constants.EMPTY;
import static io.github.kusoroadeolu.clique.core.utils.Constants.NEWLINE;

public class Tree implements Accumulated{
    private final String label;
    private final List<Tree> children; //Accumulates children
    private final TreeConfiguration treeConfiguration;
    private static final String CONNECTOR = "├─ ";
    private static final String END_CONNECTOR = "└─ ";
    private static final String SPACE = "   ";
    private static final String CONNECTING_LINE = "│  ";
    private static final String RESET = "[/]";
    private final Tree parent;

    public Tree(String label) {
        this(label, TreeConfiguration.DEFAULT, null);
    }

    public Tree(String label, TreeConfiguration treeConfiguration) {
        this(label, treeConfiguration, null);
    }

    private Tree(String label, TreeConfiguration treeConfiguration, Tree parent) {
        this.label = label;
        this.children = new ArrayList<>();
        this.treeConfiguration = treeConfiguration;
        this.parent = parent;
    }

    public Tree add(String label) {
        var child = new Tree(label, treeConfiguration, this);
        children.add(child);
        return child;
    }

    public Tree parent(){
        return parent;
    }

    private void buildTree(Tree node, String prefix, boolean isLast, StringBuilder sb) {
        String connector = isLast ? END_CONNECTOR : CONNECTOR;
        String guideStyle = treeConfiguration.getGuideStyle();
        sb.append(guideStyle)
                .append(prefix)
                .append(connector)
                .append(RESET)
                .append(node.label)
                .append(RESET)
                .append(NEWLINE);

        var childPrefix = prefix + (isLast ? SPACE : CONNECTING_LINE);
        for (int i = 0; i < node.children.size(); i++) {
            boolean lastChild = i == (node.children.size() - 1);
            buildTree(node.children.get(i), childPrefix, lastChild, sb);
        }
    }

    @Override
    public void flush() {
        children.clear();
    }

    @Override
    public String get() {
        StringBuilder sb = new StringBuilder();
        sb.append(label)
                .append(RESET)
                .append(NEWLINE);
        for (int i = 0; i < children.size(); i++) {
            buildTree(children.get(i), EMPTY, i == children.size() - 1, sb);
        }

        var parser = treeConfiguration.getParser();
        return parser != null ? parser.parse(sb.toString()) : sb.toString();
    }
}