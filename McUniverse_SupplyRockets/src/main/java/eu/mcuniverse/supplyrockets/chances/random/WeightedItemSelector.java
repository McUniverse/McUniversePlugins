package eu.mcuniverse.supplyrockets.chances.random;

import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class WeightedItemSelector<T> {

	private final Random rnd = new Random();
	private final TreeMap<Object, Range<T>> ranges = new TreeMap<Object, Range<T>>();
	private int rangeSize;// Lowest integer higher than the top of the highest range.

	public WeightedItemSelector(List<WeightedItem<T>> weightedItems) {
//		int bottom = 0; // Increment by size of non zero range added as ranges grows.
		AtomicInteger bottom = new AtomicInteger(0); // Increment by size of non zero range added as ranges grows.
		
		weightedItems.forEach(wi -> {
			int weight = wi.getWeight();
			if (weight > 0) {
				int top = bottom.get() + weight - 1;
				Range<T> r = new Range<T>(bottom.get(), top, wi);
				if (ranges.containsKey(r)) {
					Range<T> other = ranges.get(r);
					throw new IllegalArgumentException(String.format("Range %s conflicts with range %s", r, other));
				}
				ranges.put(r, r);
//				bottom = top + 1;
				bottom.set(top + 1);
			}
		});
		rangeSize = bottom.get();
	}
	
	public WeightedItem<T> select() {
		Integer key = rnd.nextInt(rangeSize);
		Range<T> r = ranges.get(key);
		if (r == null) 
			return null;
		return r.getWeightedItem();
	}
	
}
