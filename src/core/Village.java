package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Village implements Cloneable {

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

    public Object clone() {
        Village v = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la 
            // méthode super.clone()
            v = (Village) super.clone();
        } catch (CloneNotSupportedException ex) {
            // Ne devrait jamais arriver car nous implémentons 
            // l'interface Cloneable
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Clone des attributs
        v.id = id;
        
        
        if(order != null){
            v.order = (Order) order.clone();
        }
        
        v.resources = new ArrayList<>(resources.size());
        for (Resource resource : resources) {
             v.resources.add((Resource) resource.clone());
        }
        
        v.stupa = stupa;
        v.type = type;
       
        return v;
    }

}
