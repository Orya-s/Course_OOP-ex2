import api.*;
import gameClient.Arena;
import gameClient.CL_Agent;
import gameClient.CL_Pokemon;
import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArenaTest {

    @Test
    void setAndGetPokemons() {
        List<CL_Pokemon> pokemons= new LinkedList<>();
        edge_data _edge= new edgeData(1,2,3.5);
        Point3D point= new Point3D(1,2,3);
        CL_Pokemon pokemon= new CL_Pokemon(point,1,2.5,3.5,_edge);

        edge_data _edge2= new edgeData(1,2,3.5);
        Point3D point2= new Point3D(1,2,3);
        CL_Pokemon pokemon2= new CL_Pokemon(point2,1,2.5,3.5,_edge2);

        edge_data _edge3= new edgeData(1,2,3.5);
        Point3D point3= new Point3D(1,2,3);
        CL_Pokemon pokemon3= new CL_Pokemon(point3,1,2.5,3.5,_edge3);

        pokemons.add(pokemon);
        pokemons.add(pokemon2);
        pokemons.add(pokemon3);

        Arena ar= new Arena();
        ar.setPokemons(pokemons);

        assertEquals(pokemons,ar.getPokemons());
    }

    @Test
    void setAndGetAgents() {
        directed_weighted_graph graph= graphCreator();
        List<CL_Agent> agents= new LinkedList<>();

        node_data node= graph.getNode(1);
        CL_Agent agent= new CL_Agent(graph,node.getKey());

        node_data node2= graph.getNode(2);
        CL_Agent agent2= new CL_Agent(graph,node2.getKey());

        node_data node3= graph.getNode(3);
        CL_Agent agent3= new CL_Agent(graph,node3.getKey());

        agents.add(agent);
        agents.add(agent2);
        agents.add(agent3);

        Arena ar= new Arena();
        ar.setAgents(agents);

        assertEquals(agents,ar.getAgents());
    }

    @Test
    void setAndGetGraph() {
        Arena ar= new Arena();
        directed_weighted_graph graph= graphCreator();
        System.out.println(graph.getEdge(1,2).getWeight());
        ar.setGraph(graph);
        assertEquals(graph,ar.getGraph());
    }

    @Test
    void setAndGet_info() {
        Arena ar= new Arena();
        directed_weighted_graph graph= graphCreator();
        ar.setGraph(graph);
        List<String> list= new LinkedList<>();
        list.add(graph.getEdge(1,2).toString());
        list.add(graph.getEdge(4,5).toString());
        list.add(graph.getEdge(3,2).toString());
        ar.set_info(list);
        assertEquals(list,ar.get_info());
    }

    @Test
    void updateEdge() {
        Arena ar= new Arena();
        directed_weighted_graph graph= graphCreator();
        ar.setGraph(graph);
        List<CL_Pokemon> pokemons= new LinkedList<>();

        edge_data _edge= new edgeData(1,2,3.5);
        Point3D point= new Point3D(1,2,3);
        CL_Pokemon pokemon= new CL_Pokemon(point,1,2.5,3.5,_edge);
        edge_data _edge2= new edgeData(1,2,3.5);
        Point3D point2= new Point3D(1,2,3);
        CL_Pokemon pokemon2= new CL_Pokemon(point2,1,2.5,3.5,_edge2);
        pokemons.add(pokemon);
        pokemons.add(pokemon2);
        ar.setPokemons(pokemons);
        Arena.updateEdge(pokemon,graph);
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