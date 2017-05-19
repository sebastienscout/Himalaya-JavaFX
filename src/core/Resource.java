package core;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Resource implements Cloneable {

    public enum Type implements Cloneable{
        sel,
        orge,
        the,
        jade,
        or
    }
    private Type type;

    public Resource(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "" + type;
    }

    //Valeur des resources
    public int getValue() {
        int valeur = 0;
        switch (type) {
            case sel:
                valeur = 1;
                break;
            case orge:
                valeur = 2;
                break;
            case the:
                valeur = 3;
                break;
            case jade:
                valeur = 4;
                break;
            case or:
                valeur = 5;
                break;
        }
        return valeur;
    }

    public Object clone() {
        Resource r = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la 
            // méthode super.clone()
            r = (Resource) super.clone();
        } catch (CloneNotSupportedException ex) {
            // Ne devrait jamais arriver car nous implémentons 
            // l'interface Cloneable
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }

        // On clone les attributs
         r.type = type;
        return r;
    }
}
