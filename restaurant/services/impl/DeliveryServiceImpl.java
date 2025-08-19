package services.impl;

import models.DeliveryGuy;
import models.Order;
import services.interfaces.DeliveryService;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Implementation of DeliveryService interface
 */
public class DeliveryServiceImpl implements DeliveryService {
    
    private Map<String, DeliveryGuy> deliveryGuys;
    private Map<String, Order> orders;
    private int deliveryGuyIdCounter;

    public DeliveryServiceImpl() {
        this.deliveryGuys = new HashMap<>();
        this.orders = new HashMap<>();
        this.deliveryGuyIdCounter = 1;
    }

    // Setter for orders (to be injected by a dependency injection framework or main class)
    public void setOrders(Map<String, Order> orders) {
        this.orders = orders;
    }

    @Override
    public DeliveryGuy createDeliveryGuy(String name, String email, String phone, String vehicleType, String licenseNumber) {
        String deliveryGuyId = "DEL" + String.format("%04d", deliveryGuyIdCounter++);
        DeliveryGuy deliveryGuy = new DeliveryGuy(deliveryGuyId, name, email, phone, vehicleType, licenseNumber);
        deliveryGuys.put(deliveryGuyId, deliveryGuy);
        return deliveryGuy;
    }

    @Override
    public DeliveryGuy getDeliveryGuyById(String deliveryGuyId) {
        return deliveryGuys.get(deliveryGuyId);
    }

    @Override
    public List<DeliveryGuy> getAllDeliveryGuys() {
        return new ArrayList<>(deliveryGuys.values());
    }

    @Override
    public DeliveryGuy updateDeliveryGuy(DeliveryGuy deliveryGuy) {
        if (deliveryGuys.containsKey(deliveryGuy.getId())) {
            deliveryGuys.put(deliveryGuy.getId(), deliveryGuy);
            return deliveryGuy;
        }
        return null;
    }

    @Override
    public boolean deleteDeliveryGuy(String deliveryGuyId) {
        return deliveryGuys.remove(deliveryGuyId) != null;
    }

    @Override
    public void setDeliveryGuyAvailability(String deliveryGuyId, boolean isAvailable) {
        DeliveryGuy deliveryGuy = deliveryGuys.get(deliveryGuyId);
        if (deliveryGuy != null) {
            deliveryGuy.setAvailable(isAvailable);
        }
    }

    @Override
    public List<DeliveryGuy> getAvailableDeliveryGuys() {
        return deliveryGuys.values().stream()
                .filter(DeliveryGuy::isAvailable)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryGuy assignDeliveryGuyToOrder(String orderId, String deliveryZone) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }

        List<DeliveryGuy> availableDeliveryGuys = getDeliveryGuysForZone(deliveryZone).stream()
                .filter(DeliveryGuy::isAvailable)
                .collect(Collectors.toList());

        if (availableDeliveryGuys.isEmpty()) {
            throw new IllegalStateException("No available delivery guys for zone: " + deliveryZone);
        }

        // Simple assignment: pick the delivery guy with highest rating
        DeliveryGuy assignedDeliveryGuy = availableDeliveryGuys.stream()
                .max(Comparator.comparingDouble(DeliveryGuy::getRating))
                .orElse(availableDeliveryGuys.get(0));

