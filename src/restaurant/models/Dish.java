package restaurant.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Dish {
    private final String name;
    private final BigDecimal price;

    public Dish(String name, BigDecimal price) {
        this.name = Objects.requireNonNull(name, "name");
        this.price = Objects.requireNonNull(price, "price");
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

