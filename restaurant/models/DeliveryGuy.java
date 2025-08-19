package models;

import java.util.ArrayList;
import java.util.List;

/**
 * DeliveryGuy class representing a delivery person
 */
public class DeliveryGuy extends User {
    private String vehicleType;
    private String licenseNumber;
    private boolean isAvailable;
    private List<String> deliveryZones;
    private int totalDeliveries;
    private double rating;

    public DeliveryGuy(String id, String name, String email, String phone, String vehicleType, String licenseNumber) {
        super(id, name, email, phone);
        this.vehicleType = vehicleType;
        this.licenseNumber = licenseNumber;
        this.isAvailable = true;
        this.deliveryZones = new ArrayList<>();
        this.totalDeliveries = 0;
        this.rating = 5.0;
    }

    @Override
    public String getRole() {
        return "DELIVERY_GUY";
    }

    // Getters
    public String getVehicleType() {
        return vehicleType;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public List<String> getDeliveryZones() {
        return new ArrayList<>(deliveryZones);
    }

    public int getTotalDeliveries() {
        return totalDeliveries;
    }

    public double getRating() {
        return rating;
    }

    // Setters
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setRating(double rating) {
        if (rating >= 0 && rating <= 5) {
            this.rating = rating;
        } else {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
    }

    // Zone management
    public void addDeliveryZone(String zone) {
        if (!deliveryZones.contains(zone)) {
            deliveryZones.add(zone);
        }
    }

    public void removeDeliveryZone(String zone) {
        deliveryZones.remove(zone);
    }

    public boolean canDeliverToZone(String zone) {
        return deliveryZones.contains(zone);
    }

    // Delivery tracking
    public void incrementDeliveries() {
        this.totalDeliveries++;
    }

    @Override
    public String toString() {
        return "DeliveryGuy{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", isAvailable=" + isAvailable +
                ", deliveryZones=" + deliveryZones +
                ", totalDeliveries=" + totalDeliveries +
                ", rating=" + rating +
                '}';
    }
}