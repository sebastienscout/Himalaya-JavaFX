package core;

import static com.sun.org.apache.xalan.internal.lib.ExsltDynamic.map;
import java.awt.RenderingHints.Key;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static jdk.nashorn.internal.objects.NativeArray.map;

public class Region {

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
        delegations.put(r, nb);
    }
    
    public Player getMaxDelegationPlayer(){
        if(delegations.size() > 0){
            return Collections.max(delegations.entrySet(), Map.Entry.comparingByValue()).getKey();
        }
        return null;
    }
}
