package services.impl;

import models.Chef;
import models.Order;
import models.Dish;
import services.interfaces.ChefService;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;

/**
 * Implementation of ChefService interface
 */
public class ChefServiceImpl implements ChefService {
    
    private Map<String, Chef> chefs;
    private Map<String, Dish> dishes;
    private Map<String, Order> orders;
    private int chefIdCounter;
    private int dishIdCounter;

    public ChefServiceImpl() {
        this.chefs = new HashMap<>();
        this.dishes = new HashMap<>();
        this.orders = new HashMap<>();
        this.chefIdCounter = 1;
        this.dishIdCounter = 1;
    }

    // Setter for orders (to be injected by a dependency injection framework or main class)
    public void setOrders(Map<String, Order> orders) {
        this.orders = orders;
    }

    @Override
    public Chef createChef(String name, String email, String phone, String specialization, int experienceYears) {
        String chefId = "CHEF" + String.format("%04d", chefIdCounter++);
        Chef chef = new Chef(chefId, name, email, phone, specialization, experienceYears);
        chefs.put(chefId, chef);
        return chef;
    }

    @Override
    public Chef getChefById(String chefId) {
        return chefs.get(chefId);
    }

    @Override
    public List<Chef> getAllChefs() {
        return new ArrayList<>(chefs.values());
    }

    @Override
    public Chef updateChef(Chef chef) {
        if (chefs.containsKey(chef.getId())) {
            chefs.put(chef.getId(), chef);
            return chef;
        }
        return null;
    }

    @Override
    public boolean deleteChef(String chefId) {
        return chefs.remove(chefId) != null;
    }

    @Override
    public void setChefAvailability(String chefId, boolean isAvailable) {
        Chef chef = chefs.get(chefId);
        if (chef != null) {
            chef.setAvailable(isAvailable);
        }
    }

    @Override
    public List<Chef> getAvailableChefs() {
        return chefs.values().stream()
                .filter(Chef::isAvailable)
                .collect(Collectors.toList());
    }

    @Override
    public Chef assignChefToOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }

        List<Chef> availableChefs = getAvailableChefs();
        if (availableChefs.isEmpty()) {
            throw new IllegalStateException("No available chefs");
        }

        // Simple assignment: pick the first available chef
        // In a real system, this could be more sophisticated (load balancing, specialization matching, etc.)
        Chef assignedChef = availableChefs.get(0);
        order.setAssignedChefId(assignedChef.getId());
        
        return assignedChef;
    }

    @Override
    public void addSkillToChef(String chefId, String skill) {
        Chef chef = chefs.get(chefId);
        if (chef != null) {
            chef.addSkill(skill);
        }
    }

    @Override
    public void removeSkillFromChef(String chefId, String skill) {
        Chef chef = chefs.get(chefId);
        if (chef != null) {
            chef.removeSkill(skill);
        }
    }

    @Override
    public List<String> getChefSkills(String chefId) {
        Chef chef = chefs.get(chefId);
        return chef != null ? chef.getSkills() : new ArrayList<>();
    }

    @Override
    public List<Chef> getChefsWithSkill(String skill) {
        return chefs.values().stream()
                .filter(chef -> chef.hasSkill(skill))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getChefOrders(String chefId) {
        return orders.values().stream()
                .filter(order -> chefId.equals(order.getAssignedChefId()))
                .filter(order -> order.getStatus() == Order.OrderStatus.PREPARING || 
                               order.getStatus() == Order.OrderStatus.CONFIRMED)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getPendingOrders() {
        return orders.values().stream()
                .filter(order -> order.getStatus() == Order.OrderStatus.CONFIRMED)
                .collect(Collectors.toList());
    }

    @Override
    public Order startPreparingOrder(String orderId, String chefId) {
        Order order = orders.get(orderId);
        Chef chef = chefs.get(chefId);
        
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        if (chef == null) {
            throw new IllegalArgumentException("Chef not found: " + chefId);
        }
        if (order.getStatus() != Order.OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Order must be confirmed to start preparation");
        }

        order.setStatus(Order.OrderStatus.PREPARING);
        order.setAssignedChefId(chefId);
        
        return order;
    }

    @Override
    public Order markOrderReady(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        if (order.getStatus() != Order.OrderStatus.PREPARING) {
            throw new IllegalStateException("Order must be in preparing status to mark as ready");
        }

        order.setStatus(Order.OrderStatus.READY);
        return order;
    }

    @Override
    public Order updateOrderStatus(String orderId, Order.OrderStatus status) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.setStatus(status);
        }
        return order;
    }

    @Override
    public Dish createDish(String name, String description, double price, String category, int preparationTime) {
        String dishId = "DISH" + String.format("%04d", dishIdCounter++);
        Dish dish = new Dish(dishId, name, description, price, category, preparationTime);
        dishes.put(dishId, dish);
        return dish;
    }

    @Override
    public Dish updateDish(Dish dish) {
        if (dishes.containsKey(dish.getId())) {
            dishes.put(dish.getId(), dish);
            return dish;
        }
        return null;
    }

    @Override
    public boolean deleteDish(String dishId) {
        return dishes.remove(dishId) != null;
    }

    @Override
    public List<Dish> getDishesByCategory(String category) {
        return dishes.values().stream()
                .filter(dish -> dish.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    @Override
    public List<Dish> getAvailableDishes() {
        return dishes.values().stream()
                .filter(Dish::isAvailable)
                .collect(Collectors.toList());
    }

    @Override
    public void setDishAvailability(String dishId, boolean isAvailable) {
        Dish dish = dishes.get(dishId);
        if (dish != null) {
            dish.setAvailable(isAvailable);
        }
    }

    @Override
    public int getOrdersCompletedToday(String chefId) {
        LocalDate today = LocalDate.now();
        return (int) orders.values().stream()
                .filter(order -> chefId.equals(order.getAssignedChefId()))
                .filter(order -> order.getStatus() == Order.OrderStatus.READY || 
                               order.getStatus() == Order.OrderStatus.DELIVERED)
                .filter(order -> order.getOrderTime().toLocalDate().equals(today))
                .count();
    }

    @Override
    public double getAveragePreparationTime(String chefId) {
        List<Order> completedOrders = orders.values().stream()
                .filter(order -> chefId.equals(order.getAssignedChefId()))
                .filter(order -> order.getStatus() == Order.OrderStatus.READY || 
                               order.getStatus() == Order.OrderStatus.DELIVERED)
                .collect(Collectors.toList());

        if (completedOrders.isEmpty()) {
            return 0.0;
        }

        return completedOrders.stream()
                .mapToInt(Order::getEstimatedPreparationTime)
                .average()
                .orElse(0.0);
    }

    @Override
    public List<Order> getChefOrderHistory(String chefId) {
        return orders.values().stream()
                .filter(order -> chefId.equals(order.getAssignedChefId()))
                .sorted((o1, o2) -> o2.getOrderTime().compareTo(o1.getOrderTime()))
                .collect(Collectors.toList());
    }

    // Getter for dishes (for other services to access)
    public Map<String, Dish> getDishes() {
        return new HashMap<>(dishes);
    }
}