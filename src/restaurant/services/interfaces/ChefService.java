package restaurant.services.interfaces;

import restaurant.models.Chef;
import restaurant.models.Order;

public interface ChefService {
	void assignChef(Order order, Chef chef);
	void markPreparing(Order order);
	void markPrepared(Order order);
}

