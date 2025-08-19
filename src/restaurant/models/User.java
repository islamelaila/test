package restaurant.models;

import java.util.Objects;
import java.util.UUID;

public abstract class User {
    private final String id;
    private String fullName;
    private String phoneNumber;

    public User(String fullName, String phoneNumber) {
        this(UUID.randomUUID().toString(), fullName, phoneNumber);
    }

    public User(String id, String fullName, String phoneNumber) {
        this.id = Objects.requireNonNull(id, "id");
        this.fullName = Objects.requireNonNull(fullName, "fullName");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber");
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = Objects.requireNonNull(fullName, "fullName");
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}

