package restaurant.services.impl;

import restaurant.models.Chef;
import restaurant.models.Order;
import restaurant.services.interfaces.ChefService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChefServiceImpl implements ChefService {
    private final Map<String, Chef> chefIdToChef = new HashMap<>();
    private final Map<String, List<Order>> chefIdToOrders = new HashMap<>();

    @Override
    public Chef registerChef(String fullName, String phoneNumber, String specialty) {
        Chef chef = new Chef(fullName, phoneNumber, specialty);
        chefIdToChef.put(chef.getId(), chef);
        chefIdToOrders.put(chef.getId(), new ArrayList<>());
        return chef;
    }

    @Override
    public void assignChefToOrder(Chef chef, Order order) {
        order.setAssignedChef(chef);
        order.setStatus(Order.Status.PREPARING);
        chefIdToOrders.computeIfAbsent(chef.getId(), k -> new ArrayList<>()).add(order);
    }

    @Override
    public void markOrderPreparing(Order order) {
        order.setStatus(Order.Status.PREPARING);
    }

    @Override
    public void markOrderReadyForDelivery(Order order) {
        order.setStatus(Order.Status.READY_FOR_DELIVERY);
    }

    @Override
    public List<Order> getOrdersAssignedToChef(Chef chef) {
        return new ArrayList<>(chefIdToOrders.getOrDefault(chef.getId(), List.of()));
    }
}

