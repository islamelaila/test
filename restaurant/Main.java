import models.*;
import services.interfaces.*;
import services.impl.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main class demonstrating the Restaurant Management System
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Restaurant Management System Demo ===\n");

        // Initialize services
        CustomerServiceImpl customerService = new CustomerServiceImpl();
        ChefServiceImpl chefService = new ChefServiceImpl();
        DeliveryServiceImpl deliveryService = new DeliveryServiceImpl();

        // Share orders between services (in a real application, this would be handled by dependency injection)
        Map<String, Order> sharedOrders = new HashMap<>();
        chefService.setOrders(sharedOrders);
        deliveryService.setOrders(sharedOrders);

        try {
            // Demo 1: Create Users
            System.out.println("1. Creating Users:");
            System.out.println("==================");
            
            Customer customer1 = customerService.createCustomer(
                "John Doe", "john.doe@email.com", "123-456-7890", "123 Main St, City"
            );
            Customer customer2 = customerService.createCustomer(
                "Jane Smith", "jane.smith@email.com", "098-765-4321", "456 Oak Ave, Town"
            );
            
            Chef chef1 = chefService.createChef(
                "Gordon Ramsay", "gordon@restaurant.com", "555-0101", "International Cuisine", 20
            );
            Chef chef2 = chefService.createChef(
                "Julia Child", "julia@restaurant.com", "555-0102", "French Cuisine", 15
            );
            
            DeliveryGuy deliveryGuy1 = deliveryService.createDeliveryGuy(
                "Mike Wilson", "mike@restaurant.com", "555-0201", "Motorcycle", "DL123456"
            );
            DeliveryGuy deliveryGuy2 = deliveryService.createDeliveryGuy(
                "Sarah Johnson", "sarah@restaurant.com", "555-0202", "Car", "DL789012"
            );

            // Add skills to chefs
            chefService.addSkillToChef(chef1.getId(), "Grilling");
            chefService.addSkillToChef(chef1.getId(), "Pasta Making");
            chefService.addSkillToChef(chef2.getId(), "French Cooking");
            chefService.addSkillToChef(chef2.getId(), "Baking");

            // Add delivery zones
            deliveryService.addDeliveryZone(deliveryGuy1.getId(), "Downtown");
            deliveryService.addDeliveryZone(deliveryGuy1.getId(), "City Center");
            deliveryService.addDeliveryZone(deliveryGuy2.getId(), "Suburbs");
            deliveryService.addDeliveryZone(deliveryGuy2.getId(), "Town");

            System.out.println("Created Customer: " + customer1.getName());
            System.out.println("Created Customer: " + customer2.getName());
            System.out.println("Created Chef: " + chef1.getName() + " (Specialization: " + chef1.getSpecialization() + ")");
            System.out.println("Created Chef: " + chef2.getName() + " (Specialization: " + chef2.getSpecialization() + ")");
            System.out.println("Created Delivery Guy: " + deliveryGuy1.getName() + " (Vehicle: " + deliveryGuy1.getVehicleType() + ")");
            System.out.println("Created Delivery Guy: " + deliveryGuy2.getName() + " (Vehicle: " + deliveryGuy2.getVehicleType() + ")");
            System.out.println();

            // Demo 2: Create Menu Items
            System.out.println("2. Creating Menu Items:");
            System.out.println("=======================");
            
            Dish pizza = chefService.createDish("Margherita Pizza", "Classic pizza with tomato, mozzarella, and basil", 15.99, "Main Course", 20);
            Dish pasta = chefService.createDish("Spaghetti Carbonara", "Creamy pasta with bacon and parmesan", 13.99, "Main Course", 15);
            Dish salad = chefService.createDish("Caesar Salad", "Fresh romaine lettuce with caesar dressing", 8.99, "Appetizer", 10);
            Dish dessert = chefService.createDish("Tiramisu", "Italian coffee-flavored dessert", 6.99, "Dessert", 5);

            // Add ingredients
            pizza.addIngredient("Tomato Sauce");
            pizza.addIngredient("Mozzarella Cheese");
            pizza.addIngredient("Fresh Basil");
            pizza.setVegetarian(true);

            pasta.addIngredient("Spaghetti");
            pasta.addIngredient("Bacon");
            pasta.addIngredient("Parmesan Cheese");
            pasta.addIngredient("Eggs");

            salad.addIngredient("Romaine Lettuce");
            salad.addIngredient("Caesar Dressing");
            salad.addIngredient("Croutons");
            salad.setVegetarian(true);

            System.out.println("Created Dish: " + pizza.getName() + " - $" + pizza.getPrice());
            System.out.println("Created Dish: " + pasta.getName() + " - $" + pasta.getPrice());
            System.out.println("Created Dish: " + salad.getName() + " - $" + salad.getPrice());
            System.out.println("Created Dish: " + dessert.getName() + " - $" + dessert.getPrice());
            System.out.println();

            // Demo 3: Create and Process Orders
            System.out.println("3. Creating and Processing Orders:");
            System.out.println("==================================");
            
            // Customer 1 places an order
            Order order1 = customerService.createOrder(customer1.getId(), customer1.getAddress());
            customerService.addItemToOrder(order1.getId(), pizza, 2);
            customerService.addItemToOrder(order1.getId(), salad, 1);
            customerService.addItemToOrder(order1.getId(), dessert, 2);
            
            // Customer 2 places an order
            Order order2 = customerService.createOrder(customer2.getId(), customer2.getAddress());
            customerService.addItemToOrder(order2.getId(), pasta, 1);
            customerService.addItemToOrder(order2.getId(), salad, 1);

            System.out.println("Order 1 created for " + customer1.getName() + ":");
            System.out.println("  Total Items: " + order1.getTotalItems());
            System.out.println("  Total Amount: $" + String.format("%.2f", order1.getTotalAmount()));
            
            System.out.println("Order 2 created for " + customer2.getName() + ":");
            System.out.println("  Total Items: " + order2.getTotalItems());
            System.out.println("  Total Amount: $" + String.format("%.2f", order2.getTotalAmount()));
            System.out.println();

            // Place orders
            customerService.placeOrder(order1.getId());
            customerService.placeOrder(order2.getId());
            
            // Add orders to shared map for other services
            sharedOrders.put(order1.getId(), order1);
            sharedOrders.put(order2.getId(), order2);

            System.out.println("Orders placed successfully!");
            System.out.println("Order 1 Status: " + order1.getStatus());
            System.out.println("Order 2 Status: " + order2.getStatus());
            System.out.println();

            // Demo 4: Chef Operations
            System.out.println("4. Chef Operations:");
            System.out.println("==================");
            
            List<Order> pendingOrders = chefService.getPendingOrders();
            System.out.println("Pending Orders: " + pendingOrders.size());

            // Assign chefs to orders
            Chef assignedChef1 = chefService.assignChefToOrder(order1.getId());
            Chef assignedChef2 = chefService.assignChefToOrder(order2.getId());

            System.out.println("Order 1 assigned to Chef: " + assignedChef1.getName());
            System.out.println("Order 2 assigned to Chef: " + assignedChef2.getName());

            // Start preparing orders
            chefService.startPreparingOrder(order1.getId(), assignedChef1.getId());
            chefService.startPreparingOrder(order2.getId(), assignedChef2.getId());

            System.out.println("Orders are now being prepared...");
            System.out.println("Order 1 Status: " + order1.getStatus());
            System.out.println("Order 2 Status: " + order2.getStatus());

            // Mark orders as ready
            chefService.markOrderReady(order1.getId());
            chefService.markOrderReady(order2.getId());

            System.out.println("Orders are ready for delivery!");
            System.out.println("Order 1 Status: " + order1.getStatus());
            System.out.println("Order 2 Status: " + order2.getStatus());
            System.out.println();

            // Demo 5: Delivery Operations
            System.out.println("5. Delivery Operations:");
            System.out.println("======================");
            
            List<Order> readyOrders = deliveryService.getReadyForDeliveryOrders();
            System.out.println("Orders ready for delivery: " + readyOrders.size());

            // Assign delivery guys
            DeliveryGuy assignedDelivery1 = deliveryService.assignDeliveryGuyToOrder(order1.getId(), "City Center");
            DeliveryGuy assignedDelivery2 = deliveryService.assignDeliveryGuyToOrder(order2.getId(), "Town");

            System.out.println("Order 1 assigned to Delivery Guy: " + assignedDelivery1.getName());
            System.out.println("Order 2 assigned to Delivery Guy: " + assignedDelivery2.getName());

            // Start deliveries
            deliveryService.startDelivery(order1.getId(), assignedDelivery1.getId());
            deliveryService.startDelivery(order2.getId(), assignedDelivery2.getId());

            System.out.println("Orders are out for delivery...");
            System.out.println("Order 1 Status: " + order1.getStatus());
            System.out.println("Order 2 Status: " + order2.getStatus());

            // Complete deliveries
            deliveryService.markOrderDelivered(order1.getId());
            deliveryService.markOrderDelivered(order2.getId());

            System.out.println("Orders delivered successfully!");
            System.out.println("Order 1 Status: " + order1.getStatus());
            System.out.println("Order 2 Status: " + order2.getStatus());
            System.out.println();

            // Demo 6: Customer Loyalty and Statistics
            System.out.println("6. Customer Loyalty and Statistics:");
            System.out.println("===================================");
            
            System.out.println("Customer 1 Loyalty Points: " + customer1.getLoyaltyPoints());
            System.out.println("Customer 2 Loyalty Points: " + customer2.getLoyaltyPoints());
            
            List<Order> customer1History = customerService.getCustomerOrderHistory(customer1.getId());
            System.out.println("Customer 1 Order History: " + customer1History.size() + " orders");
            
            System.out.println("Chef 1 Orders Completed Today: " + chefService.getOrdersCompletedToday(chef1.getId()));
            System.out.println("Chef 2 Orders Completed Today: " + chefService.getOrdersCompletedToday(chef2.getId()));
            
            System.out.println("Delivery Guy 1 Deliveries Today: " + deliveryService.getDeliveriesCompletedToday(deliveryGuy1.getId()));
            System.out.println("Delivery Guy 2 Deliveries Today: " + deliveryService.getDeliveriesCompletedToday(deliveryGuy2.getId()));
            
            System.out.println("Total Deliveries Today: " + deliveryService.getTotalDeliveriesToday());
            System.out.println();

            // Demo 7: System Status
            System.out.println("7. System Status:");
            System.out.println("================");
            
            System.out.println("Total Customers: " + customerService.getAllCustomers().size());
            System.out.println("Total Chefs: " + chefService.getAllChefs().size());
            System.out.println("Available Chefs: " + chefService.getAvailableChefs().size());
            System.out.println("Total Delivery Guys: " + deliveryService.getAllDeliveryGuys().size());
            System.out.println("Available Delivery Guys: " + deliveryService.getAvailableDeliveryGuys().size());
            System.out.println("Available Dishes: " + chefService.getAvailableDishes().size());

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n=== Demo Completed Successfully! ===");
    }
}