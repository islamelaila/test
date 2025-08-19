package restaurant.services.impl;

import restaurant.models.DeliveryGuy;
import restaurant.models.Order;
import restaurant.services.interfaces.DeliveryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryServiceImpl implements DeliveryService {
    private final Map<String, DeliveryGuy> deliveryGuyIdToDeliveryGuy = new HashMap<>();
    private final Map<String, List<Order>> deliveryGuyIdToOrders = new HashMap<>();

    @Override
    public DeliveryGuy registerDeliveryGuy(String fullName, String phoneNumber, String vehicle) {
        DeliveryGuy deliveryGuy = new DeliveryGuy(fullName, phoneNumber, vehicle);
        deliveryGuyIdToDeliveryGuy.put(deliveryGuy.getId(), deliveryGuy);
        deliveryGuyIdToOrders.put(deliveryGuy.getId(), new ArrayList<>());
        return deliveryGuy;
    }

    @Override
    public void assignDeliveryGuyToOrder(DeliveryGuy deliveryGuy, Order order) {
        order.setAssignedDeliveryGuy(deliveryGuy);
        order.setStatus(Order.Status.OUT_FOR_DELIVERY);
        deliveryGuyIdToOrders.computeIfAbsent(deliveryGuy.getId(), k -> new ArrayList<>()).add(order);
    }

    @Override
    public void markOrderOutForDelivery(Order order) {
        order.setStatus(Order.Status.OUT_FOR_DELIVERY);
    }

    @Override
    public void markOrderDelivered(Order order) {
        order.setStatus(Order.Status.DELIVERED);
    }

    @Override
    public List<Order> getOrdersAssignedToDeliveryGuy(DeliveryGuy deliveryGuy) {
        return new ArrayList<>(deliveryGuyIdToOrders.getOrDefault(deliveryGuy.getId(), List.of()));
    }
}

