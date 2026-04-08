package io.github.kusoroadeolu.clique.components;

import io.github.kusoroadeolu.clique.configuration.TreeConfiguration;
import io.github.kusoroadeolu.clique.internal.documentation.Stable;
import io.github.kusoroadeolu.clique.internal.utils.StringUtils;
import io.github.kusoroadeolu.clique.spi.AnsiCode;
import io.github.kusoroadeolu.clique.style.StyleBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.github.kusoroadeolu.clique.internal.Constants.EMPTY;
import static io.github.kusoroadeolu.clique.internal.Constants.NEWLINE;

@Stable(since = "3.1.0")
public class Tree implements Component {
    private final String label;
    private final List<Tree> children; //Accumulates children
    private final TreeConfiguration treeConfiguration;
    static final String CONNECTOR = "├─ ";
    static final String END_CONNECTOR = "└─ ";
    static final String SPACE = "   ";
    static final String CONNECTING_LINE = "│  ";
    private final AnsiCode[] connectorColor;
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
        this.connectorColor = treeConfiguration.getConnectorColor();
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

    public Optional<Tree> parent(){
        return Optional.ofNullable(parent);
    }

    private void buildTree(Tree node, String prefix, boolean isLast, StyleBuilder sb) {
        String connector = isLast ? END_CONNECTOR : CONNECTOR;
        sb.stack(prefix, this.connectorColor)
                .append(connector)
                .append(node.label)
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
    public String get() {
        var sb = new StyleBuilder();
        sb.append(label).append(NEWLINE);
        for (int i = 0; i < children.size(); i++) {
            buildTree(children.get(i), EMPTY, i == children.size() - 1, sb);
        }

        var parser = treeConfiguration.getParser();
        return StringUtils.parseIfPresent(sb.toString(), parser);
    }

    //For Tests
    List<Tree> children(){
        return children;
    }

    void assertNotSelf(Tree tree){
        if (tree == this) throw new UnsupportedOperationException("Cannot nest a tree within itself");
    }
}