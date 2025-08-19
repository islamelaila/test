package services.interfaces;

import models.Chef;
import models.Order;
import models.Dish;
import java.util.List;

/**
 * Service interface for chef-related operations
 */
public interface ChefService {
    
    // Chef management
    Chef createChef(String name, String email, String phone, String specialization, int experienceYears);
    Chef getChefById(String chefId);
    List<Chef> getAllChefs();
    Chef updateChef(Chef chef);
    boolean deleteChef(String chefId);
    
    // Chef availability
    void setChefAvailability(String chefId, boolean isAvailable);
    List<Chef> getAvailableChefs();
    Chef assignChefToOrder(String orderId);
    
    // Skill management
    void addSkillToChef(String chefId, String skill);
    void removeSkillFromChef(String chefId, String skill);
    List<String> getChefSkills(String chefId);
    List<Chef> getChefsWithSkill(String skill);
    
    // Order management
    List<Order> getChefOrders(String chefId);
    List<Order> getPendingOrders();
    Order startPreparingOrder(String orderId, String chefId);
    Order markOrderReady(String orderId);
    Order updateOrderStatus(String orderId, Order.OrderStatus status);
    
    // Menu and dish management
    Dish createDish(String name, String description, double price, String category, int preparationTime);
    Dish updateDish(Dish dish);
    boolean deleteDish(String dishId);
    List<Dish> getDishesByCategory(String category);
    List<Dish> getAvailableDishes();
    void setDishAvailability(String dishId, boolean isAvailable);
    
    // Performance tracking
    int getOrdersCompletedToday(String chefId);
    double getAveragePreparationTime(String chefId);
    List<Order> getChefOrderHistory(String chefId);
}