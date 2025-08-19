package restaurant.models;

import java.util.concurrent.atomic.AtomicInteger;

public class Customer extends User {
	private static final AtomicInteger sequence = new AtomicInteger(0);

	public Customer(int id, String name) {
		super(id, name);
	}

	public static int nextId() {
		return sequence.incrementAndGet();
	}
}

