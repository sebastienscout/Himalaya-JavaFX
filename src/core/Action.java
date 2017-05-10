package core;

public class Action {

    public enum Type {

        soil,
        stone,
        ice,
        transaction,
        bartering,
        offering,
        delegation,
        pause

    }

    private Type type;
    private int id;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (type.equals(Type.delegation)) {
            this.id = id;
        }
    }

    public Action(Type type) {
        this.type = type;
        this.id = 0;
    }

    public Action(Type type, int id) {
        this.type = type;
        if (type.equals(Type.delegation)) {
            this.id = id;
        }
    }

    @Override
    public String toString() {
        return "Action{" + "type=" + type + ", id=" + id + '}';
    }

}
