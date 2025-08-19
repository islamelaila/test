package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Dish class representing a menu item
 */
public class Dish {
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    private List<String> ingredients;
    private boolean isVegetarian;
    private boolean isVegan;
    private boolean isGlutenFree;
    private int preparationTimeMinutes;
    private boolean isAvailable;

    public Dish(String id, String name, String description, double price, String category, int preparationTimeMinutes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.preparationTimeMinutes = preparationTimeMinutes;
        this.ingredients = new ArrayList<>();
        this.isVegetarian = false;
        this.isVegan = false;
        this.isGlutenFree = false;
        this.isAvailable = true;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public boolean isGlutenFree() {
        return isGlutenFree;
    }

    public int getPreparationTimeMinutes() {
        return preparationTimeMinutes;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
        if (vegan) {
            isVegetarian = true; // Vegan implies vegetarian
        }
    }

    public void setGlutenFree(boolean glutenFree) {
        isGlutenFree = glutenFree;
    }

    public void setPreparationTimeMinutes(int preparationTimeMinutes) {
        if (preparationTimeMinutes >= 0) {
            this.preparationTimeMinutes = preparationTimeMinutes;
        } else {
            throw new IllegalArgumentException("Preparation time cannot be negative");
        }
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    // Ingredient management
    public void addIngredient(String ingredient) {
        if (!ingredients.contains(ingredient)) {
            ingredients.add(ingredient);
        }
    }

    public void removeIngredient(String ingredient) {
        ingredients.remove(ingredient);
    }

    public boolean hasIngredient(String ingredient) {
        return ingredients.contains(ingredient);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", ingredients=" + ingredients +
                ", isVegetarian=" + isVegetarian +
                ", isVegan=" + isVegan +
                ", isGlutenFree=" + isGlutenFree +
                ", preparationTimeMinutes=" + preparationTimeMinutes +
                ", isAvailable=" + isAvailable +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Dish dish = (Dish) obj;
        return id.equals(dish.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}