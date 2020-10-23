package eu.mcuniverse.supplyrockets.chances.random;

import lombok.Data;

@Data
public class Range<T> implements Comparable<Object> {

	final int bottom;
	final int top;
	final WeightedItem<T> weightedItem;

	@Override
	public int compareTo(Object in) {
		if (in instanceof Range<?>) {
			Range<?> other = (Range<?>) in;
			if (this.bottom > other.top) {
				return 1;
			}
			if (this.top < other.bottom) {
				return -1;
			}
			return 0; // overlapping ranges are considered equal
		} else if (in instanceof Integer) {
			Integer other = (Integer) in;
			if (this.bottom > other.intValue()) {
				return 1;
			}
			if (this.top < other.intValue()) {
				return -1;
			}
			return 0;
		}
		throw new IllegalArgumentException(String.format("Cannot compare Range objects to %s objects", in.getClass().getName()));
	}

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.lang.Object#toString()
//	 */
//	@Override
//	public String toString() {
//		StringBuilder builder = new StringBuilder();
//		builder.append("{\"_class\": Range {\"bottom\":\"").append(bottom).append("\", \"top\":\"").append(top)
//				.append("\", \"weightedItem\":\"").append(weightedItem).append("}");
//		return builder.toString();
//	}

}
