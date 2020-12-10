package api;

import java.util.*;

/**
 * This class implements the directed_weighted_graph interface, represents a directed weighted graph,
 * represented by a HashMap.
 */
public class DWGraph_DS implements directed_weighted_graph, java.io.Serializable{
    HashMap<Integer, node_data> graph;
    HashMap<String,edge_data> edgeList;
    private int mc;

    /**
     * default constructor
     */
    public DWGraph_DS(){
        graph= new HashMap<>();
        edgeList= new HashMap<>();
        this.mc= 0;
    }

    /**
     * A copy constructor.
     * This function creates a new HashMap and new nodes with the original keys from graph g.
     * After creating all the nodes the function is connecting all the edges that exist in graph g
     * with the same weight.
     * @param g
     */
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

    /**
     * This function allows easy access to the nodes in the graph.
     * @param key - the node_id
     * @return node_data(key).
     */
    @Override
    public node_data getNode(int key) {
        if(!graph.containsKey(key)) return null;
        return graph.get(key);
    }

    /**
     * This function returns the edge_data from the source node to the destination node in the graph.
     * @param src
     * @param dest
     * @return edge_data. null if there isn't an edge.
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if (hasEdge(src, dest)){
            return edgeList.get(src + "-" + dest);
        }
        return null;
    }

    /**
     * This function returns true if there's an edge from the source node to the destination node in the graph.
     * @param src
     * @param dest
     * @return True if connected, False if not connected.
     */
    public boolean hasEdge(int src, int dest){
        if(src==dest) return false;
        if(graph.containsKey(src) && graph.containsKey(dest)) {
            return ((NodeData)graph.get(src)).hasNi(dest) && ((NodeData)graph.get(dest)).hasEdgeFrom(src);
        }
        return false;
    }

    /**
     * This function adds a new node to the graph. If the node is already in the graph
     * the MC number stays the same.
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        int node_size = nodeSize();
        graph.put(n.getKey(), n);
        if (node_size != nodeSize()){  //if the nodeSize is the same- the node is already in the graph-
            mc++;                      // so mc wouldn't change
        }
    }

    /**
     * Connecting two nodes in the graph. If one of the nodes is not in the graph the function does nothing.
     * If there's already an edge the weight is updated. If the weight is the same the function does nothing.
     * @param src
     * @param dest
     * @param w - weight of the edge
     */
    @Override
    public void connect(int src, int dest, double w) {
        if(graph.containsKey(src) && graph.containsKey(dest) && src!=dest){
            if(!hasEdge(src, dest)){   //if there's an edge already- the number of edges doesn't change
                mc++;
            }
            else {
                if(getEdge(src,dest).getWeight() != w){ //if the edge's weight is the same- the number of mc doesn't change
                    mc++;
                }
            }

            ((NodeData)graph.get(src)).addNi(graph.get(dest));
            ((NodeData)graph.get(dest)).addEdgeFrom(graph.get(src));

            edgeData e= new edgeData(src,dest,w);
            edgeList.put(e.getId(),e);
        }
    }

    /**
     * This function returns a collection representing all the nodes in the graph.
     * @return Collection<node_data>.
     */
    @Override
    public Collection<node_data> getV() {
        return graph.values();
    }

    /**
     * This function returns a collection representing all the edges in the graph.
     * @return Collection<edge_data>.
     */
    public Collection<edge_data> getE(){
        return  edgeList.values();
    }

    /**
     * This function returns a collection of all the edges that start from the node_data(node_id) in the graph.
     * @return Collection<edge_data>.
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        Collection<edge_data> ans= new LinkedList<>();
        Iterator<edge_data> it= edgeList.values().iterator();
        while (it.hasNext()){
            edge_data temp= it.next();
            if(temp.getSrc()==node_id){
                ans.add(temp);
            }
        }
        return ans;
    }

    /**
     * This function is deleting the node (with the given ID) from the graph,
     * and removing all edges which start or end at this node.
     * @param key
     * @return the deleted node.
     */
    @Override
    public node_data removeNode(int key) {
        if (!graph.containsKey(key)) return null;
        node_data ans = getNode(key);

        Iterator<node_data> it = ((NodeData)graph.get(key)).getNi().iterator();
        while(it.hasNext()) {
            node_data temp = it.next();
            ((NodeData)temp).removeEdgeFrom(graph.get(key)); //removing the node from the list of parents
            edgeList.remove(key + "-" + temp.getKey());
            mc++;
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

    /**
     * This function is deleting the edge_data from the graph.
     * @param src
     * @param dest
     * @return the deleted edge.
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        if(hasEdge(src, dest)) {
            ((NodeData) graph.get(src)).removeNi(graph.get(dest));
            ((NodeData) graph.get(dest)).removeEdgeFrom(graph.get(src));
            edge_data ans = edgeList.get(src + "-" + dest);
            edgeList.remove(src + "-" + dest);
            mc++;
            return ans;
        }
        return null;
    }

    /**
     * @return the number of nodes in the graph.
     */
    @Override
    public int nodeSize() {
        return graph.size();
    }

    /**
     * @return the number of edges in the graph.
     */
    @Override
    public int edgeSize() {
        return edgeList.size();
    }

    @Override
    public int getMC() {
        return mc;
    }


    public String toString(){
        String temp= "Graph: "+graph;
        return temp;
    }
}
