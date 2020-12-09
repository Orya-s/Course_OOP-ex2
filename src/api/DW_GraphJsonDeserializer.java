package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DW_GraphJsonDeserializer implements JsonDeserializer<directed_weighted_graph> {
    @Override
    public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        directed_weighted_graph graph = new DWGraph_DS();
        JsonArray jsonArray= (JsonArray)jsonObject.get("Nodes");
        Iterator<JsonElement> it= jsonArray.iterator();
        while (it.hasNext()){
            JsonElement temp= it.next();
            int id=temp.getAsJsonObject().get("id").getAsInt();
            node_data n= new NodeData(id);

            String pos=temp.getAsJsonObject().get("pos").getAsString();
            String[] posSplit= pos.split(",");
            double x= Double.parseDouble(posSplit[0]);
            double y= Double.parseDouble(posSplit[1]);
            double z= Double.parseDouble(posSplit[2]);
//            System.out.println("x: "+x+" y: "+y+" z: "+z);
            geo_location geoLoc= new GeoLocation(x,y,z);
            n.setLocation(geoLoc);

            graph.addNode(n);
        }

        JsonArray jsonArrayEdge= (JsonArray)jsonObject.get("Edges");
        Iterator<JsonElement> it2= jsonArrayEdge.iterator();
        while (it2.hasNext()){
            JsonElement temp= it2.next();
            int src=temp.getAsJsonObject().get("src").getAsInt();
            double w= temp.getAsJsonObject().get("w").getAsDouble();
            int dest=temp.getAsJsonObject().get("dest").getAsInt();
            graph.connect(src,dest,w);
        }

        return graph;
    }


}
