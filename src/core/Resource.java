package core;

public class Resource {

    public enum Type {
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
}
