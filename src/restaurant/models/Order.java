package restaurant.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    public enum Status { CREATED, PREPARING, READY_FOR_DELIVERY, OUT_FOR_DELIVERY, DELIVERED, CANCELLED }

    private final int id;
    private final Customer customer;
    private final List<Dish> dishes = new ArrayList<>();
    private final LocalDateTime createdAt;
    private Status status;
    private Chef preparedBy;
    private DeliveryGuy deliveredBy;

    public Order(int id, Customer customer, List<Dish> dishes) {
        if (customer == null) throw new IllegalArgumentException("customer must not be null");
        if (dishes == null || dishes.isEmpty()) throw new IllegalArgumentException("dishes must not be empty");
        this.id = id;
        this.customer = customer;
        this.dishes.addAll(dishes);
        this.createdAt = LocalDateTime.now();
        this.status = Status.CREATED;
    }

    public int getId() { return id; }
    public Customer getCustomer() { return customer; }
    public List<Dish> getDishes() { return Collections.unmodifiableList(dishes); }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Status getStatus() { return status; }
    public Chef getPreparedBy() { return preparedBy; }
    public DeliveryGuy getDeliveredBy() { return deliveredBy; }

    public double getTotalPrice() {
        return dishes.stream().mapToDouble(Dish::getPrice).sum();
    }

    public void markPreparing(Chef chef) {
        if (status != Status.CREATED) throw new IllegalStateException("Order not in CREATED state");
        if (chef == null) throw new IllegalArgumentException("chef must not be null");
        this.preparedBy = chef;
        this.status = Status.PREPARING;
    }

    public void markReadyForDelivery() {
        if (status != Status.PREPARING) throw new IllegalStateException("Order not in PREPARING state");
        this.status = Status.READY_FOR_DELIVERY;
    }

    public void markOutForDelivery(DeliveryGuy deliveryGuy) {
        if (status != Status.READY_FOR_DELIVERY) throw new IllegalStateException("Order not ready for delivery");
        if (deliveryGuy == null) throw new IllegalArgumentException("deliveryGuy must not be null");
        this.deliveredBy = deliveryGuy;
        this.status = Status.OUT_FOR_DELIVERY;
    }

    public void markDelivered() {
        if (status != Status.OUT_FOR_DELIVERY) throw new IllegalStateException("Order not out for delivery");
        this.status = Status.DELIVERED;
        customer.addOrderToHistory(this);
    }

    public void cancel() {
        if (status == Status.DELIVERED) throw new IllegalStateException("Delivered order cannot be cancelled");
        this.status = Status.CANCELLED;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer.getName() +
                ", total=" + getTotalPrice() +
                ", status=" + status +
                '}';
    }
}

