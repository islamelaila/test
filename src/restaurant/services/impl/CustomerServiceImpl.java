package restaurant.services.impl;

import restaurant.models.Customer;
import restaurant.models.Dish;
import restaurant.models.Order;
import restaurant.services.interfaces.CustomerService;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerServiceImpl implements CustomerService {
	private static final AtomicInteger orderSequence = new AtomicInteger(0);

	@Override
	public Order placeOrder(Customer customer, List<Dish> dishes) {
		int orderId = orderSequence.incrementAndGet();
		return new Order(orderId, customer, dishes);
	}
}

