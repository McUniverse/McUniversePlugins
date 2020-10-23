package eu.mcuniverse.supplyrockets.chances.random;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class RandomCollection<T> {

	public final NavigableMap<Double, T> map = new TreeMap<Double, T>();
	private final Random random;
	private double total = 0;

	public RandomCollection() {
		this(new Random());
	}
	
	public RandomCollection(Random random) {
		this.random = random;
	}
	
	public RandomCollection<T> add(double weight, T result) {
		if (weight <= 0) return this;
		total += weight;
		map.put(total, result);
		return this;
	}
	
	public T next() {
		double value = random.nextDouble() * total;
		return map.higherEntry(value).getValue();
	}

}
