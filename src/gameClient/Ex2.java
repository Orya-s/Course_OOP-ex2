package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Ex2 implements Runnable {

    private static HashMap<Integer,Integer> destination = new HashMap<>();  //<destNodeKey,agent_id>

    private static MyFrame _win;
    private static MyLoginFrame lf;
    private static Arena _ar;
    private static long dt;
    public static long startTime = System.currentTimeMillis();
    private int scenario_num;
    private int id;
    public static Thread client = new Thread(new Ex2());



    public static void main(String[] a) {
        lf = new MyLoginFrame("login");
        lf.loginPage();
    }


    @Override
    public void run() {
        scenario_num = lf.getLevel();
        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
        id = lf.getID();
        game.login(id);
        String g = game.getGraph();

        //getting the graph for the game
        try {
            File f = new File("Graph.json");
            FileWriter fileWriter = new FileWriter(f);
            fileWriter.write(g);
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.load("Graph.json");
        directed_weighted_graph gg = ga.getGraph();

        init(game,gg);
        game.startGame();
        _win.setTitle("Ex2 - OOP: "+game.toString());
        int ind=0;
        dt=120;

        //make sure there's only one agent going after the same pokemon
        Collection<node_data> n= gg.getV();
        Iterator<node_data> it2= n.iterator();
        while (it2.hasNext()){
            node_data temp= it2.next();
            destination.put(temp.getKey(),-1);
        }

        while(game.isRunning()) {

            if (System.currentTimeMillis() - startTime > 10000)
                dt = 110;
            if (System.currentTimeMillis() - startTime > 20000)
                dt = 100;
            if (System.currentTimeMillis() - startTime > 30000)
                dt = 90;
            if (System.currentTimeMillis() - startTime > 40000)
                dt = 85;
            if (System.currentTimeMillis() - startTime > 50000)
                dt = 80;

            moveAgents(game, gg);
            try {
                if(ind%1==0) {
                    _win.repaint();
                    _win.timeToEndGame(game.timeToEnd()/10);
                    int grade=0;
                    for (CL_Agent ag :_ar.getAgents()) {
                        grade+= ag.getValue();
                    }
                    _win.gameGrade(grade);
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
     * in case the agent is on a node the next destination (next edge) is chosen using the nextNode function.
     * @param game
     * @param gg
     * @param
     */
    private static void moveAgents(game_service game, directed_weighted_graph gg) {
        String lg = game.move();
        List<CL_Agent> log = Arena.getAgents(lg, gg);
        _ar.setAgents(log);
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
     * This function returns the key of the node the agent is going to move to.
     * @param g
     * @param src
     * @return
     */
    private static int nextNode(directed_weighted_graph g, int src, int ag_id) {
        int ans = -1;

        //last move
        Collection<CL_Pokemon> pokeC = _ar.getPokemons();
        Iterator<CL_Pokemon> itr1 = pokeC.iterator();
        while(itr1.hasNext()) {
            CL_Pokemon temp = itr1.next();

            Arena.updateEdge(temp, g);
            if (temp.get_edge().getSrc() == src) {
                destination.put(temp.get_edge().getSrc(), -1);
                boolean changeDT= true;
//                if (temp.get_edge().getWeight()<0.3) {    ///////////// added later
//                    dt = dt - 20;
//                    changeDT= false;
//                }
//                else  {
//                    if (!changeDT) {
//                        dt = dt + 20;
//                        changeDT = true;
//                    }
//                }
                return temp.get_edge().getDest();
            }
        }

        dw_graph_algorithms ga= new DWGraph_Algo();
        ga.init(g);
        double dist= Double.MAX_VALUE;
        int key= -1;
        int dest= -1;

        List<CL_Pokemon> poke = _ar.getPokemons();
        Iterator<CL_Pokemon> itr2 = poke.iterator();
        double value= 0;

        while (itr2.hasNext()) {
            CL_Pokemon temp = itr2.next();
            Arena.updateEdge(temp, g);

            int tempSrc = temp.get_edge().getSrc();
            if (destination.get(tempSrc) == -1 || destination.get(tempSrc) == ag_id) {
                double distTemp = ga.shortestPathDist(src, tempSrc);
                if (distTemp < dist) {
                    dist = distTemp;
                    key = tempSrc;
                    value = temp.getValue();
                    dest = temp.get_edge().getDest();
                }
                if (distTemp == dist) {  //check if value of distTemp is higher
                    if (temp.getValue() > value) key = tempSrc;
                }
            }

        }

        //change dest
        Iterator<node_data> it = g.getV().iterator();
        while (it.hasNext()) {
            node_data temp = it.next();
            if ((destination.get(temp.getKey())) == ag_id) {
                destination.put(key, -1);
            }
        }

        //check if any close pokemon's
        List<CL_Pokemon> poke1 = _ar.getPokemons();
        Iterator<CL_Pokemon> it1 = poke1.iterator();
        while (it1.hasNext()) {
            CL_Pokemon temp = it1.next();
            if (temp.get_edge() !=null &&(destination.get(temp.get_edge().getSrc()) == -1 || temp.get_edge().getSrc() == dest)) {
                double tempD = ga.shortestPathDist(temp.get_edge().getSrc(), dest);
                if (tempD < 4) {
                    destination.put(temp.get_edge().getSrc(), ag_id);
                }
            }
        }

        destination.put(key,ag_id);
        List<node_data> ll=ga.shortestPath(src,key);

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
    }

    private void init(game_service game, directed_weighted_graph gg) {
        String fs = game.getPokemons();
//        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win = new MyFrame("test Ex2");
//        _win.pack();
        _win.setSize(1000, 700);
        _win.update(_ar);
//        _win.setTitle("Pokemon");


        _win.show();
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
            for(int a = 0;a<rs;a++) {
                int ind = a%cl_fs.size();
                CL_Pokemon c = cl_fs.get(ind);
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