package ex2.src.api;
import ex2.src.api.*;
public class main {
    public static void main(String[] args) {
        NodeData n1= new NodeData();
        NodeData n2= new NodeData();
        NodeData n3= new NodeData();
        NodeData n4= new NodeData();
        NodeData n5= new NodeData();

        directed_weighted_graph g1= new DWGraph_DS();
        g1.addNode(n1);
        g1.addNode(n2);
        g1.addNode(n3);
        g1.addNode(n4);
        g1.addNode(n5);

        dw_graph_algorithms ga= new DWGraph_Algo();
        ga.init(g1);

        System.out.println(ga.shortestPathDist(n1.getKey(),n2.getKey()));
        System.out.println(n2.getKey());

        g1.connect(n1.getKey(),n2.getKey(),3.0);


        edgeData edge= new edgeData(n1.getKey(),n2.getKey(),3);
        g1.connect(n1.getKey(),n2.getKey(),3.0);
        g1.connect(n1.getKey(),n3.getKey(),1);
        g1.connect(n3.getKey(),n2.getKey(),1);


        System.out.println("answer:   "+ga.shortestPathDist(n1.getKey(),n2.getKey()));








    }

}
