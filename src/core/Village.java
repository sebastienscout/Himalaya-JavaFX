package core;

import java.util.ArrayList;
import java.util.HashMap;

public class Village {

    private enum Type {
        house,
        temple,
        monastery
    };
    private int id;
    private ArrayList<Resource> resources;
    private HashMap<Road, Village> roads;
    private Order order;
    private Type type;

    public Village(int id, Type type) {
        this.id = id;
        resources = new ArrayList<>();
        roads = new HashMap<>();
        this.type = type;
    }

    public void addRoad(Road r, Village v) {
        roads.put(r, v);
    }

    public void setOrder(Order o) {
        order = o;
    }

    public void addResource(Resource r) {
        resources.add(r);
    }

}
