package restaurant.models;

public class Chef extends User {
    private String specialty;

    public Chef(String fullName, String phoneNumber, String specialty) {
        super(fullName, phoneNumber);
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}

