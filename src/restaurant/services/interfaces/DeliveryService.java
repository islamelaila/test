package restaurant.services.interfaces;

import restaurant.models.DeliveryGuy;
import restaurant.models.Order;

public interface DeliveryService {
	void assignDeliveryGuy(Order order, DeliveryGuy deliveryGuy);
	void markOutForDelivery(Order order);
	void markDelivered(Order order);
}

