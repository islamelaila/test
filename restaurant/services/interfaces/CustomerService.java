package services.interfaces;

import models.Customer;
import models.Order;
import models.Dish;
import java.util.List;

/**
 * Service interface for customer-related operations
 */
public interface CustomerService {
    
    // Customer management
    Customer createCustomer(String name, String email, String phone, String address);
    Customer getCustomerById(String customerId);
    List<Customer> getAllCustomers();
    Customer updateCustomer(Customer customer);
    boolean deleteCustomer(String customerId);
    
    // Order management
    Order createOrder(String customerId, String deliveryAddress);
    Order addItemToOrder(String orderId, Dish dish, int quantity);
    Order removeItemFromOrder(String orderId, Dish dish);
    Order updateOrderItemQuantity(String orderId, Dish dish, int quantity);
    Order placeOrder(String orderId);
    Order cancelOrder(String orderId);
    Order getOrderById(String orderId);
    List<Order> getCustomerOrders(String customerId);
    List<Order> getCustomerOrderHistory(String customerId);
    
    // Loyalty points
    void addLoyaltyPoints(String customerId, double points);
    void redeemLoyaltyPoints(String customerId, double points);
    double getLoyaltyPoints(String customerId);
    
    // Customer preferences and profile
    void updateCustomerProfile(String customerId, String name, String email, String phone, String address);
    List<Order> getRecentOrders(String customerId, int limit);
    List<Dish> getRecommendedDishes(String customerId);
}