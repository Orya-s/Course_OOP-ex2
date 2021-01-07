package api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;
import com.google.gson.*;
/**
 * This class represents a directed weighted Graph Theory algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected();
 * 3. double shortestPathDist(int src, int dest);
 * 4. List<node_data> shortestPath(int src, int dest);
 * 5. Save(file);
 * 6. Load(file);
 */
public class DWGraph_Algo implements dw_graph_algorithms {

    private HashMap<Integer, node_data> visited;
    private directed_weighted_graph dw_graph;

    /**
     * default constructor
     */
    public DWGraph_Algo() {
        dw_graph = new DWGraph_DS();
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) {
        this.dw_graph = g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return dw_graph;
    }

    /**
     * Computes a deep copy of this graph, using a copy constructor in the DWGraph_DS class.
     */
    @Override
    public directed_weighted_graph copy() {
        DWGraph_DS ans = new DWGraph_DS((DWGraph_DS) dw_graph);
        return ans;
    }

    /**
     * Returns true iff the graph is connected.
     * This function uses the BFS algorithm to "iterate" over the nodes in the graph, to check if there's a
     * valid path between all the nodes.
     * This function is activating the BFS algorithm using the first node in the graph, but the function
     * can work using any other node in the graph as well.
     * The algorithm is first running on the list of nodes that have an edge from this first node to them,
     * and then on the list of the nodes that send an edge from them to this node. if the counter of the
     * algorithm returns the number of nodes in the graph- the graph is connected.
     * @return true iff the graph is connected.
     */
    @Override
    public boolean isConnected() {
        if (dw_graph.getV().size() <= 1) return true;
        if (dw_graph.edgeSize() < dw_graph.nodeSize() - 1) return false;

        resetTag(dw_graph, 0);
        int counter = 1;

        Queue<node_data> checklist = new LinkedList<>();

        Collection<node_data> points = dw_graph.getV();
        Iterator<node_data> st = points.iterator();
        node_data head = st.next();
        head.setTag(1);
        checklist.add(head);

        while (!checklist.isEmpty()) {
            node_data currentNode = checklist.poll();

            Collection<node_data> nei = ((NodeData) currentNode).getNi();
            Iterator<node_data> it = nei.iterator();
            while (it.hasNext()) {
                node_data temp = it.next();
                if (temp.getTag() == 0) {
                    checklist.add(temp);
                    temp.setTag(1);
                    counter++;
                }
            }
        }
        if (counter == dw_graph.nodeSize()) {

            resetTag(dw_graph, 0);
            int counter1 = 1;

            Queue<node_data> checklist1 = new LinkedList<>();

            Collection<node_data> points1 = dw_graph.getV();
            Iterator<node_data> st1 = points1.iterator();
            head = st1.next();
            head.setTag(1);
            checklist1.add(head);

            while (!checklist1.isEmpty()) {
                node_data currentNode = checklist1.poll();

                Collection<node_data> parents = ((NodeData) currentNode).getParents();
                Iterator<node_data> it = parents.iterator();
                while (it.hasNext()) {
                    node_data temp = it.next();
                    if (temp.getTag() == 0) {
                        checklist1.add(temp);
                        temp.setTag(1);
                        counter1++;
                    }
                }
            }

            if (counter1 == dw_graph.nodeSize()) {
                return true;
            }
        }
        return false;
    }

    private void resetTag(directed_weighted_graph g, int tag) {
        Collection<node_data> points = g.getV();
        Iterator<node_data> st = points.iterator();

        while (st.hasNext()) {
            node_data temp = st.next();
            temp.setTag(tag);
        }
    }

