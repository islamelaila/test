package restaurant.services.impl;

import restaurant.models.Chef;
import restaurant.models.Order;
import restaurant.services.interfaces.ChefService;

import java.util.HashMap;
import java.util.Map;

public class ChefServiceImpl implements ChefService {
    private final Map<Integer, Chef> chefsById = new HashMap<>();

    @Override
    public Chef registerChef(int id, String name) {
        Chef chef = new Chef(id, name);
        chefsById.put(id, chef);
        return chef;
    }

    @Override
    public void startPreparingOrder(Chef chef, Order order) {
        if (chef == null || order == null) throw new IllegalArgumentException("chef and order must not be null");
        order.markPreparing(chef);
    }

    @Override
    public void markOrderReady(Order order) {
        if (order == null) throw new IllegalArgumentException("order must not be null");
        order.markReadyForDelivery();
    }
}

