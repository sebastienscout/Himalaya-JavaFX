package core;

import java.util.ArrayList;

public class BagResources {

    private ArrayList<Resource> resources;

    public BagResources() {
        resources = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            resources.add(new Resource(Resource.Type.sel));
            resources.add(new Resource(Resource.Type.orge));
            resources.add(new Resource(Resource.Type.the));
            resources.add(new Resource(Resource.Type.or));
            resources.add(new Resource(Resource.Type.jade));
        }
    }

    public Resource takeRandom() {
        int rand = (int) (Math.random() * resources.size());
        Resource result = resources.get(rand);
        resources.remove(result);
        return result;
    }
    
    public ArrayList<Resource> getResources(){
        return resources;
    }
    
    public void addResource(Resource r){
        resources.add(r);
    }
}
