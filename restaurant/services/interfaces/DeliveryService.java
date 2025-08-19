package services.interfaces;

import models.DeliveryGuy;
import models.Order;
import java.util.List;

/**
 * Service interface for delivery-related operations
 */
public interface DeliveryService {
    
    // Delivery guy management
    DeliveryGuy createDeliveryGuy(String name, String email, String phone, String vehicleType, String licenseNumber);
    DeliveryGuy getDeliveryGuyById(String deliveryGuyId);
    List<DeliveryGuy> getAllDeliveryGuys();
    DeliveryGuy updateDeliveryGuy(DeliveryGuy deliveryGuy);
    boolean deleteDeliveryGuy(String deliveryGuyId);
    
    // Availability management
    void setDeliveryGuyAvailability(String deliveryGuyId, boolean isAvailable);
    List<DeliveryGuy> getAvailableDeliveryGuys();
    DeliveryGuy assignDeliveryGuyToOrder(String orderId, String deliveryZone);
    
    // Zone management
    void addDeliveryZone(String deliveryGuyId, String zone);
    void removeDeliveryZone(String deliveryGuyId, String zone);
    List<String> getDeliveryZones(String deliveryGuyId);
    List<DeliveryGuy> getDeliveryGuysForZone(String zone);
    
    // Order delivery management
    List<Order> getDeliveryGuyOrders(String deliveryGuyId);
    List<Order> getReadyForDeliveryOrders();
    Order startDelivery(String orderId, String deliveryGuyId);
    Order markOrderDelivered(String orderId);
    Order updateDeliveryStatus(String orderId, Order.OrderStatus status);
    
    // Performance tracking
    int getDeliveriesCompletedToday(String deliveryGuyId);
    double getDeliveryGuyRating(String deliveryGuyId);
    void updateDeliveryGuyRating(String deliveryGuyId, double rating);
    List<Order> getDeliveryHistory(String deliveryGuyId);
    
    // Route optimization and tracking
    double calculateEstimatedDeliveryTime(String deliveryAddress, String deliveryGuyId);
    List<Order> getOptimalDeliveryRoute(String deliveryGuyId);
    void updateDeliveryLocation(String deliveryGuyId, String currentLocation);
    
    // Statistics and reporting
    double getAverageDeliveryTime();
    List<DeliveryGuy> getTopPerformingDeliveryGuys(int limit);
    int getTotalDeliveriesToday();
}