    /**
     * Returns the length of the shortest path between two nodes- source and destination.
     * This function is using the Dijkstra algorithm. The Dijkstra function recieves the source node and
     * the destination node and is setting the weight of every node in the path between them as the distance
     * from the source node. Until the algorithm finds all the ways to get to the destination node
     * and sets the weight of destination node with the distance of the shortest path.
     * Then this function returns the weight of the destination node.
     * If there isn't a valid path between the two nodes the function returns -1.
     * @param src - start node.
     * @param dest - end node.
     * @return the distance between the two nodes.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest) return 0;

        if (dw_graph.getV().contains(dw_graph.getNode(src)) && dw_graph.getV().contains(dw_graph.getNode(dest))) {
            node_data start = dw_graph.getNode(src);
            node_data end = dw_graph.getNode(dest);
            return DijkstraAlgo(start, end);
        }
        return -1;
    }

    /**
     * Returns the shortest path between src to dest as an ordered List of nodes.
     * This function is using the Dijkstra algorithm to set the weight of the destination node (and all
     * the other nodes in the path) as the shortest distance from the source node. When a node
     * gets the right weight the function is setting the info of the node as the key of the previous
     * node in the path. Then with the getPath function the list is created by adding the destination
     * node and then adding the node with the key from the destination node's info, this process
     * continues until the function gets to the source node.
     * In case there isn't a valid path between the two nodes the function returns null.
     * @param src - start node
     * @param dest - end (target) node
     * @return List<node_data> of the shortest path.
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        if(src == dest) {
            LinkedList<node_data> ans= new LinkedList<>();
            ans.add(dw_graph.getNode(src));
            return ans;
        }
        if(shortestPathDist(src, dest) == -1) return null;

        return getPath(dw_graph.getNode(src), dw_graph.getNode(dest));
    }

    public List<node_data> connected_component(int ID) {
        List<node_data> ans1 = new LinkedList<node_data>();
        Queue<NodeData> checklist = new LinkedList<>();
        if (dw_graph == null || dw_graph.getV().size() == 0 || !dw_graph.getV().contains(dw_graph.getNode(ID)))
            return ans1;
//        ans1.add((NodeData) dw_graph.getNode(ID));
        checklist.add((NodeData) dw_graph.getNode(ID));
        resetTag(dw_graph, 0);
        while (!checklist.isEmpty()) {
            NodeData temp = checklist.poll();
            if (temp.getTag() == 0) {
                temp.setTag(1);
                for (node_data n : temp.getNi()) {
//                    ans1.add((NodeData) n);
                    checklist.add((NodeData) n);
                }
            }
        }

//        List<NodeData> ans2 = new LinkedList<>();
        ans2.add((NodeData) dw_graph.getNode(ID));
        checklist.add((NodeData) dw_graph.getNode(ID));
        while (!checklist.isEmpty()) {
            NodeData temp = checklist.poll();
            if (temp.getTag() == 1) {
                for (node_data n : temp.getParents()) {
                    visited.put(n.getKey(), n)         ;
                    checklist.add((NodeData) n);
                }
                temp.setTag(0);
            }
        }
//        Iterator<node_data> it = ((NodeData)graph.get(key)).getNi().iterator();
        Iterator<node_data> it = visited.values().iterator();
        while(it.hasNext()){
            node_data temp = it.next();
            ans1.add(temp);
        }

        return ans1;
    }

    public List<List<node_data>> connected_components() {
        List<List<node_data>> ans = new LinkedList<>();
        List<node_data> temp = new LinkedList<node_data>();
        for (node_data n : dw_graph.getV()) {
            if (n.getTag() == 0) {
                temp = connected_component(n.getKey());
                ans.add(temp);
            }
        }
        return ans;
    }

    /**
     * Saves this weighted directed graph to the given file name, using json serialization.
     * @param file - the file name.
     * @return true - iff the file was successfully saved.
     */
    @Override
    public boolean save(String file) {

        JsonObject graph= new JsonObject();
        JsonArray edges= new JsonArray();
        JsonArray nodes= new JsonArray();

        for(edge_data e: ((DWGraph_DS)dw_graph).getE()){
            JsonObject edge= new JsonObject();
            edge.addProperty("src",e.getSrc());
            edge.addProperty("w",e.getWeight());
            edge.addProperty("dest",e.getDest());
            edges.add(edge);
        }
        for (node_data n: dw_graph.getV()){
            JsonObject node= new JsonObject();
            String pos= n.getLocation().x()+","+n.getLocation().y()+","+n.getLocation().z();
            node.addProperty("pos", pos);
            node.addProperty("id", n.getKey());
            nodes.add(node);
        }
        graph.add("Edges",edges);
        graph.add("Nodes",nodes);

        try
        {
            Gson gson = new Gson();
            PrintWriter pw = new PrintWriter(new File(file));
            pw.write(gson.toJson(graph));
            pw.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * This method loads a graph to this graph algorithm. using the DW_GraphJsonDeserializer class that is
     * implementing JsonDeserializer<directed_weighted_graph>.
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(DWGraph_DS.class, new DW_GraphJsonDeserializer());
            Gson gson = builder.create();

            FileReader reader = new FileReader(file);
            this.dw_graph= gson.fromJson(reader, DWGraph_DS.class);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    private double DijkstraAlgo(node_data start, node_data end){
        PriorityQueue<node_data> q= new PriorityQueue<>(Comparator.comparingDouble(node_data::getWeight));

        for (node_data node : dw_graph.getV()) {
            node.setInfo("null");
            node.setWeight(Double.MAX_VALUE);
        }
        start.setWeight(0);
        q.add(start);
        while (!q.isEmpty()) {

            node_data curr= q.remove();
            if (!curr.getInfo().contains("visited")) {
                curr.setInfo(curr.getInfo() + " visited");
                if (curr.getKey() == end.getKey()) {
                    return curr.getWeight();
                }

                for (edge_data e : dw_graph.getE(curr.getKey())) {
                    node_data neighbor= dw_graph.getNode(e.getDest());

                    if (!neighbor.getInfo().contains("visited")) {
                        double distance = curr.getWeight() + dw_graph.getEdge(curr.getKey(),neighbor.getKey()).getWeight();
                        if (distance < neighbor.getWeight()) {
                            neighbor.setWeight(distance);
                            neighbor.setInfo(curr.getKey() + "");
                            q.add(neighbor);
                        }
                    }
                }
            }
        }

        return -1;
    }


    private List<node_data> getPath(node_data start, node_data end){
        LinkedList<node_data> ans= new LinkedList<>();
        if(end.getWeight() == Double.MAX_VALUE) return null;

        if(start == end){
            ans.add(start);
            return ans;
        }

        node_data temp= end;
        String info="";
        while (temp != start){
            ans.addFirst(temp);
            info= temp.getInfo();
            String[] getKey = info.split(" ");
            int key= Integer.parseInt(getKey[0]);
            temp= dw_graph.getNode(key);
        }
        ans.addFirst(start);

        return ans;
    }


}
