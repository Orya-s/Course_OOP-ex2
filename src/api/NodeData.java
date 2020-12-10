package api;

import java.util.Collection;
import java.util.HashMap;

/**
 * This class implements the node_data interface, represents a NodeData(vertex) in a directed weighted graph:
 * Each node has a unique ID, Tag, String of info, and two HashMap<> of neighbors (edges "coming" to the node,
 * and edges "leaving" node).
 */
public class NodeData implements node_data, java.io.Serializable {

    private int key, tag;
    private String info;
    private geo_location location;
    private double weight;
    private static int counter;
    private HashMap<Integer, node_data> toEdge;  //the list of edges coming out from this node
    private HashMap<Integer, node_data> fromEdge;  //the list of edges coming into this node

    /**
     * default constructor
     */
    public NodeData(){
        key= counter();
        toEdge= new HashMap<>();
        fromEdge= new HashMap<>();
        location= new GeoLocation(0,0,0);
    }

    private int counter(){
        return counter++;
    }

    /**
     * Constructor, creates NodeData from given key.
     * @param key Integer representing the ID of created node.
     */
    public NodeData(int key){
        this.key= key;
        toEdge= new HashMap<>();
        fromEdge= new HashMap<>();
        location= new GeoLocation();
    }

    /**
     * This function returns a collection of node_data representing the nodes that selected node connects to.
     * @return collection<node_data> toEdge
     */
    public Collection<node_data> getNi() {
        return toEdge.values();
    }

    /**
     * This function returns a collection of node_data representing the nodes that contain selected node in their toEdge collections.
     * @return collection<node_data> fromEdge
     */
    public Collection<node_data> getParents() {
        return fromEdge.values();
    }

    /**
     * This function returns whether the selected node has an edge to the node_data with the given key.
     * @param key - int representing the ID of the node
     * @return returns true if the node has an edge "leaving" it to node_data(key), and false if it does not.
     */
    public boolean hasNi(int key) {
        return toEdge.containsKey(key);
    }

    /**
     * This function connects an edge from this node to node t, by adding it to the collection of
     * nodes leaving this node (toEdge). (if node is already in collection- function will not add).
     * @param t - the node_data that will be added to the collection of nodes this node is connected to.
     */
    public void addNi(node_data t) {
        toEdge.put(t.getKey(),t);
    }

    /**
     * This function connects a selected node_data to this node by adding the "parent node" to the collection
     * of the node's parents (fromEdge).
     * (if node is already in collection- function will not add).
     * @param t - the node_data that will be added to the collection of nodes connected by an edge to operated node.
     */
    public void addEdgeFrom(node_data t){
        fromEdge.put(t.getKey(),t);
    }

    /**
     * This function removes the edge from this node to node_data node, by removing it from the collection of
     * nodes leaving this node (toEdge). (if node is not in the collection- nothing happens).
     * @param node - the node_data that will be removed from the collection of nodes this node is connected to.
     */
    public void removeNi(node_data node) {
        toEdge.remove(node.getKey(),node);
    }

    /**
     * This function removes the edge from the selected node_data to this node by removing the "parent node"
     * from the "parents" collection of this node (fromEdge).
     * (if node is not in the collection- nothing happens).
     * @param node - node_data that will be removed from the collection of nodes connected with an edge to this node.
     */
    public void removeEdgeFrom(node_data node){
        fromEdge.remove(node.getKey(),node);
    }

    /**
     * This function returns whether the selected node_data(srcKey) has an edge to this node.
     * @param srcKey - int representing the ID of the node
     * @return returns true if the node_data has an edge to this node, and false if it does not.
     */
    public boolean hasEdgeFrom(int srcKey){
        return fromEdge.containsKey(srcKey);
    }

    /**
     * This function returns the "key" of node(note: each node has a unique "key")
     * @return int key
     */
    @Override
    public int getKey() {
        return key;
    }

    @Override
    public geo_location getLocation() {
        return location;
    }

    @Override
    public void setLocation(geo_location p) {
        this.location= p;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight= w;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
        this.info= s;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t) {
        this.tag= t;
    }

    public String toString(){
        return  "Node- Key:" + key ;
    }

}
