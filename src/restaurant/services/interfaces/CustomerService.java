package restaurant.services.interfaces;

import restaurant.models.Customer;
import restaurant.models.Dish;
import restaurant.models.Order;

import java.util.List;

public interface CustomerService {
	Order placeOrder(Customer customer, List<Dish> dishes);
}

