package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a GUI class to present a game on a graph.
 */
public class MyFrame extends JFrame{
	private int _ind;
	private Arena _ar;
	private Range2Range _w2f;
	private float time;
	private int grade;

	private ImageIcon image1;
	private JLabel label1;
	private ImageIcon image2;


	MyFrame(String a) {
		super(a);
		int _ind = 0;
		this.setSize(1000, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void paint(Graphics g) {
		MyPanel myPanel = new MyPanel(this._w2f);
		this.add(myPanel);
		updateFrame();
	}

	public void update(Arena ar) {
		this._ar = ar;
		updateFrame();
	}

	private void updateFrame() {
		Range rx = new Range(50,this.getWidth()-50);
		Range ry = new Range(this.getHeight()-80,150);
		Range2D frame = new Range2D(rx,ry);
		if(_ar != null) {
			directed_weighted_graph g = _ar.getGraph();
			_w2f = Arena.w2f(g, frame);
			this.revalidate();
			this.setVisible(true);
		}
	}

	public void timeToEndGame(long l) {
		time= (float)l/100;
	}

	public void gameGrade(int grade) {
		this.grade= grade;
	}


	private class MyPanel extends JPanel implements ActionListener {
		private Range2Range _w2f;

		public MyPanel(Range2Range _w2f) {
			this.setBackground(Color.orange);
			if(_w2f != null)
				this._w2f = _w2f;
			this.revalidate();
		}

		/**
		 * drawing all the elements in the game (graph, agents, Pokemons)
		 * @param g
		 */
		public void paint(Graphics g)
		{
			if(_w2f != null && _ar!= null) {
				Graphics2D g1 = (Graphics2D) g;
				super.paintComponent(g);
				updateFrame();
				drawTitle(g1);
				drawGraph(g1);
				drawPokemons(g1);
				drawAgents(g1);
				drawInfo(g1);
				drawTimer(g1);
				grades(g1);
			}
		}

		private void drawTitle(Graphics g){

			//adding background picture
			image1= new ImageIcon("./data/pokemon-wallpaper.jpg");
			double widthScaleFactor = getWidth() / (double)image1.getIconWidth();
			double heightScaleFactor = getHeight() / (double)image1.getIconHeight();
			double scaleFactor = (widthScaleFactor > heightScaleFactor)? heightScaleFactor : widthScaleFactor;
			int width = (int)(image1.getIconWidth() * scaleFactor);
			int height = (int)(image1.getIconHeight() * scaleFactor);
			g.drawImage(image1.getImage(), 0, 0, width, height, this);

			//adding title picture
			image2= new ImageIcon("./data/pokemon_logo.png");
			g.drawImage(image2.getImage(),340,20,300,150,this);

//			label1= new JLabel("Time left:");
//			label1.setBounds(10,20,800,25);
//			add(label1);
//			g.drawString(label1.getText(), 50,50);

		}
		public  void grades(Graphics g){
			double points = 0;
			for (CL_Agent i : _ar.getAgents()) {
				points = i.getValue();
				g.setColor(Color.blue);
				g.setFont(new Font("SERIF", 1, 14));
				g.drawString("Agent " + i.getID() + ":  " + points, 40, 65 + (20 * i.getID()));
			}
		}

		public void drawTimer(Graphics g){
			g.setColor(Color.blue);
			g.setFont(new Font(	"SERIF",1,18) );
			g.drawString("Time left: "+time,35,45);
//			g.drawString("Score: "+grade,35,65);

		}
		private void drawInfo(Graphics g) {
			if(_w2f != null && _ar!= null) {
				List<String> str = _ar.get_info();
				String dt = "none";
				for (int i = 0; i < str.size(); i++) {
					g.drawString(str.get(i) + " dt: " + dt, 100, 60 + i * 20);
				}
			}
			this.revalidate();
		}
		private void drawGraph(Graphics g) {
			if(_w2f != null && _ar!= null) {
				directed_weighted_graph gg = _ar.getGraph();
				Iterator<node_data> iter = gg.getV().iterator();
				while (iter.hasNext()) {
					node_data n = iter.next();
					g.setColor(Color.black);
					drawNode(n, 5, g);
					Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
					while (itr.hasNext()) {
						edge_data e = itr.next();
						g.setColor(Color.gray);
						drawEdge(e, g);
					}
				}
			}
			this.revalidate();
		}
		private void drawPokemons(Graphics g) {
			List<CL_Pokemon> fs = _ar.getPokemons();
			if(fs!=null) {
				Iterator<CL_Pokemon> itr = fs.iterator();

				while(itr.hasNext()) {

					CL_Pokemon f = itr.next();
					Point3D c = f.getLocation();
					int r=10;
					g.setColor(Color.blue);
					if(f.getType()<0) {g.setColor(Color.cyan.darker());}
					if(c!=null) {

						geo_location fp = this._w2f.world2frame(c);
						g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
						//	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);

					}
				}
			}
			this.revalidate();

		}
		private void drawAgents(Graphics g) {
			if(_w2f != null && _ar!= null) {
				List<CL_Agent> rs = _ar.getAgents();
				//	Iterator<OOP_Point3D> itr = rs.iterator();
				g.setColor(Color.red);
				int i = 0;
				while (rs != null && i < rs.size()) {
					geo_location c = rs.get(i).getLocation();
					int r = 8;
					i++;
					if (c != null) {

						geo_location fp = this._w2f.world2frame(c);
						g.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);
					}
				}
			}
			this.revalidate();
		}
		private void drawNode(node_data n, int r, Graphics g) {
			if(_w2f != null && _ar!= null) {
				geo_location pos = n.getLocation();
				geo_location fp = this._w2f.world2frame(pos);
				g.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);
				g.drawString("" + n.getKey(), (int) fp.x(), (int) fp.y() - 4 * r);
			}
			this.revalidate();
		}
		private void drawEdge(edge_data e, Graphics g) {
			if(_w2f != null && _ar!= null) {
				directed_weighted_graph gg = _ar.getGraph();
				geo_location s = gg.getNode(e.getSrc()).getLocation();
				geo_location d = gg.getNode(e.getDest()).getLocation();
				geo_location s0 = this._w2f.world2frame(s);
				geo_location d0 = this._w2f.world2frame(d);
				g.drawLine((int) s0.x(), (int) s0.y(), (int) d0.x(), (int) d0.y());
			}
			this.revalidate();
			//	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

}