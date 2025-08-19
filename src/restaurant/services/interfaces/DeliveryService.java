package restaurant.services.interfaces;

import restaurant.models.DeliveryGuy;
import restaurant.models.Order;

public interface DeliveryService {
    DeliveryGuy registerDeliveryGuy(int id, String name);
    void pickUpOrder(DeliveryGuy deliveryGuy, Order order);
    void markDelivered(Order order);
}

