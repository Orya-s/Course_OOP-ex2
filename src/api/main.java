package api;
import com.google.gson.Gson;
import api.*;
public class main {
    public static void main(String[] args) {
        NodeData n0= new NodeData();
        NodeData n1= new NodeData();
        NodeData n2= new NodeData();
        NodeData n3= new NodeData();
        NodeData n4= new NodeData();
        NodeData n5= new NodeData();
        NodeData n6= new NodeData();
        NodeData n7= new NodeData();
        NodeData n8= new NodeData();
        NodeData n9= new NodeData();


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


        dw_graph_algorithms ga= new DWGraph_Algo();
        ga.init(g1);

        System.out.println(ga.shortestPathDist(n1.getKey(),n2.getKey()) + "     SHOULD PRINT- -1.0");

        g1.connect(n1.getKey(),n2.getKey(),3.0);
        g1.connect(n1.getKey(),n3.getKey(),1);
        g1.connect(n3.getKey(),n2.getKey(),1);
        System.out.println(ga.shortestPathDist(n1.getKey(),n2.getKey()) + "     SHOULD PRINT- 2.0");
        System.out.println(ga.isConnected() + "     SHOULD PRINT- False");

        g1.connect(n0.getKey(),n2.getKey(),4.5);
        g1.connect(n0.getKey(),n4.getKey(),1);
        g1.connect(n4.getKey(),n5.getKey(),0.5);
        g1.connect(n5.getKey(),n6.getKey(),8);
        g1.connect(n7.getKey(),n1.getKey(),0.5);
        g1.connect(n8.getKey(),n0.getKey(),3.5);
        g1.connect(n9.getKey(),n5.getKey(),11);
        System.out.println(ga.isConnected() + "     SHOULD PRINT- False");


        g1.connect(n6.getKey(),n9.getKey(),5);
        g1.connect(n9.getKey(),n4.getKey(),9);
        g1.connect(n4.getKey(),n8.getKey(),10);
        g1.connect(n3.getKey(),n7.getKey(),6);
        g1.connect(n2.getKey(),n1.getKey(),4);
        g1.connect(n3.getKey(),n6.getKey(),30);
        System.out.println(ga.isConnected() + "     SHOULD PRINT- True");

        g1.connect(n7.getKey(),n8.getKey(),9);
        System.out.println(ga.isConnected() + "     SHOULD PRINT- True");

        System.out.println(ga.shortestPathDist(n3.getKey(),n6.getKey()) + "     SHOULD PRINT- 28.0");
        System.out.println(ga.shortestPathDist(n1.getKey(),11) + "     SHOULD PRINT- -1.0");

        System.out.println(ga.shortestPath(7,2) + "     SHOULD PRINT- [Node- Key:7, Node- Key:1, Node- Key:3, Node- Key:2]");
        System.out.println(ga.shortestPath(3,6) + "     SHOULD PRINT- [Node- Key:3, Node- Key:7, Node- Key:8, Node- Key:0, Node- Key:4, Node- Key:5, Node- Key:6]");
        System.out.println(ga.shortestPath(3,3) + "     SHOULD PRINT- [Node- Key:3]");
        System.out.println(ga.shortestPath(3,11) + "     SHOULD PRINT- null");

        node_data n10= new NodeData();
        g1.addNode(n10);
        g1.connect(10,6,2);
        System.out.println(ga.shortestPath(1,10) + "     SHOULD PRINT- null");

        System.out.println("Edges list: "+((DWGraph_DS)g1).getE());
        ga.save("DWGraph_DS.json");
        System.out.println(ga.load("DWGraph_DS.json"));
        System.out.println(ga.getGraph());
        System.out.println(ga.getGraph().getNode(0).getLocation());
        System.out.println("Edges list: "+((DWGraph_DS)(ga.getGraph())).getE());
















    }

}
