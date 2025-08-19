package restaurant.models;

public class DeliveryGuy extends User {
    private String vehicle;

    public DeliveryGuy(String fullName, String phoneNumber, String vehicle) {
        super(fullName, phoneNumber);
        this.vehicle = vehicle;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
}

