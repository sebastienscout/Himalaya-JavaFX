package core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Resource {

    sel,
    orge,
    the,
    jade,
    or;

    private static final List<Resource> VALUES
            = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Resource getRandomResource() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

}
