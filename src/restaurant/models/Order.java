package restaurant.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
	public enum Status {
		CREATED,
		PREPARING,
		PREPARED,
		OUT_FOR_DELIVERY,
		DELIVERED
	}

	private final int orderId;
	private final Customer customer;
	private final List<Dish> dishes;
	private final Instant createdAt;
	private Status status;
	private Chef assignedChef;
	private DeliveryGuy assignedDeliveryGuy;

	public Order(int orderId, Customer customer, List<Dish> dishes) {
		this.orderId = orderId;
		this.customer = customer;
		this.dishes = new ArrayList<>(dishes);
		this.createdAt = Instant.now();
		this.status = Status.CREATED;
	}

	public int getOrderId() {
		return orderId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public List<Dish> getDishes() {
		return Collections.unmodifiableList(dishes);
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

	@Override
	public String toString() {
		return "Order{" +
				"orderId=" + orderId +
				", customer=" + customer +
				", dishes=" + dishes +
				", status=" + status +
				", chef=" + assignedChef +
				", delivery=" + assignedDeliveryGuy +
				'}';
	}
}

