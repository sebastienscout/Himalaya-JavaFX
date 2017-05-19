package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Region implements Cloneable {

    private int id;
    private HashMap<Player, Integer> delegations;

    public Region(int id) {
        this.id = id;
        delegations = new HashMap<>();
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

    public Object clone() {
        Region r = null;

        try {
            // On récupère l'instance à renvoyer par l'appel de la 
            // méthode super.clone()
            r = (Region) super.clone();
        } catch (CloneNotSupportedException ex) {
            // Ne devrait jamais arriver car nous implémentons 
            // l'interface Cloneable
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        r.id = id;
        
        r.delegations = new HashMap<>(delegations.size());
        for (Map.Entry<Player, Integer> entry : delegations.entrySet()) {
            Player player = entry.getKey();
            Integer nbDelegations = entry.getValue();
           
            r.delegations.put(player, nbDelegations);
        }

        return r;
    }

}
