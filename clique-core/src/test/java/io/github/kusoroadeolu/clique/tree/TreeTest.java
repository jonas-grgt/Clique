package io.github.kusoroadeolu.clique.tree;

import io.github.kusoroadeolu.clique.Clique;
import org.junit.jupiter.api.Test;

import static io.github.kusoroadeolu.clique.tree.Tree.CONNECTOR;
import static io.github.kusoroadeolu.clique.tree.Tree.END_CONNECTOR;
import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    @Test
    public void rootTreeShouldReturnNullParent(){
        Tree tree = Clique.tree("Some label");
        assertNull(tree.parent());
    }

    //Here the tree creates a new child node since we just passed a string
    @Test
    public void testTreeAddition(){
        Tree tree = Clique.tree("Some label");
        Tree child = tree.add("Some node");
        assertTrue(tree.children().contains(child));
        assertSame(child.parent(), tree);
    }

    //Here the tree does not create a new child node
    @Test
    public void testManualTreeAddition(){
        Tree tree = Clique.tree("Some label");
        Tree child = Clique.tree("Child label");
        Tree returned = tree.add(child);
        assertTrue(tree.children().contains(child));
        assertSame(child, returned);
    }


    @Test
    public void treeNodeShouldReturnParent(){
        Tree tree = Clique.tree("Some label");
        Tree node = tree.add("Node");
        assertSame(tree, node.parent());
    }

    @Test
    public void treeShouldContainBoth(){
        Tree tree = Clique.tree("Some label");
        tree.add("Node");
        tree.add("Node1");
        String str = tree.get();
        assertTrue(str.contains(END_CONNECTOR));
        assertTrue(str.contains(CONNECTOR));
    }

    @Test
    public void treeShouldContainAllChildren(){
        Tree tree = Clique.tree("Some label");
        Tree child = tree.add("Node");
        Tree child1 = tree.add("Node1");
        assertTrue(tree.children().contains(child));
        assertTrue(tree.children().contains(child1));
    }

    @Test
    public void onFlush_shouldUnlinkChildren(){
        Tree tree = Clique.tree("Some label");
        Tree child = tree.add("Node");
        Tree child1 = tree.add("Node1");
        assertTrue(tree.children().contains(child));
        assertTrue(tree.children().contains(child1));

        tree.flush();
        assertTrue(child.children().isEmpty());
        assertNull(child.parent());
        assertNull(child1.parent());

    }
}