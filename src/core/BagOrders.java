package core;

import java.util.ArrayList;

public class BagOrders {

    private ArrayList<Order> orders = new ArrayList<>();

    public BagOrders() {
        for (int i = 0; i < 40; i++) {
            orders.add(new Order());
        }
    }

    public Order takeRandom() {
        int rand = (int) (Math.random() * orders.size());
        Order result = orders.get(rand);
        orders.remove(result);
        return result;
    }
}
