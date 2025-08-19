package restaurant.services.impl;

import restaurant.models.DeliveryGuy;
import restaurant.models.Order;
import restaurant.services.interfaces.DeliveryService;

public class DeliveryServiceImpl implements DeliveryService {
	@Override
	public void assignDeliveryGuy(Order order, DeliveryGuy deliveryGuy) {
		order.setAssignedDeliveryGuy(deliveryGuy);
		order.setStatus(Order.Status.OUT_FOR_DELIVERY);
	}

	@Override
	public void markOutForDelivery(Order order) {
		order.setStatus(Order.Status.OUT_FOR_DELIVERY);
	}

	@Override
	public void markDelivered(Order order) {
		order.setStatus(Order.Status.DELIVERED);
	}
}

