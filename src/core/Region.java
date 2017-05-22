package core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Region {

    private int id;
    private HashMap<Player, Integer> delegations;

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
        this.id = id;
        //TODO remplacer Player par string color dans Hash MAP
        this.delegations = null;
    }

    public int getId() {
        return id;
    }

    public HashMap<Player, Integer> getDelegations() {
        return delegations;
    }

    public void addDelegations(Player r, Integer nb) {
        if (delegations.get(r) == null) {
            delegations.put(r, nb);
        } else {
            int nbDelegation = delegations.get(r) + nb;
            delegations.put(r, nbDelegation);
        }

    }

    public Player getMaxDelegationPlayer() {
        if (delegations.size() > 0) {
            return Collections.max(delegations.entrySet(), Map.Entry.comparingByValue()).getKey();
        }
        return null;
    }

}
