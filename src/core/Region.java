package core;

import java.util.ArrayList;

public class Region {

    private ArrayList<Region> neighbors;
    private int id;

    public Region(int id) {
        this.id = id;
    }

    public void addNeighbor(Region r) {
        neighbors.add(r);
    }

}
