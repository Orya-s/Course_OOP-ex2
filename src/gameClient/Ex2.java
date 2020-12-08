package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Ex2 implements Runnable {

    private static HashMap<Integer,Integer> addressed = new HashMap<>();  //<agent_id,destNodeKey>
    private static HashMap<Integer,Integer> destination = new HashMap<>();  //<destNodeKey,agent_id>

    private static MyFrame _win;
    private static Arena _ar;
    public static void main(String[] a) {
        Thread client = new Thread(new Ex2());
        client.start();
    }


    @Override
    public void run() {
        int scenario_num = 23;
        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
//        	int id = 208925982;
//        	game.login(id);
        String g = game.getGraph();
        String pks = game.getPokemons();
        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        init(game);

        game.startGame();
        _win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
        int ind=0;
        long dt=100;


//make sure there's only one agent going after the same pokemon
        String lg = game.move();
        List<CL_Agent> log = Arena.getAgents(lg, gg);
        Iterator<CL_Agent> it= log.iterator();
        while (it.hasNext()){
            CL_Agent temp= it.next();
            addressed.put(temp.getID(),-1);
        }


        Collection<node_data> n= gg.getV();
        Iterator<node_data> it2= n.iterator();
        while (it2.hasNext()){
            node_data temp= it2.next();
            destination.put(temp.getKey(),-1);
        }


        while(game.isRunning()) {
            moveAgents(game, gg);
            try {
                if(ind%1==0) {_win.repaint();
                    _win.setTitle("Ex2 - "+game.toString());
                }
                Thread.sleep(dt);
                ind++;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        String res = game.toString();

        System.out.println(res);
        System.exit(0);
    }


    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     * @param game
     * @param gg
     * @param
     */
    private static void moveAgents(game_service game, directed_weighted_graph gg) {
        String lg = game.move();
        List<CL_Agent> log = Arena.getAgents(lg, gg);
        _ar.setAgents(log);
        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
        String fs =  game.getPokemons();
        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
        _ar.setPokemons(ffs);
        for(int i=0;i<log.size();i++) {
            CL_Agent ag = log.get(i);
            int id = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            double v = ag.getValue();
            if(dest==-1) {
                dest = nextNode(gg, src,id);
                game.chooseNextEdge(ag.getID(), dest);
                System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
            }
        }
    }
    /**
     * a very simple random walk implementation!
     * @param g
     * @param src
     * @return
     */
    private static int nextNode(directed_weighted_graph g, int src, int ag_id) {
        int ans = -1;

        //FIRST MOVE
        Collection<CL_Pokemon> pokeC = _ar.getPokemons();
        Iterator<CL_Pokemon> itr1 = pokeC.iterator();
        while(itr1.hasNext()){
            CL_Pokemon temp= itr1.next();
            Arena.updateEdge(temp,g);

            if(temp.get_edge().getSrc() == src) {
                destination.put(src,-1);
                return temp.get_edge().getDest();
            }
        }

        dw_graph_algorithms ga= new DWGraph_Algo();
        ga.init(g);
        double dist= Double.MAX_VALUE;
        int key= -1;

        List<CL_Pokemon> poke = _ar.getPokemons();
        Iterator<CL_Pokemon> itr2 = poke.iterator();
        double value= 0;

        while (itr2.hasNext()){
            CL_Pokemon temp= itr2.next();
            Arena.updateEdge(temp, g);
            int tempSrc= temp.get_edge().getSrc();
    //first draft
//            if((!addressed.containsValue(tempSrc)) ||
//                    (addressed.containsValue(tempSrc)&&addressed.get(ag_id)==tempSrc) ) {  ///change

    //second draft
             if(destination.get(tempSrc) == -1 ||
                     destination.get(tempSrc)== ag_id )   {
                double distTemp = ga.shortestPathDist(src, tempSrc);
                if (distTemp < dist) {
                    dist = distTemp;
                    key = tempSrc;
                    value= temp.getValue();
                }
                if (distTemp==dist){  //check if value of distTemp is higher
                    if (temp.getValue()>value) key= tempSrc;
                }
            }
        }

        destination.put(key,ag_id);
//        addressed.put(ag_id,key);
        List<node_data> ll=ga.shortestPath(src,key);
        System.out.println(destination);


        if(ll==null){
            Collection<edge_data> ee = g.getE(src);
            Iterator<edge_data> itr = ee.iterator();
            int s = ee.size();
            int r = (int)(Math.random()*s);
            int i=0;
            while(i<r) {itr.next();i++;}
            ans = itr.next().getDest();
            return ans;
        }
        return ll.get(1).getKey();




        //DEFAULT
//         ans = -1;
//        Collection<edge_data> ee = g.getE(src);
//        Iterator<edge_data> itr = ee.iterator();
//        int s = ee.size();
//        int r = (int)(Math.random()*s);
//        int i=0;
//        while(i<r) {itr.next();i++;}
//        ans = itr.next().getDest();
//        return ans;
    }

    private void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();
        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        //gg.init(g);
        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win = new MyFrame("test Ex2");
        _win.setSize(1000, 700);
        _win.update(_ar);


        _win.show();
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());
            int src_node = 0;  // arbitrary node, you should start at one of the pokemon
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
            for(int a = 0;a<rs;a++) {
                int ind = a%cl_fs.size();
                CL_Pokemon c = cl_fs.get(ind);
                System.out.println((c.get_edge()) + "  " + c.get_edge().getDest()); ///////////////
                int nn = c.get_edge().getDest();
                if(c.getType()<0 ) {nn = c.get_edge().getSrc();}

                game.addAgent(nn);

                Collection<CL_Pokemon> pokeC = _ar.getPokemons();
                Iterator<CL_Pokemon> itr = pokeC.iterator();
                while(itr.hasNext()) {
                    CL_Pokemon temp = itr.next();
                    Arena.updateEdge(_ar.getPokemons().get(0),_ar.getGraph());

                }
            }
        }
        catch (JSONException e) {e.printStackTrace();}
    }
}
