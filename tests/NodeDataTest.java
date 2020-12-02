package ex2.tests;

import ex2.src.api.GeoLocation;
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
        NodeData n = new NodeData();
        GeoLocation loc= new GeoLocation();
        n.setLocation(loc);
        assertEquals(0.0,n.getLocation().x());
        assertEquals(0.0,n.getLocation().y());
        assertEquals(0.0,n.getLocation().z());
    }

    @Test
    void setLocation() {
        NodeData n = new NodeData();
        GeoLocation loc= new GeoLocation();
        n.setLocation(loc);
        assertEquals(0.0,n.getLocation().x());
        assertEquals(0.0,n.getLocation().y());
        assertEquals(0.0,n.getLocation().z());

        GeoLocation loc1= new GeoLocation(1,2,3);
        n.setLocation(loc1);
        assertEquals(1.0,n.getLocation().x());
        assertEquals(2.0,n.getLocation().y());
        assertEquals(3.0,n.getLocation().z());
    }

    @Test
    void getWeight() {
        NodeData n = new NodeData();
        assertEquals(0.0,n.getWeight());
    }

    @Test
    void setWeight() {
        NodeData n = new NodeData();
        assertEquals(0.0,n.getWeight());

        n.setWeight(1.1);
        assertEquals(1.1,n.getWeight());
    }

    @Test
    void getInfo() {
        NodeData n = new NodeData();
        assertNull(n.getInfo());
        n.setInfo("A");
        assertEquals("A",n.getInfo());
    }

    @Test
    void setInfo() {
        NodeData n = new NodeData();
        assertNull(n.getInfo());
        n.setInfo("A");
        assertEquals("A",n.getInfo());
    }

    @Test
    void getTag() {
        NodeData n = new NodeData();
        assertEquals(0,n.getTag());
        n.setTag(1);
        assertEquals(1,n.getTag());
    }

    @Test
    void setTag() {
        NodeData n = new NodeData();
        assertEquals(0,n.getTag());
        n.setTag(1);
        assertEquals(1,n.getTag());
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