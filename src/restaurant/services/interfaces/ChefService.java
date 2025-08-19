package restaurant.services.interfaces;

import restaurant.models.Chef;
import restaurant.models.Order;

import java.util.List;

public interface ChefService {
    Chef registerChef(String fullName, String phoneNumber, String specialty);
    void assignChefToOrder(Chef chef, Order order);
    void markOrderPreparing(Order order);
    void markOrderReadyForDelivery(Order order);
    List<Order> getOrdersAssignedToChef(Chef chef);
}

