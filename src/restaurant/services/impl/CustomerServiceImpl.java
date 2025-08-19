package restaurant.services.impl;

import restaurant.models.Customer;
import restaurant.models.Dish;
import restaurant.models.Order;
import restaurant.services.interfaces.CustomerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerServiceImpl implements CustomerService {
    private final Map<Integer, Customer> customersById = new HashMap<>();
    private int nextOrderId = 1;

    @Override
    public Customer registerCustomer(int id, String name) {
        Customer customer = new Customer(id, name);
        customersById.put(id, customer);
        return customer;
    }

    @Override
    public Order placeOrder(Customer customer, List<Dish> dishes) {
        if (customer == null) throw new IllegalArgumentException("customer must not be null");
        if (dishes == null || dishes.isEmpty()) throw new IllegalArgumentException("dishes must not be empty");
        List<Dish> copy = new ArrayList<>(dishes);
        Order order = new Order(nextOrderId++, customer, copy);
        return order;
    }
}

