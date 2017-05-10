package core;

import java.util.ArrayList;

public class Order {

    private ArrayList<Resource> resources;
    private int nbYacks = 0 ;

    public Order() {
        //Random between 1 and 3 resources
        int nbResources = (int) (Math.random() * 3 + 1);
        resources = new ArrayList<>();
        for (int i = 0; i < nbResources; i++) {
            Resource.Type type = null;
            int randType = (int) (Math.random() * 4 + 0);
            switch(randType){
                case 0:
                    type = Resource.Type.sel;
                    break;
                case 1:
                    type = Resource.Type.orge;
                    break;
                case 2:
                    type = Resource.Type.the;
                    break;
                case 3:
                    type = Resource.Type.jade;
                    break;
                case 4:
                    type = Resource.Type.or;
                    break;
            }
            resources.add(new Resource(type));
            
            switch(nbResources){
                case 1:
                    nbYacks = (int) (Math.random() * 5 + 2);
                    break;
                case 2:
                    nbYacks = (int) (Math.random() * 7 + 5);
                    break;
                case 3:
                    nbYacks = (int) (Math.random() * 8 + 7);
                    break;
            }
        }
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }
    
    public int getNbYacks() {
        return nbYacks;
    }    
}
