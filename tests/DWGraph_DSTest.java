package ex2.tests;

import ex2.src.api.DWGraph_DS;
import ex2.src.api.NodeData;
import ex2.src.api.directed_weighted_graph;
import ex2.src.api.node_data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {

    public directed_weighted_graph graphCreator(){
        node_data n0= new NodeData(0);
        node_data n1= new NodeData(1);
        node_data n2= new NodeData(2);
        node_data n3= new NodeData(3);
        node_data n4= new NodeData(4);
        node_data n5= new NodeData(5);
        node_data n6= new NodeData(6);
        node_data n7= new NodeData(7);
        node_data n8= new NodeData(8);
        node_data n9= new NodeData(9);
        node_data n10= new NodeData(10);

        directed_weighted_graph g1= new DWGraph_DS();
        g1.addNode(n0);
        g1.addNode(n1);
        g1.addNode(n2);
        g1.addNode(n3);
        g1.addNode(n4);
        g1.addNode(n5);
        g1.addNode(n6);
        g1.addNode(n7);
        g1.addNode(n8);
        g1.addNode(n9);
        g1.addNode(n10);

        g1.connect(n1.getKey(),n2.getKey(),3.0);
        g1.connect(n1.getKey(),n3.getKey(),1);
        g1.connect(n3.getKey(),n2.getKey(),1);
        g1.connect(n0.getKey(),n2.getKey(),4.5);
        g1.connect(n0.getKey(),n4.getKey(),1);
        g1.connect(n4.getKey(),n5.getKey(),0.5);
        g1.connect(n5.getKey(),n6.getKey(),8);
        g1.connect(n7.getKey(),n1.getKey(),0.5);
        g1.connect(n8.getKey(),n0.getKey(),3.5);
        g1.connect(n9.getKey(),n5.getKey(),11);
        g1.connect(n6.getKey(),n9.getKey(),5);
        g1.connect(n9.getKey(),n4.getKey(),9);
        g1.connect(n4.getKey(),n8.getKey(),10);
        g1.connect(n3.getKey(),n7.getKey(),6);
        g1.connect(n2.getKey(),n1.getKey(),4);
        g1.connect(n3.getKey(),n6.getKey(),30);
        g1.connect(n10.getKey(),n6.getKey(),2);

        return g1;
    }

    @Test
    void getNode() {
        directed_weighted_graph g= graphCreator();
        node_data n= g.getNode(0);
        assertEquals(0,n.getKey());
    }

    @Test
    void getEdge() {
        directed_weighted_graph ga= graphCreator();
        assertEquals(9.0,ga.getEdge(9,4).getWeight());
    }

    @Test
    void hasEdge() {
        DWGraph_DS g= (DWGraph_DS) graphCreator();
        assertTrue(g.hasEdge(4,5));
        assertFalse(g.hasEdge(8,4));
    }

    @Test
    void addNode() {
        DWGraph_DS g= (DWGraph_DS) graphCreator();
        node_data n= new NodeData(12);
        g.addNode(n);
        assertEquals(12,g.getNode(12).getKey());

        assertNull(g.getNode(13));
    }

    @Test
    void connect() {
        DWGraph_DS g= (DWGraph_DS) graphCreator();
        g.connect(2,3,4.5);
        assertTrue(g.hasEdge(2,3));
        assertEquals(4.5,g.getEdge(2,3).getWeight());
        g.connect(2,3,5);
        assertEquals(5,g.getEdge(2,3).getWeight());
    }

    @Test
    void getV() {
        DWGraph_DS g= (DWGraph_DS) graphCreator();
        assertEquals(11,g.getV().size());
    }

    @Test
    void getE() {
        DWGraph_DS g= (DWGraph_DS) graphCreator();
        assertEquals(17,g.getE().size());
        g.removeEdge(3,2);
        assertEquals(16,g.getE().size());
    }

    @Test
    void removeNode() {
        DWGraph_DS g= (DWGraph_DS) graphCreator();
        g.removeNode(9);
        assertEquals(10,g.nodeSize());
        assertEquals(15,g.edgeSize());

        g.removeNode(9);
        assertEquals(10,g.nodeSize());
    }

    @Test
    void removeEdge() {
        DWGraph_DS g= (DWGraph_DS) graphCreator();
        g.removeEdge(3,2);
        assertEquals(16,g.edgeSize());
        g.removeEdge(3,2);
        assertEquals(16,g.edgeSize());
        assertNull(g.removeEdge(3,2));
    }

    @Test
    void nodeSize() {
        DWGraph_DS g= (DWGraph_DS) graphCreator();
        g.removeNode(9);
        assertEquals(10,g.nodeSize());
        g.removeNode(9);
        assertEquals(10,g.nodeSize());
        g.addNode(new NodeData(12));
        assertEquals(11,g.nodeSize());
    }

    @Test
    void edgeSize() {
        DWGraph_DS g= (DWGraph_DS) graphCreator();
        assertEquals(17,g.edgeSize());
        g.removeEdge(3,2);
        assertEquals(16,g.edgeSize());
        g.removeEdge(3,2);
        assertEquals(16,g.edgeSize());
        g.connect(2,3,0.5);
        assertEquals(17,g.edgeSize());
        g.connect(2,3,0.5);
        assertEquals(17,g.edgeSize());
    }

    @Test
    void getMC() {
        DWGraph_DS g= (DWGraph_DS) graphCreator();
        assertEquals(28,g.getMC());
        g.removeEdge(3,2);
        assertEquals(29,g.getMC());
        g.removeEdge(3,2);
        assertEquals(29,g.getMC());
        g.removeNode(10);
        assertEquals(31,g.getMC());
    }
    
}