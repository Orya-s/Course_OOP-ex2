import api.*;
import gameClient.CL_Agent;
import gameClient.CL_Pokemon;
import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CL_AgentTest {


    @Test
    void getSrcNode() {
        directed_weighted_graph graph= graphCreator();
        node_data node= graph.getNode(1);
        CL_Agent agent= new CL_Agent(graph,node.getKey());

        assertEquals(1,agent.getSrcNode());
    }


    @Test
    void setNextNode() {
        directed_weighted_graph graph= graphCreator();
        node_data node= graph.getNode(1);
        CL_Agent agent= new CL_Agent(graph,node.getKey());

        graph.connect(node.getKey(),2,1);

        assertTrue(agent.setNextNode(2));

        graph.removeEdge(node.getKey(),2);

        assertFalse(agent.setNextNode(2));
    }

    @Test
    void setCurrNode() {
        directed_weighted_graph graph= graphCreator();
        node_data node= graph.getNode(1);
        CL_Agent agent= new CL_Agent(graph,node.getKey());

        node_data node2= graph.getNode(3);
        agent.setCurrNode(node2.getKey());

        assertEquals(3,agent.getSrcNode());
    }

    @Test
    void isMoving() {
        directed_weighted_graph graph= graphCreator();
        node_data node= graph.getNode(1);
        CL_Agent agent= new CL_Agent(graph,node.getKey());

        assertNotNull(agent.isMoving());
    }

    @Test
    void getID() {
        directed_weighted_graph graph= graphCreator();
        node_data node= graph.getNode(1);
        CL_Agent agent= new CL_Agent(graph,node.getKey());

        assertEquals(-1,agent.getID());
    }

    @Test
    void getLocation() {
        directed_weighted_graph graph= graphCreator();
        node_data node= graph.getNode(1);
        CL_Agent agent= new CL_Agent(graph,node.getKey());

        assertEquals(node.getLocation(),agent.getLocation());
    }

    @Test
    void getValue() {
        directed_weighted_graph graph= graphCreator();
        node_data node= graph.getNode(1);
        CL_Agent agent= new CL_Agent(graph,node.getKey());

        assertEquals(0,agent.getValue());
    }

    @Test
    void getNextNode() {
        directed_weighted_graph graph= graphCreator();
        node_data node= graph.getNode(1);
        CL_Agent agent= new CL_Agent(graph,node.getKey());
        agent.setNextNode(2);
        edge_data temp= agent.get_curr_edge();

        assertEquals(temp.getDest(),agent.getNextNode());
    }

    @Test
    void getSpeed() {
        directed_weighted_graph graph= graphCreator();
        node_data node= graph.getNode(1);
        CL_Agent agent= new CL_Agent(graph,node.getKey());

        assertEquals(0,agent.getSpeed());
    }

    @Test
    void setSpeed() {
        directed_weighted_graph graph= graphCreator();
        node_data node= graph.getNode(1);
        CL_Agent agent= new CL_Agent(graph,node.getKey());
        assertEquals(0,agent.getSpeed());

        agent.setSpeed(2);
        assertEquals(2,agent.getSpeed());
    }

    @Test
    void get_curr_edge() {
        directed_weighted_graph graph= graphCreator();
        node_data node= graph.getNode(1);
        CL_Agent agent= new CL_Agent(graph,node.getKey());
        agent.setNextNode(2);
        edge_data temp= graph.getEdge(1,2);

        assertEquals(temp,agent.get_curr_edge());
    }

    private directed_weighted_graph graphCreator(){
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