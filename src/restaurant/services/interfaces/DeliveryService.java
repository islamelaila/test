package restaurant.services.interfaces;

import restaurant.models.DeliveryGuy;
import restaurant.models.Order;

import java.util.List;

public interface DeliveryService {
    DeliveryGuy registerDeliveryGuy(String fullName, String phoneNumber, String vehicle);
    void assignDeliveryGuyToOrder(DeliveryGuy deliveryGuy, Order order);
    void markOrderOutForDelivery(Order order);
    void markOrderDelivered(Order order);
    List<Order> getOrdersAssignedToDeliveryGuy(DeliveryGuy deliveryGuy);
}

