package restaurant.models;

import java.util.Objects;

public class Dish {
    private final int id;
    private final String name;
    private final double price;

    public Dish(int id, String name, double price) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("dish name must not be blank");
        }
        if (price < 0) {
            throw new IllegalArgumentException("price must be non-negative");
        }
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;
        Dish dish = (Dish) o;
        return id == dish.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

