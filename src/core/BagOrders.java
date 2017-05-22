package core;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BagOrders implements Cloneable {

    private ArrayList<Order> orders = new ArrayList<>();

    public BagOrders(int nb) {
        for (int i = 0; i < nb; i++) {
            orders.add(new Order());
        }
    }

    public Order takeRandom() {
        int rand = (int) (Math.random() * orders.size());
        Order result = orders.get(rand);
        orders.remove(result);
        return result;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public Object clone() {
        BagOrders bo = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la 
            // méthode super.clone()
            bo = (BagOrders) super.clone();
        } catch (CloneNotSupportedException ex) {
            // Ne devrait jamais arriver car nous implémentons 
            // l'interface Cloneable
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }

        // On clone les attributs
        bo.orders = new ArrayList<>(orders.size());
        for (Order order : orders) {
            bo.orders.add((Order) order.clone());
        }

        return bo;
    }
}
