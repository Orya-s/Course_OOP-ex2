import api.edgeData;
import api.edge_data;
import gameClient.CL_Pokemon;
import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CL_PokemonTest {


    @Test
    void get_edge() {
        edge_data _edge= new edgeData(1,2,3.5);
        Point3D point= new Point3D(1,2,3);
        CL_Pokemon pokemon= new CL_Pokemon(point,1,2.5,3.5,_edge);

        assertEquals(_edge,pokemon.get_edge());
    }

    @Test
    void set_edge() {
        edge_data _edge= new edgeData(1,2,3.5);
        Point3D point= new Point3D(1,2,3);
        CL_Pokemon pokemon= new CL_Pokemon(point,1,2.5,3.5,_edge);

        edge_data edge2= new edgeData(1,2,10);
        pokemon.set_edge(edge2);

        assertEquals(edge2,pokemon.get_edge());
    }

    @Test
    void getLocation() {
        edge_data _edge= new edgeData(1,2,3.5);
        Point3D point= new Point3D(1,2,3);
        CL_Pokemon pokemon= new CL_Pokemon(point,1,2.5,3.5,_edge);

        assertEquals(point,pokemon.getLocation());
    }

    @Test
    void getType() {
        edge_data _edge= new edgeData(1,2,3.5);
        Point3D point= new Point3D(1,2,3);
        CL_Pokemon pokemon= new CL_Pokemon(point,1,2.5,3.5,_edge);

        assertEquals(1,pokemon.getType());
    }

    @Test
    void getValue() {
        edge_data _edge= new edgeData(1,2,3.5);
        Point3D point= new Point3D(1,2,3);
        CL_Pokemon pokemon= new CL_Pokemon(point,1,2.5,3.5,_edge);

        assertEquals(2.5,pokemon.getValue());
    }

}