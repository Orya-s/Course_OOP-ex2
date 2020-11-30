package ex2.src.api;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph{
    HashMap<Integer, node_data> graph;
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
        if (hasEdge(src, dest)){
            return edgeList.get(src + "-" + dest);
        }
        return null;
    }

    public boolean hasEdge(int src, int dest){
        if(src==dest) return false;
        if(graph.containsKey(src) && graph.containsKey(dest)) {
            return ((NodeData)graph.get(src)).hasNi(dest) && ((NodeData)graph.get(src)).hasEdgeFrom(dest);
        }
        return false;
    }

    @Override
    public void addNode(node_data n) {
        int node_size = nodeSize();
        graph.put(n.getKey(), n);
        if (node_size != nodeSize()){  //if the nodeSize is the same- the node is already int the graph-
            mc++;                      // so mc shouldn't change
        }
    }

    @Override
    public void connect(int src, int dest, double w) {
        if(graph.containsKey(src) && graph.containsKey(dest) && src!=dest){
            if(!hasEdge(src, dest)){    //if there's an edge already- the number of edges doesn't change
                edges++; mc++;
            }
            else {

                if(getEdge(src, dest).getWeight() != w){  //if the edge's weight is the same- the number of mc doesn't change
                    mc++;
                }
            }

            ((NodeData)graph.get(src)).addNi(graph.get(dest));
            ((NodeData)graph.get(dest)).addEdgeFrom(graph.get(src));

            edgeData e= new edgeData(src,dest,w);
            edgeList.put(e.getId(),e);
        }
    }

    @Override
    public Collection<node_data> getV() {
        return graph.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        return edgeList.values();
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
