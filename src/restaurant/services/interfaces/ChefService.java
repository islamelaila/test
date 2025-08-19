package restaurant.services.interfaces;

import restaurant.models.Chef;
import restaurant.models.Order;

public interface ChefService {
    Chef registerChef(int id, String name);
    void startPreparingOrder(Chef chef, Order order);
    void markOrderReady(Order order);
}

