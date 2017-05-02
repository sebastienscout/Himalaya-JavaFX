package core;

import java.util.ArrayList;

public class BagResources {

    private ArrayList<Resource> resources = new ArrayList<>();

    public BagResources() {
        for (int i = 0; i < 12; i++) {

            resources.add(Resource.sel);
            resources.add(Resource.orge);
            resources.add(Resource.the);
            resources.add(Resource.or);
            resources.add(Resource.jade);

        }
    }

    public Resource takeRandom() {
        int rand = (int) (Math.random() * resources.size());
        Resource result = resources.get(rand);
        resources.remove(result);
        return result;
    }
}
