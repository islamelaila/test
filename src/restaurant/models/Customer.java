package restaurant.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer extends User {
    private final List<Order> orderHistory = new ArrayList<>();

    public Customer(int id, String name) {
        super(id, name);
    }

    public List<Order> getOrderHistory() {
        return Collections.unmodifiableList(orderHistory);
    }

    public void addOrderToHistory(Order order) {
        if (order != null) {
            orderHistory.add(order);
        }
    }
}

