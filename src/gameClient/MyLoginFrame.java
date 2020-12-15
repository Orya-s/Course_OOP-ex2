package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a GUI class to present a login window for the game.
 */
public class MyLoginFrame extends JFrame{

    private Arena _ar;
    private MyLoginPanel p;


    MyLoginFrame(String a) {
        super(a);
        this.setSize(350, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public int invisible(){
        if(!(this.p.isVisible())) {
            this.setVisible(false);
            return 1;
        }
        return 0;
    }

    public void loginPage() {
        p = new MyLoginPanel();
        this.add(p);

        this.setVisible(true);
        p.setVisible(true);
    }

    public void paintComponents(Graphics g) {
        MyLoginPanel myLoginPanel = new MyLoginPanel();
        this.add(myLoginPanel);
//			this.revalidate();
    }

    public int getID() {
        return p.getID();
    }

    public int getLevel() {
        return p.getLevel();
    }


    public static class MyLoginPanel extends JPanel implements ActionListener {
        private int level;
        private int ID;
        private JTextField jt;
        private JTextField jt2;
        private JButton jb;


        public MyLoginPanel() {
            super();
            this.setLayout(null);

            JLabel idLabel= new JLabel("ID:");
            idLabel.setBounds(10,20,80,25);
            this.add(idLabel);

            jt= new JTextField(20);
            jt.setBounds(100,20,165,25);
            this.add(jt);

            JLabel levelNumLabel= new JLabel("Level Num:");
            levelNumLabel.setBounds(10,50,80,25);
            this.add(levelNumLabel);

            jt2= new JTextField(20);
            jt2.setBounds(100,50,165,25);
            this.add(jt2);


            //start game button
            jb= new JButton("Start");
            jb.setBounds(125, 90, 80, 25);
            jb.addActionListener(this);
            this.add(jb);
        }


        public int getID() {
            return ID;
        }

        public int getLevel() {
            return level;
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            String id= jt.getText();
            String level1= jt2.getText();

            ID= Integer.parseInt(id);
            level= Integer.parseInt(level1);

            this.setVisible(false);
            Ex2.client.start();
        }
    }


}
