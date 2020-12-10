package api;

/**
 * This class implements the geo_location interface, represents a geo location <x,y,z>, aka Point3D.
 */
public class GeoLocation implements geo_location{

    private double x,y,z;

    /**
     * default constructor
     */
    public GeoLocation(){
        this.x= 0;
        this.y= 0;
        this.z= 0;
    }

    public GeoLocation(double x, double y, double z){
        this.x= x;
        this.y= y;
        this.z= z;
    }

    /**
     * copy constructor
     */
    public GeoLocation(geo_location geo){
        this.x= geo.x();
        this.y= geo.y();
        this.z= geo.z();
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    /**
     * This function returns the distance between this location to location g.
     * @param g
     * @return distance between two points.
     */
    @Override
    public double distance(geo_location g) {
        return Math.sqrt(Math.pow((this.x-g.x()),2)+Math.pow((this.y-g.y()),2)+Math.pow((this.z-g.z()),2));
    }

    public String toString(){
        return "Geo Location: "+x+","+y+","+z+".";
    }
}
