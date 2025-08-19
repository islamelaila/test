package restaurant;

import restaurant.models.Chef;
import restaurant.models.Customer;
import restaurant.models.DeliveryGuy;
import restaurant.models.Dish;
import restaurant.models.Order;
import restaurant.services.impl.ChefServiceImpl;
import restaurant.services.impl.CustomerServiceImpl;
import restaurant.services.impl.DeliveryServiceImpl;
import restaurant.services.interfaces.ChefService;
import restaurant.services.interfaces.CustomerService;
import restaurant.services.interfaces.DeliveryService;

import java.util.Arrays;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		CustomerService customerService = new CustomerServiceImpl();
		ChefService chefService = new ChefServiceImpl();
		DeliveryService deliveryService = new DeliveryServiceImpl();

		Customer customer = new Customer(1, "Alice");
		Chef chef = new Chef(100, "Chef Bob");
		DeliveryGuy deliveryGuy = new DeliveryGuy(200, "Dan the Driver");

		List<Dish> dishes = Arrays.asList(
			new Dish("Margherita Pizza", 10.99),
			new Dish("Caesar Salad", 6.50)
		);

		Order order = customerService.placeOrder(customer, dishes);
		System.out.println("Order created: " + order);

		chefService.assignChef(order, chef);
		System.out.println("Chef assigned: " + order);

		chefService.markPrepared(order);
		System.out.println("Order prepared: " + order);

		deliveryService.assignDeliveryGuy(order, deliveryGuy);
		System.out.println("Delivery assigned: " + order);

		deliveryService.markDelivered(order);
		System.out.println("Order delivered: " + order);
	}
}

