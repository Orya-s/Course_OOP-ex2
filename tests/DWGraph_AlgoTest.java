package ex2.tests;

import ex2.src.api.*;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    @Test
    void init() {
        directed_weighted_graph g= graphCreator();
        dw_graph_algorithms ga= new DWGraph_Algo();
        ga.init(g);
        assertEquals(g,ga.getGraph());
    }

    @Test
    void getGraph() {
        directed_weighted_graph g= graphCreator();
        dw_graph_algorithms ga= new DWGraph_Algo();
        ga.init(g);
        assertEquals(g,ga.getGraph());

        g.addNode(new NodeData(12));
        assertEquals(g,ga.getGraph());
    }

    @Test
    void copy() {
        directed_weighted_graph g= graphCreator();
        dw_graph_algorithms ga= new DWGraph_Algo();
        ga.init(g);
        directed_weighted_graph newG= ga.copy();
        assertEquals(g.edgeSize(),newG.edgeSize());

        newG.removeEdge(3,2);
        assertNotEquals(g.edgeSize(),newG.edgeSize());
    }

    @Test
    void isConnected() {
        directed_weighted_graph g= graphCreator();
        dw_graph_algorithms ga= new DWGraph_Algo();
        ga.init(g);
        assertFalse(ga.isConnected());
        g.connect(6,10,2);
        assertTrue(ga.isConnected());
    }

    @Test
    void shortestPathDist() {
        directed_weighted_graph g= graphCreator();
        dw_graph_algorithms ga= new DWGraph_Algo();
        ga.init(g);
        assertEquals(28,ga.shortestPathDist(3,6));
        g.connect(3,6,27);
        assertEquals(27,ga.shortestPathDist(3,6));
    }

    @Test
    void shortestPath() {
        directed_weighted_graph g= graphCreator();
        dw_graph_algorithms ga= new DWGraph_Algo();
        ga.init(g);
        LinkedList<node_data> path= new LinkedList<>();
        path.add(g.getNode(3));
        path.add(g.getNode(7));
        path.add(g.getNode(8));
        path.add(g.getNode(0));
        path.add(g.getNode(4));
        path.add(g.getNode(5));
        path.add(g.getNode(6));
        assertEquals(path,ga.shortestPath(3,6));
    }

    @Test
    void save() {
        directed_weighted_graph g= graphCreator();
        dw_graph_algorithms ga= new DWGraph_Algo();
        ga.init(g);

        String str = "WDGraph.json";
        ga.save(str);
        ga.load("WDGraph");
        assertEquals(g,ga.getGraph());
    }

    @Test
    void load() {
        directed_weighted_graph g= graphCreator();
        dw_graph_algorithms ga= new DWGraph_Algo();
        ga.init(g);

        String str = "WDGraph.json";
        ga.save(str);
        ga.load("WDGraph");
        assertEquals(g,ga.getGraph());
    }


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
        g1.connect(n7.getKey(),n8.getKey(),9);
        g1.connect(n4.getKey(),n8.getKey(),10);
        g1.connect(n3.getKey(),n7.getKey(),6);
        g1.connect(n2.getKey(),n1.getKey(),4);
        g1.connect(n3.getKey(),n6.getKey(),30);
        g1.connect(n10.getKey(),n6.getKey(),2);

        return g1;
    }
}