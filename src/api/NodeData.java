package ex2.src.api;

import java.util.Collection;
import java.util.HashMap;

public class NodeData implements node_data, java.io.Serializable {

    private int key, tag;
    private String info;
    private geo_location location;
    private double weight;
    private static int counter;
    private HashMap<Integer, node_data> toEdge;  //the list of edges coming out from this node
    private HashMap<Integer, node_data> fromEdge;  //the list of edges coming into this node


    public NodeData(){
        key= counter();
        toEdge= new HashMap<>();
        fromEdge= new HashMap<>();
    }

    private int counter(){
        return counter++;
    }

    public NodeData(int key){
        this.key= key;
        toEdge= new HashMap<>();
        fromEdge= new HashMap<>();
    }


    public Collection<node_data> getNi() {
        return toEdge.values();
    }

    public Collection<node_data> getParents() {
        return fromEdge.values();
    }

    public boolean hasNi(int key) {
        return toEdge.containsKey(key);
    }

    public void addNi(node_data t) {
        toEdge.put(t.getKey(),t);
    }

    public void addEdgeFrom(node_data t){
        fromEdge.put(t.getKey(),t);
    }

    public void removeNi(node_data node) {
        toEdge.remove(node.getKey(),node);
    }

    public void removeEdgeFrom(node_data node){
        fromEdge.remove(node.getKey(),node);
    }

    public boolean hasEdgeFrom(int srcKey){
        return fromEdge.containsKey(srcKey);
    }

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
