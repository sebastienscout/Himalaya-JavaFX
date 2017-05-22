package core;

import java.util.ArrayList;

public class BagResources {

    private ArrayList<Resource> resources;

    public BagResources() {
        resources = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            resources.add(new Resource(Resource.Type.sel));
        }
        for (int i = 0; i < 15; i++) {
            resources.add(new Resource(Resource.Type.orge));
        }
        for (int i = 0; i < 12; i++) {
            resources.add(new Resource(Resource.Type.the));
        }
        for (int i = 0; i < 9; i++) {
            resources.add(new Resource(Resource.Type.jade));
        }
        for (int i = 0; i < 6; i++) {
            resources.add(new Resource(Resource.Type.or));
        }
    }
    
    /**
     * Constructeur par copie
     * @param br 
     */
    public BagResources(BagResources br){
        this.resources = new ArrayList<>();
        for (Resource resource : br.resources) {
            this.resources.add(new Resource(resource.getType()));
        }
    }

    public Resource takeRandom() {
        if (resources.size() > 0) {
            int rand = (int) (Math.random() * resources.size());
            Resource result = resources.get(rand);
            resources.remove(result);
            return result;
        }

        System.out.println("Plus de resources disponible !");
        return null;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public void addResource(Resource r) {
        resources.add(r);
    }
}
