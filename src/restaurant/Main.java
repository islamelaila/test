package restaurant;

import restaurant.models.*;
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

        // Register users
        Customer alice = customerService.registerCustomer(1, "Alice");
        Chef chefBob = chefService.registerChef(1, "Bob");
        DeliveryGuy dave = deliveryService.registerDeliveryGuy(1, "Dave");

        // Menu
        Dish pizza = new Dish(1, "Pizza Margherita", 10.5);
        Dish pasta = new Dish(2, "Pasta Carbonara", 12.25);
        List<Dish> dishes = Arrays.asList(pizza, pasta);

        // Place order
        Order order = customerService.placeOrder(alice, dishes);
        System.out.println("Created: " + order);

        // Prepare
        chefService.startPreparingOrder(chefBob, order);
        System.out.println("Preparing: " + order);
        chefService.markOrderReady(order);
        System.out.println("Ready: " + order);

        // Deliver
        deliveryService.pickUpOrder(dave, order);
        System.out.println("Out for delivery: " + order);
        deliveryService.markDelivered(order);
        System.out.println("Delivered: " + order);

        // Show customer history
        System.out.println("Order history for " + alice.getName() + ": " + alice.getOrderHistory());
    }
}

