package core;

import java.util.ArrayList;
import java.util.HashMap;

public class Village {

    public enum Type {
        house,
        temple,
        monastery
    };
    private int id;
    private ArrayList<Resource> resources;
    private HashMap<Road, Village> roads;
    private Order order;
    private Type type;
    private Player stupa;

    public Village(int id, Type type) {
        this.id = id;
        this.type = type;
        resources = new ArrayList<>();
        roads = new HashMap<>();
        stupa = null;
        order = null;
    }

    public Player getStupa() {
        return stupa;
    }

    public void setStupa(Player stupa) {
        this.stupa = stupa;
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

    public void removeResource(Resource r) {
        resources.remove(r);
    }
    
    public void removeOrder() {
        order = null;
    }

    public Village getDestVillage(Road type) {

        Village v = roads.get(type);
        System.out.println("Village : " + v);

        return v;
    }

    @Override
    public String toString() {
        return "Village{" + "id=" + id + ", type=" + type + '}';
    }

    public int getId() {
        return id;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public Order getOrder() {
        return order;
    }
}
