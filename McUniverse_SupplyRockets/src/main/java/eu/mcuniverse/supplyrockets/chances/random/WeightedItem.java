package eu.mcuniverse.supplyrockets.chances.random;

import lombok.Data;

@Data
public class WeightedItem<T> {

	private final int weight;
	private final T item;

//	/**
//	 * {non-javadoc}
//	 * @see java.lang.Object#toString()
//	 */
//	@Override
//	public String toString() {
//		StringBuilder builder = new StringBuilder();
//		builder.append("{\"_class\": WeightedItem {\"weight\":\"").append(weight).append("\", \"item\":\"").append(item)
//				.append("}");
//		return builder.toString();
//	}

}