        order.setAssignedDeliveryGuyId(assignedDeliveryGuy.getId());
        return assignedDeliveryGuy;
    }

    @Override
    public void addDeliveryZone(String deliveryGuyId, String zone) {
        DeliveryGuy deliveryGuy = deliveryGuys.get(deliveryGuyId);
        if (deliveryGuy != null) {
            deliveryGuy.addDeliveryZone(zone);
        }
    }

    @Override
    public void removeDeliveryZone(String deliveryGuyId, String zone) {
        DeliveryGuy deliveryGuy = deliveryGuys.get(deliveryGuyId);
        if (deliveryGuy != null) {
            deliveryGuy.removeDeliveryZone(zone);
        }
    }

    @Override
    public List<String> getDeliveryZones(String deliveryGuyId) {
        DeliveryGuy deliveryGuy = deliveryGuys.get(deliveryGuyId);
        return deliveryGuy != null ? deliveryGuy.getDeliveryZones() : new ArrayList<>();
    }

    @Override
    public List<DeliveryGuy> getDeliveryGuysForZone(String zone) {
        return deliveryGuys.values().stream()
                .filter(deliveryGuy -> deliveryGuy.canDeliverToZone(zone))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getDeliveryGuyOrders(String deliveryGuyId) {
        return orders.values().stream()
                .filter(order -> deliveryGuyId.equals(order.getAssignedDeliveryGuyId()))
                .filter(order -> order.getStatus() == Order.OrderStatus.OUT_FOR_DELIVERY)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getReadyForDeliveryOrders() {
        return orders.values().stream()
                .filter(order -> order.getStatus() == Order.OrderStatus.READY)
                .collect(Collectors.toList());
    }

    @Override
    public Order startDelivery(String orderId, String deliveryGuyId) {
        Order order = orders.get(orderId);
        DeliveryGuy deliveryGuy = deliveryGuys.get(deliveryGuyId);

        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        if (deliveryGuy == null) {
            throw new IllegalArgumentException("Delivery guy not found: " + deliveryGuyId);
        }
        if (order.getStatus() != Order.OrderStatus.READY) {
            throw new IllegalStateException("Order must be ready to start delivery");
        }

        order.setStatus(Order.OrderStatus.OUT_FOR_DELIVERY);
        order.setAssignedDeliveryGuyId(deliveryGuyId);
        
        // Set estimated delivery time (simple calculation)
        LocalDateTime estimatedTime = LocalDateTime.now().plusMinutes(30); // Default 30 minutes
        order.setEstimatedDeliveryTime(estimatedTime);

        return order;
    }

    @Override
    public Order markOrderDelivered(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        if (order.getStatus() != Order.OrderStatus.OUT_FOR_DELIVERY) {
            throw new IllegalStateException("Order must be out for delivery to mark as delivered");
        }

        order.setStatus(Order.OrderStatus.DELIVERED);
        order.setActualDeliveryTime(LocalDateTime.now());

        // Increment delivery count for the delivery guy
        String deliveryGuyId = order.getAssignedDeliveryGuyId();
        if (deliveryGuyId != null) {
            DeliveryGuy deliveryGuy = deliveryGuys.get(deliveryGuyId);
            if (deliveryGuy != null) {
                deliveryGuy.incrementDeliveries();
            }
        }

        return order;
    }

    @Override
    public Order updateDeliveryStatus(String orderId, Order.OrderStatus status) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.setStatus(status);
        }
        return order;
    }

    @Override
    public int getDeliveriesCompletedToday(String deliveryGuyId) {
        LocalDate today = LocalDate.now();
        return (int) orders.values().stream()
                .filter(order -> deliveryGuyId.equals(order.getAssignedDeliveryGuyId()))
                .filter(order -> order.getStatus() == Order.OrderStatus.DELIVERED)
                .filter(order -> order.getActualDeliveryTime() != null)
                .filter(order -> order.getActualDeliveryTime().toLocalDate().equals(today))
                .count();
    }

    @Override
    public double getDeliveryGuyRating(String deliveryGuyId) {
        DeliveryGuy deliveryGuy = deliveryGuys.get(deliveryGuyId);
        return deliveryGuy != null ? deliveryGuy.getRating() : 0.0;
    }

    @Override
    public void updateDeliveryGuyRating(String deliveryGuyId, double rating) {
        DeliveryGuy deliveryGuy = deliveryGuys.get(deliveryGuyId);
        if (deliveryGuy != null) {
            deliveryGuy.setRating(rating);
        }
    }

    @Override
    public List<Order> getDeliveryHistory(String deliveryGuyId) {
        return orders.values().stream()
                .filter(order -> deliveryGuyId.equals(order.getAssignedDeliveryGuyId()))
                .filter(order -> order.getStatus() == Order.OrderStatus.DELIVERED)
                .sorted((o1, o2) -> o2.getActualDeliveryTime().compareTo(o1.getActualDeliveryTime()))
                .collect(Collectors.toList());
    }

    @Override
    public double calculateEstimatedDeliveryTime(String deliveryAddress, String deliveryGuyId) {
        // Simple estimation based on distance (mock implementation)
        // In a real system, this would use GPS/mapping services
        DeliveryGuy deliveryGuy = deliveryGuys.get(deliveryGuyId);
        if (deliveryGuy == null) {
            return 30.0; // Default 30 minutes
        }

        // Simple calculation based on vehicle type
        double baseTime = 20.0; // Base time in minutes
        switch (deliveryGuy.getVehicleType().toLowerCase()) {
            case "bike":
            case "bicycle":
                return baseTime + 10.0;
            case "motorcycle":
                return baseTime + 5.0;
            case "car":
                return baseTime;
            default:
                return baseTime + 15.0;
        }
    }

    @Override
    public List<Order> getOptimalDeliveryRoute(String deliveryGuyId) {
        // Simple implementation: return orders sorted by order time
        // In a real system, this would use route optimization algorithms
        return getDeliveryGuyOrders(deliveryGuyId).stream()
                .sorted(Comparator.comparing(Order::getOrderTime))
                .collect(Collectors.toList());
    }

    @Override
    public void updateDeliveryLocation(String deliveryGuyId, String currentLocation) {
        // In a real system, this would update the delivery guy's current GPS location
        // For this implementation, we'll just log it
        System.out.println("Updated location for delivery guy " + deliveryGuyId + ": " + currentLocation);
    }

    @Override
    public double getAverageDeliveryTime() {
        List<Order> deliveredOrders = orders.values().stream()
                .filter(order -> order.getStatus() == Order.OrderStatus.DELIVERED)
                .filter(order -> order.getActualDeliveryTime() != null && order.getOrderTime() != null)
                .collect(Collectors.toList());

        if (deliveredOrders.isEmpty()) {
            return 0.0;
        }

        return deliveredOrders.stream()
                .mapToLong(order -> ChronoUnit.MINUTES.between(order.getOrderTime(), order.getActualDeliveryTime()))
                .average()
                .orElse(0.0);
    }

    @Override
    public List<DeliveryGuy> getTopPerformingDeliveryGuys(int limit) {
        return deliveryGuys.values().stream()
                .sorted(Comparator.comparingDouble(DeliveryGuy::getRating).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalDeliveriesToday() {
        LocalDate today = LocalDate.now();
        return (int) orders.values().stream()
                .filter(order -> order.getStatus() == Order.OrderStatus.DELIVERED)
                .filter(order -> order.getActualDeliveryTime() != null)
                .filter(order -> order.getActualDeliveryTime().toLocalDate().equals(today))
                .count();
    }
}