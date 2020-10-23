package eu.mcuniverse.supplyrockets.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RandomUtils {

	public <T> T getRandomElement(@NonNull Collection<T> collection) {
		if (collection.size() == 0) {
			return null;
		}
		Random random = ThreadLocalRandom.current();
		int index = random.nextInt(collection.size());
		if (collection instanceof List) {
			return ((List<? extends T>) collection).get(index);
		} else {
			Iterator<? extends T> iter = collection.iterator();
			for (int i = 0; i < index; i++) {
				iter.next();
			}
			return iter.next();
		}
	}

	public <T> T getRandomElementV2(@NonNull Collection<T> collection) {
		return collection.stream().
				skip(ThreadLocalRandom.current().nextInt(collection.size())).
				findFirst().
				orElse(null);
	}
	
	public <T> T getRrandomElementArray(@NonNull T[] array) {
		int index = ThreadLocalRandom.current().nextInt(array.length);
		return array[index];
	}
	
	public int[] getRandomSlot(int size) {
		IntArrayList count = new IntArrayList();
//		LinkedList<Integer> count = new LinkedList<Integer>();
		for (int i = 0; i < size; i++) {
			count.add(i);
		}
		int[] result = new int[size];
		for (int i = 0; i < result.length; i++) {
//			result[i] = count.
			Collections.shuffle(count);
			result[i] = count.popInt();
		}
		return result;
	}

}
