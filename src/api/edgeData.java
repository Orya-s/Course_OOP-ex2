package ex2.src.api;

import java.util.HashMap;

public class edgeData implements edge_data {

    private int src, dest, key, tag;
    private double weight;
    private String id, info;
    private static int counter;

    public edgeData(int src, int dest, double weight){
        this.src= src;
        this.dest= dest;
        this.weight= weight;
        this.id= src + "-" + dest;

        this.key= counter();  //unique key of the edge
    }

    private int counter(){
        return counter++;
    }

    public String getId(){
        return id;
    }

    @Override
    public int getSrc() {
        return src;
    }

    @Override
    public int getDest() {
        return dest;
    }

    @Override
    public double getWeight() {
        return weight;
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
}
