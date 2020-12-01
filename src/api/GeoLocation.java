package ex2.src.api;

public class GeoLocation implements geo_location{

    private double x,y,z;

    public GeoLocation(double x, double y, double z){
        this.x= x;
        this.y= y;
        this.z= z;
    }

    public GeoLocation(){
        this.x= 0;
        this.y= 0;
        this.z= 0;
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

    @Override
    public double distance(geo_location g) {
        return 0;
    }

    public String toString(){
        return "Geo Location: "+x+","+y+","+z+".";
    }
}
