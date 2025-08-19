package restaurant.services.impl;

import restaurant.models.DeliveryGuy;
import restaurant.models.Order;
import restaurant.services.interfaces.DeliveryService;

import java.util.HashMap;
import java.util.Map;

public class DeliveryServiceImpl implements DeliveryService {
    private final Map<Integer, DeliveryGuy> deliveryGuysById = new HashMap<>();

    @Override
    public DeliveryGuy registerDeliveryGuy(int id, String name) {
        DeliveryGuy deliveryGuy = new DeliveryGuy(id, name);
        deliveryGuysById.put(id, deliveryGuy);
        return deliveryGuy;
    }

    @Override
    public void pickUpOrder(DeliveryGuy deliveryGuy, Order order) {
        if (deliveryGuy == null || order == null) throw new IllegalArgumentException("deliveryGuy and order must not be null");
        order.markOutForDelivery(deliveryGuy);
    }

    @Override
    public void markDelivered(Order order) {
        if (order == null) throw new IllegalArgumentException("order must not be null");
        order.markDelivered();
    }
}

