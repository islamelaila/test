package services.impl;

import models.Customer;
import models.Order;
import models.Dish;
import services.interfaces.CustomerService;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of CustomerService interface
 */
public class CustomerServiceImpl implements CustomerService {
    
    private Map<String, Customer> customers;
    private Map<String, Order> orders;
    private int customerIdCounter;
    private int orderIdCounter;

    public CustomerServiceImpl() {
        this.customers = new HashMap<>();
        this.orders = new HashMap<>();
        this.customerIdCounter = 1;
        this.orderIdCounter = 1;
    }

    @Override
    public Customer createCustomer(String name, String email, String phone, String address) {
        String customerId = "CUST" + String.format("%04d", customerIdCounter++);
        Customer customer = new Customer(customerId, name, email, phone, address);
        customers.put(customerId, customer);
        return customer;
    }

    @Override
    public Customer getCustomerById(String customerId) {
        return customers.get(customerId);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        if (customers.containsKey(customer.getId())) {
            customers.put(customer.getId(), customer);
            return customer;
        }
        return null;
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        return customers.remove(customerId) != null;
    }

    @Override
    public Order createOrder(String customerId, String deliveryAddress) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found: " + customerId);
        }
        
        String orderId = "ORD" + String.format("%04d", orderIdCounter++);
        Order order = new Order(orderId, customerId, deliveryAddress);
        orders.put(orderId, order);
        return order;
    }

    @Override
    public Order addItemToOrder(String orderId, Dish dish, int quantity) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new IllegalStateException("Cannot modify order after it has been placed");
        }
        
        order.addItem(dish, quantity);
        return order;
    }

    @Override
    public Order removeItemFromOrder(String orderId, Dish dish) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new IllegalStateException("Cannot modify order after it has been placed");
        }
        
        order.removeItem(dish);
        return order;
    }

    @Override
    public Order updateOrderItemQuantity(String orderId, Dish dish, int quantity) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        if (order.getStatus() != Order.OrderStatus.PENDING) {
            throw new IllegalStateException("Cannot modify order after it has been placed");
        }
        
        order.updateItemQuantity(dish, quantity);
        return order;
    }

    @Override
    public Order placeOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        if (order.getTotalItems() == 0) {
            throw new IllegalStateException("Cannot place empty order");
        }
        
        order.setStatus(Order.OrderStatus.CONFIRMED);
        
        // Add to customer's order history
        Customer customer = customers.get(order.getCustomerId());
        if (customer != null) {
            customer.addOrderToHistory(order);
            // Award loyalty points (1 point per dollar spent)
            customer.addLoyaltyPoints(order.getTotalAmount());
        }
        
        return order;
    }

    @Override
    public Order cancelOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        if (order.getStatus() == Order.OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot cancel delivered order");
        }
        
        order.setStatus(Order.OrderStatus.CANCELLED);
        return order;
    }

    @Override
    public Order getOrderById(String orderId) {
        return orders.get(orderId);
    }

    @Override
    public List<Order> getCustomerOrders(String customerId) {
        return orders.values().stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .filter(order -> order.getStatus() != Order.OrderStatus.DELIVERED)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getCustomerOrderHistory(String customerId) {
        Customer customer = customers.get(customerId);
        return customer != null ? customer.getOrderHistory() : new ArrayList<>();
    }

    @Override
    public void addLoyaltyPoints(String customerId, double points) {
        Customer customer = customers.get(customerId);
        if (customer != null) {
            customer.addLoyaltyPoints(points);
        }
    }

    @Override
    public void redeemLoyaltyPoints(String customerId, double points) {
        Customer customer = customers.get(customerId);
        if (customer != null) {
            customer.redeemLoyaltyPoints(points);
        }
    }

    @Override
    public double getLoyaltyPoints(String customerId) {
        Customer customer = customers.get(customerId);
        return customer != null ? customer.getLoyaltyPoints() : 0.0;
    }

    @Override
    public void updateCustomerProfile(String customerId, String name, String email, String phone, String address) {
        Customer customer = customers.get(customerId);
        if (customer != null) {
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhone(phone);
            customer.setAddress(address);
        }
    }

    @Override
    public List<Order> getRecentOrders(String customerId, int limit) {
        return getCustomerOrderHistory(customerId).stream()
                .sorted((o1, o2) -> o2.getOrderTime().compareTo(o1.getOrderTime()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<Dish> getRecommendedDishes(String customerId) {
        // Simple recommendation based on order history
        Customer customer = customers.get(customerId);
        if (customer == null) {
            return new ArrayList<>();
        }
        
        Map<Dish, Integer> dishFrequency = new HashMap<>();
        
        for (Order order : customer.getOrderHistory()) {
            for (Dish dish : order.getAllDishes()) {
                dishFrequency.put(dish, dishFrequency.getOrDefault(dish, 0) + 1);
            }
        }
        
        return dishFrequency.entrySet().stream()
                .sorted(Map.Entry.<Dish, Integer>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}