package ex2.tests;

import ex2.src.api.NodeData;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class NodeDataTest {

    @Test
    void getNi() {
        NodeData n = new NodeData();
        NodeData n1 = new NodeData();
        n.addNi(n1);
        assertEquals(1, n.getNi().size());

        n.removeNi(n1);
        assertEquals(0, n.getNi().size());
    }

    @Test
    void hasNi() {
        NodeData n = new NodeData();
        NodeData n1 = new NodeData();
        n.addNi(n1);
        assertTrue(n.hasNi(n1.getKey()));

        n.removeNi(n1);
        assertFalse(n.hasNi(n1.getKey()));
    }

    @Test
    void addNi() {
        NodeData n = new NodeData();
        NodeData n1 = new NodeData();
        n.addNi(n1);
        n.addNi(n1);
        assertEquals(1, n.getNi().size());
    }

    @Test
    void addEdgeFrom() {
        NodeData n = new NodeData();
        NodeData n1 = new NodeData();
        n1.addEdgeFrom(n);
        assertTrue(n1.hasEdgeFrom(n.getKey()));
    }

    @Test
    void removeNi() {
        NodeData n = new NodeData();
        NodeData n1 = new NodeData();
        n.addNi(n1);
        assertTrue(n.hasNi(n1.getKey()));

        n.removeNi(n1);
        assertFalse(n.hasNi(n1.getKey()));

        n.removeNi(n1);
        assertFalse(n.hasNi(n1.getKey()));
    }

    @Test
    void removeEdgeFrom() {
        NodeData n = new NodeData();
        NodeData n1 = new NodeData();
        n1.addEdgeFrom(n);
        assertTrue(n1.hasEdgeFrom(n.getKey()));

        n1.removeEdgeFrom(n);
        assertFalse(n1.hasEdgeFrom(n.getKey()));

        n1.removeEdgeFrom(n);
        assertFalse(n1.hasEdgeFrom(n.getKey()));
    }

    @Test
    void getKey() {
        NodeData n = new NodeData();
        assertEquals(0,n.getKey());
    }

    @Test
    void getLocation() {
    }

    @Test
    void setLocation() {
    }

    @Test
    void getWeight() {

    }

    @Test
    void setWeight() {

    }

    @Test
    void getInfo() {
    }

    @Test
    void setInfo() {
    }

    @Test
    void getTag() {
    }

    @Test
    void setTag() {
    }

    @Test
    void getParents() {
        NodeData n = new NodeData();
        NodeData n1 = new NodeData();
        n1.addEdgeFrom(n);
        assertTrue(n1.hasEdgeFrom(n.getKey()));
        assertEquals(1,n1.getParents().size());
    }

    @Test
    void hasEdgeFrom() {
        NodeData n = new NodeData();
        NodeData n1 = new NodeData();
        n1.addEdgeFrom(n);
        assertTrue(n1.hasEdgeFrom(n.getKey()));

        n1.removeEdgeFrom(n);
        assertFalse(n1.hasEdgeFrom(n.getKey()));
    }

}