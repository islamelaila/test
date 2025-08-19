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
    private final Map<String, Customer> customerIdToCustomer = new HashMap<>();
    private final Map<String, List<Order>> customerIdToOrders = new HashMap<>();

    @Override
    public Customer registerCustomer(String fullName, String phoneNumber, String address) {
        Customer customer = new Customer(fullName, phoneNumber, address);
        customerIdToCustomer.put(customer.getId(), customer);
        customerIdToOrders.put(customer.getId(), new ArrayList<>());
        return customer;
    }

    @Override
    public Order placeOrder(Customer customer, List<Dish> dishes) {
        Order order = new Order(customer, dishes);
        customerIdToOrders.computeIfAbsent(customer.getId(), k -> new ArrayList<>()).add(order);
        return order;
    }

    @Override
    public List<Order> getOrdersByCustomer(Customer customer) {
        return new ArrayList<>(customerIdToOrders.getOrDefault(customer.getId(), List.of()));
    }
}

