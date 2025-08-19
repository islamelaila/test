package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Order class representing a customer order
 */
public class Order {
    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        PREPARING,
        READY,
        OUT_FOR_DELIVERY,
        DELIVERED,
        CANCELLED
    }

    private String id;
    private String customerId;
    private Map<Dish, Integer> items; // Dish and quantity
    private OrderStatus status;
    private LocalDateTime orderTime;
    private LocalDateTime estimatedDeliveryTime;
    private LocalDateTime actualDeliveryTime;
    private String deliveryAddress;
    private String assignedChefId;
    private String assignedDeliveryGuyId;
    private double totalAmount;
    private double discount;
    private String specialInstructions;

    public Order(String id, String customerId, String deliveryAddress) {
        this.id = id;
        this.customerId = customerId;
        this.deliveryAddress = deliveryAddress;
        this.items = new HashMap<>();
        this.status = OrderStatus.PENDING;
        this.orderTime = LocalDateTime.now();
        this.totalAmount = 0.0;
        this.discount = 0.0;
        this.specialInstructions = "";
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Map<Dish, Integer> getItems() {
        return new HashMap<>(items);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public LocalDateTime getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public LocalDateTime getActualDeliveryTime() {
        return actualDeliveryTime;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getAssignedChefId() {
        return assignedChefId;
    }

    public String getAssignedDeliveryGuyId() {
        return assignedDeliveryGuyId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    // Setters
    public void setStatus(OrderStatus status) {
        this.status = status;
        if (status == OrderStatus.DELIVERED && actualDeliveryTime == null) {
            this.actualDeliveryTime = LocalDateTime.now();
        }
    }

    public void setEstimatedDeliveryTime(LocalDateTime estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public void setActualDeliveryTime(LocalDateTime actualDeliveryTime) {
        this.actualDeliveryTime = actualDeliveryTime;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setAssignedChefId(String assignedChefId) {
        this.assignedChefId = assignedChefId;
    }

    public void setAssignedDeliveryGuyId(String assignedDeliveryGuyId) {
        this.assignedDeliveryGuyId = assignedDeliveryGuyId;
    }

    public void setDiscount(double discount) {
        if (discount >= 0) {
            this.discount = discount;
            calculateTotalAmount();
        } else {
            throw new IllegalArgumentException("Discount cannot be negative");
        }
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    // Item management
    public void addItem(Dish dish, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        items.put(dish, items.getOrDefault(dish, 0) + quantity);
        calculateTotalAmount();
    }

    public void removeItem(Dish dish) {
        items.remove(dish);
        calculateTotalAmount();
    }

    public void updateItemQuantity(Dish dish, int quantity) {
        if (quantity <= 0) {
            removeItem(dish);
        } else {
            items.put(dish, quantity);
        }
        calculateTotalAmount();
    }

    public int getItemQuantity(Dish dish) {
        return items.getOrDefault(dish, 0);
    }

    public List<Dish> getAllDishes() {
        return new ArrayList<>(items.keySet());
    }

    public int getTotalItems() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }

    // Calculate total amount
    private void calculateTotalAmount() {
        double subtotal = items.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
        this.totalAmount = subtotal - discount;
    }

    // Calculate estimated preparation time
    public int getEstimatedPreparationTime() {
        return items.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPreparationTimeMinutes() * entry.getValue())
                .max()
                .orElse(0);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", status=" + status +
                ", orderTime=" + orderTime +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", totalAmount=" + totalAmount +
                ", totalItems=" + getTotalItems() +
                ", assignedChefId='" + assignedChefId + '\'' +
                ", assignedDeliveryGuyId='" + assignedDeliveryGuyId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (Order) obj;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}