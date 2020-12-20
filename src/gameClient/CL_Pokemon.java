package gameClient;
import api.edge_data;
import gameClient.util.Point3D;
import org.json.JSONObject;

/**
 * This class represents a Pokemon in the game
 */
public class CL_Pokemon {
	private edge_data _edge;
	private double _value;
	private int _type;
	private Point3D _pos;
	private double min_dist;
	private int min_ro;

	/**
	 * default constructor
	 */
	public CL_Pokemon(Point3D p, int t, double v, double s, edge_data e) {
		_type = t;
		_value = v;
		set_edge(e);
		_pos = p;
		min_dist = -1;
		min_ro = -1;
	}

	/**
	 * This function is initializing this pokemon to the pokemon that is given in the json file string
	 * @param json
	 * @return
	 */
	public static CL_Pokemon init_from_json(String json) {
		CL_Pokemon ans = null;
		try {
			JSONObject p = new JSONObject(json);
			int id = p.getInt("id");

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ans;
	}
	public String toString() {return "F:{v="+_value+", t="+_type+"}";}

	/**
	 * @return the edge_data the Pokemon is on
	 */
	public edge_data get_edge() {
		return _edge;
	}

	/**
	 * @param _edge for the pokemon to be on
	 */
	public void set_edge(edge_data _edge) {
		this._edge = _edge;
	}

	/**
	 * @return the location of the pokemon
	 */
	public Point3D getLocation() {
		return _pos;
	}
	public int getType() {return _type;}

	/**
	 * @return the value of the Pokemon
	 */
	public double getValue() {return _value;}

	//not currently in use
	public double getMin_dist() {
		return min_dist;
	}
	//not currently in use
	public void setMin_dist(double mid_dist) {
		this.min_dist = mid_dist;
	}

	//not currently in use
	//	public double getSpeed() {return _speed;}
	//not currently in use
	public int getMin_ro() {
		return min_ro;
	}
	//not currently in use
	public void setMin_ro(int min_ro) {
		this.min_ro = min_ro;
	}
}
