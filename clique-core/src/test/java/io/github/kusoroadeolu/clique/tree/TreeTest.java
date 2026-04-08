package io.github.kusoroadeolu.clique.tree;

import io.github.kusoroadeolu.clique.Clique;
import org.junit.jupiter.api.Test;

import static io.github.kusoroadeolu.clique.tree.Tree.CONNECTOR;
import static io.github.kusoroadeolu.clique.tree.Tree.END_CONNECTOR;
import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    @Test
    public void assertRoot_hasNoParent(){
        Tree tree = Clique.tree("Some label");
        assertTrue(tree.parent().isEmpty());
    }

    //Here the tree does not create a new child node
    @Test
    public void testManualTreeAddition(){
        Tree tree = Clique.tree("Some label");
        Tree child = Clique.tree("Child label");
        Tree returned = tree.add(child);
        assertSame(child, returned);
    }


    @Test
    public void treeNodeShouldReturnParent(){
        Tree tree = Clique.tree("Some label");
        Tree node = tree.add("Node");
        assertSame(tree, node.parent().get());
    }

    @Test
    public void treeShouldContainConnector(){
        Tree tree = Clique.tree("Some label");
        tree.add("Node");
        tree.add("Node");
        String str = tree.get();
        assertTrue(str.contains(CONNECTOR));
    }

    @Test
    public void treeShouldContainEndConnector(){
        Tree tree = Clique.tree("Some label");
        tree.add("Node");
        tree.add("Node");
        String str = tree.get();
        assertTrue(str.contains(END_CONNECTOR));
    }

    @Test
    public void treeShouldContainChild(){
        Tree tree = Clique.tree("Some label");
        Tree child = tree.add("Node");
        assertTrue(tree.children().contains(child));
    }

    @Test
    public void assertThrowsUnsupportedOperationExWhenTreeRefIsAddedToItself(){
        Tree tree = Clique.tree("Some label");
        assertThrows(UnsupportedOperationException.class, () -> tree.add(tree));
    }


}