package restaurant.models;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Order {
    public enum Status {
        CREATED, PREPARING, READY_FOR_DELIVERY, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
    }

    private final String id;
    private final Customer customer;
    private final List<Dish> dishes;
    private final Instant createdAt;
    private Status status;
    private Chef assignedChef;
    private DeliveryGuy assignedDeliveryGuy;

    public Order(Customer customer, List<Dish> dishes) {
        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.dishes = new ArrayList<>(dishes);
        this.createdAt = Instant.now();
        this.status = Status.CREATED;
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Dish> getDishes() {
        return Collections.unmodifiableList(dishes);
    }

    public BigDecimal getTotal() {
        return dishes.stream()
                .map(Dish::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Chef getAssignedChef() {
        return assignedChef;
    }

    public void setAssignedChef(Chef assignedChef) {
        this.assignedChef = assignedChef;
    }

    public DeliveryGuy getAssignedDeliveryGuy() {
        return assignedDeliveryGuy;
    }

    public void setAssignedDeliveryGuy(DeliveryGuy assignedDeliveryGuy) {
        this.assignedDeliveryGuy = assignedDeliveryGuy;
    }
}

