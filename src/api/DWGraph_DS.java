package ex2.src.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class DWGraph_DS implements directed_weighted_graph{
    HashMap<Integer, node_data> graph;
    HashMap<String,edge_data> edgeList;
    private int mc;

    public DWGraph_DS(){
        graph= new HashMap<>();
        this.mc= 0;
    }

    public DWGraph_DS(DWGraph_DS g){
        this.graph= new HashMap<>();
        this.edgeList= new HashMap<>();

        for(node_data n: g.getV()) {   //Building new nodes for the new graph, keeping the keys
            NodeData temp= new NodeData(n.getKey());
            temp.setTag(n.getTag());
            temp.setInfo(n.getInfo());
            this.graph.put(temp.getKey(), temp);
        }

        for(node_data n: g.getV()) {   //Setting the list of neighbors for each node
            for (node_data neighbor: ((NodeData)n).getNi()){
                double w= g.edgeList.get(n.getKey() + "-" + neighbor.getKey()).getWeight();
                connect(n.getKey(), neighbor.getKey(), w);
            }
        }

        this.mc= g.mc;
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
                mc++;
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
        if (!graph.containsKey(key)) return null;
        node_data ans = getNode(key);

        Iterator<node_data> it = ((NodeData)graph.get(key)).getNi().iterator();
        while(it.hasNext()) {
            node_data temp = it.next();
            ((NodeData)temp).removeEdgeFrom(graph.get(key)); //removing the node from the list of parents
            edgeList.remove(key + "-" + temp.getKey());
        }

        Iterator<node_data> it2 = ((NodeData)graph.get(key)).getParents().iterator();
        while(it.hasNext()) {
            node_data temp = it2.next();
            ((NodeData)temp).removeNi(graph.get(key)); //removing the node from the list of neighbors
            edgeList.remove(temp.getKey() + "-" + key);
        }

        graph.remove(key, getNode(key));
        mc++;
        return ans;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        if(hasEdge(src, dest)) {
            ((NodeData) graph.get(src)).removeNi(graph.get(dest));
            ((NodeData) graph.get(dest)).removeEdgeFrom(graph.get(src));
            edge_data ans = edgeList.get(src + "-" + dest);
            edgeList.remove(ans);
            mc++;
            return ans;
        }
        return null;
    }

    @Override
    public int nodeSize() {
        return graph.size();
    }

    @Override
    public int edgeSize() {
        return edgeList.size();
    }

    @Override
    public int getMC() {
        return mc;
    }
}
