package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Customer class representing a restaurant customer
 */
public class Customer extends User {
    private String address;
    private List<Order> orderHistory;
    private double loyaltyPoints;

    public Customer(String id, String name, String email, String phone, String address) {
        super(id, name, email, phone);
        this.address = address;
        this.orderHistory = new ArrayList<>();
        this.loyaltyPoints = 0.0;
    }

    @Override
    public String getRole() {
        return "CUSTOMER";
    }

    // Getters
    public String getAddress() {
        return address;
    }

    public List<Order> getOrderHistory() {
        return new ArrayList<>(orderHistory);
    }

    public double getLoyaltyPoints() {
        return loyaltyPoints;
    }

    // Setters
    public void setAddress(String address) {
        this.address = address;
    }

    public void addLoyaltyPoints(double points) {
        this.loyaltyPoints += points;
    }

    public void redeemLoyaltyPoints(double points) {
        if (points <= this.loyaltyPoints) {
            this.loyaltyPoints -= points;
        } else {
            throw new IllegalArgumentException("Insufficient loyalty points");
        }
    }

    // Order management
    public void addOrderToHistory(Order order) {
        this.orderHistory.add(order);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", address='" + address + '\'' +
                ", loyaltyPoints=" + loyaltyPoints +
                ", totalOrders=" + orderHistory.size() +
                '}';
    }
}