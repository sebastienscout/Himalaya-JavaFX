package core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Resource {

    public enum Type {
        sel,
        orge,
        the,
        jade,
        or
    }
    private Type type;

    public static Resource getRandomResource() {
        return null; //TODO
    }

    public Resource(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Resource{" + "type=" + type + '}';
    }
}
