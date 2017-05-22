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
    private HashMap<Road, Integer> roads;
    private Order order;
    private Type type;
    private Player stupa;
    private ArrayList<Region> regions;

    public Village(int id, Type type) {
        this.id = id;
        this.type = type;
        resources = new ArrayList<>();
        roads = new HashMap<>();
        stupa = null;
        order = null;
        regions = new ArrayList<>();
    }

    /**
     * Constucteur par copie
     *
     * @param village
     */
    public Village(Village village) {
        this.id = village.id;
        this.resources = new ArrayList<>();
        this.regions = new ArrayList<>();
        for (Resource resource : village.resources) {
            this.resources.add(new Resource(resource.getType()));
        }
        this.roads = village.roads;

        if (village.order != null) {
            this.order = new Order(village.order);
        }

        this.type = village.type;
        this.stupa = null;

        for (Region region : village.regions) {
            if (region != null) {
                this.regions.add(new Region(region));
            }

        }
    }

    public Type getType() {
        return type;
    }

    public ArrayList<Region> getRegions() {
        return regions;
    }

    public void addRegion(Region region) {
        regions.add(region);
    }

    public Player getStupa() {
        return stupa;
    }

    public void setStupa(Player stupa) {
        this.stupa = stupa;
    }

    public void addRoad(Road r, Integer v) {
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

    public Integer getDestVillage(Road type) {
        return roads.get(type);
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
