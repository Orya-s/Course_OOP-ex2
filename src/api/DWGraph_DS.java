package ex2.src.api;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph{
    HashMap<Integer, NodeData> graph;
    HashMap<String,edge_data> edgeList;

    private int edges, mc;

    public DWGraph_DS(){
        graph= new HashMap<>();
        this.edges= 0;
        this.mc= 0;
    }

    @Override
    public node_data getNode(int key) {
        if(!graph.containsKey(key)) return null;
        return graph.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        if(graph.containsKey(src) && graph.containsKey(dest)){
            if (hasEdge(src, dest)){
                return edgeList.get(src + "-" + dest);
            }
        }
        return null;
    }

    public boolean hasEdge(int src, int dest){
        return graph.get(src).hasNi(dest) && graph.get(src).hasEdgeFrom(dest);
    }

    @Override
    public void addNode(node_data n) {

    }

    @Override
    public void connect(int src, int dest, double w) {

    }

    @Override
    public Collection<node_data> getV() {
        return null;
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        return null;
    }

    @Override
    public node_data removeNode(int key) {
        return null;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        return null;
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }
}
