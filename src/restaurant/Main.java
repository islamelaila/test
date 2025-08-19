package restaurant;

import restaurant.models.*;
import restaurant.services.impl.ChefServiceImpl;
import restaurant.services.impl.CustomerServiceImpl;
import restaurant.services.impl.DeliveryServiceImpl;
import restaurant.services.interfaces.ChefService;
import restaurant.services.interfaces.CustomerService;
import restaurant.services.interfaces.DeliveryService;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CustomerService customerService = new CustomerServiceImpl();
        ChefService chefService = new ChefServiceImpl();
        DeliveryService deliveryService = new DeliveryServiceImpl();

        Customer alice = customerService.registerCustomer("Alice Doe", "+1234567890", "123 Main St");
        Chef chefBob = chefService.registerChef("Bob Chef", "+1111111", "Italian");
        DeliveryGuy dan = deliveryService.registerDeliveryGuy("Dan Rider", "+2222222", "Bike");

        Dish pizza = new Dish("Margherita Pizza", new BigDecimal("12.50"));
        Dish salad = new Dish("Caesar Salad", new BigDecimal("7.25"));

        Order order = customerService.placeOrder(alice, List.of(pizza, salad));
        chefService.assignChefToOrder(chefBob, order);
        chefService.markOrderPreparing(order);
        chefService.markOrderReadyForDelivery(order);
        deliveryService.assignDeliveryGuyToOrder(dan, order);
        deliveryService.markOrderOutForDelivery(order);
        deliveryService.markOrderDelivered(order);

        System.out.println("Order id: " + order.getId());
        System.out.println("Customer: " + order.getCustomer().getFullName());
        System.out.println("Total: " + order.getTotal());
        System.out.println("Status: " + order.getStatus());
    }
}

