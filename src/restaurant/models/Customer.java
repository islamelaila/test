package restaurant.models;

public class Customer extends User {
    private String address;

    public Customer(String fullName, String phoneNumber, String address) {
        super(fullName, phoneNumber);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

