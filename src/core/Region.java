package core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Region {

    private int id;
    private HashMap<String, Integer> delegations;

    public Region(int id) {
        this.id = id;
        delegations = new HashMap<>();
    }

    /**
     * Constructeur par copie
     *
     * @param r
     */
    public Region(Region r) {
        this.id = r.id;
        delegations = new HashMap<>();
        for (Map.Entry<String, Integer> entry : r.delegations.entrySet()) {
            String color = entry.getKey();
            Integer nbDelegations = entry.getValue();
            this.delegations.put(color, new Integer(nbDelegations));
        }

    }

    public int getId() {
        return id;
    }
    
    public HashMap<String, Integer> getDelegations() {
        return delegations;
    }

    public void addDelegations(Player r, Integer nb) {
        if (delegations.get(r.getColor()) == null) {
            delegations.put(r.getColor(), nb);
        } else {
            int nbDelegation = delegations.get(r.getColor()) + nb;
            delegations.put(r.getColor(), nbDelegation);
        }

    }

    public String getMaxDelegationPlayer() {
        if (delegations.size() > 0) {
            return Collections.max(delegations.entrySet(), Map.Entry.comparingByValue()).getKey();
        }
        return null;
    }

}
