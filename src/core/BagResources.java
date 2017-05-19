package core;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BagResources implements Cloneable {

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

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public void addResource(Resource r) {
        resources.add(r);
    }

    public Object clone() {
        BagResources br = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la 
            // méthode super.clone()
            br = (BagResources) super.clone();
        } catch (CloneNotSupportedException ex) {
            // Ne devrait jamais arriver car nous implémentons 
            // l'interface Cloneable
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }

        // On clone les attributs
        br.resources = new ArrayList<>(resources.size());
        for (Resource resource : resources) {
            br.resources.add((Resource) resource.clone());
        }

        return br;
    }

}
