package io.github.kusoroadeolu.clique.tree;

import io.github.kusoroadeolu.clique.config.TreeConfiguration;
import io.github.kusoroadeolu.clique.core.display.Borderless;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.github.kusoroadeolu.clique.core.utils.Constants.EMPTY;
import static io.github.kusoroadeolu.clique.core.utils.Constants.NEWLINE;

public class Tree implements Borderless {
    private final String label;
    private final List<Tree> children; //Accumulates children
    private final TreeConfiguration treeConfiguration;
    static final String CONNECTOR = "├─ ";
    static final String END_CONNECTOR = "└─ ";
    static final String SPACE = "   ";
    static final String CONNECTING_LINE = "│  ";
    private static final String RESET = "[/]";
    private Tree parent;

    public Tree(String label) {
        this(label, TreeConfiguration.DEFAULT, null);
    }

    public Tree(String label, TreeConfiguration treeConfiguration) {
        this(label, treeConfiguration, null);
    }

    private Tree(String label, TreeConfiguration treeConfiguration, Tree parent) {
        validateLabel(label);
        Objects.requireNonNull(treeConfiguration, "Tree configuration cannot be null");
        this.label = label;
        this.children = new ArrayList<>();
        this.treeConfiguration = treeConfiguration;
        this.parent = parent;
    }

    public Tree add(String label) {
        validateLabel(label);
        var child = new Tree(label, treeConfiguration, this);
        children.add(child);
        return child;
    }

    public Tree add(Tree tree) {
        Objects.requireNonNull(tree, "Tree cannot be null");
        assertNotSelf(tree);
        tree.parent = this;
        children.add(tree);
        return tree;
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

    void validateLabel(String label){
        Objects.requireNonNull(label, "Label cannot be null");
    }

    @Override
    public void flush() {;
        children.forEach(Tree::flush);
        parent = null;
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

    //For Tests
    List<Tree> children(){
        return children;
    }

    void assertNotSelf(Tree tree){
        if (tree == this) throw new UnsupportedOperationException("Cannot a tree within itself");
    }
}