package restaurant.services.impl;

import restaurant.models.Chef;
import restaurant.models.Order;
import restaurant.services.interfaces.ChefService;

public class ChefServiceImpl implements ChefService {
	@Override
	public void assignChef(Order order, Chef chef) {
		order.setAssignedChef(chef);
		order.setStatus(Order.Status.PREPARING);
	}

	@Override
	public void markPreparing(Order order) {
		order.setStatus(Order.Status.PREPARING);
	}

	@Override
	public void markPrepared(Order order) {
		order.setStatus(Order.Status.PREPARED);
	}
}

