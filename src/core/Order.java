package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Order {

    private ArrayList<Resource> resources;

    public Order() {
        //Random between 1 and 3 resources
        int nbResources = (int) (Math.random() * 3 + 1);
        resources = new ArrayList<>();
        for (int i = 0; i < nbResources; i++) {
            resources.add(Resource.getRandomResource());
        }
    }
}
