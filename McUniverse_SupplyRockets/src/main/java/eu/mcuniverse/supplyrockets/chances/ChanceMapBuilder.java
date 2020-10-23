package eu.mcuniverse.supplyrockets.chances;

import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;

class ChanceMapBuilder {

	private Object2DoubleOpenHashMap<Rarity> map;

	public ChanceMapBuilder() {
		map = new Object2DoubleOpenHashMap<Rarity>();
	}

	public ChanceMapBuilder putUncommon(double d) {
		map.put(Rarity.UNCOMMON, d);
		return this;
	}

	public ChanceMapBuilder putCommon(double d) {
		map.put(Rarity.COMMON, d);
		return this;
	}

	public ChanceMapBuilder putRare(double d) {
		map.put(Rarity.RARE, d);
		return this;
	}

	public Object2DoubleOpenHashMap<Rarity> build() {
		
		double sum = map.values().stream().reduce(0d, Double::sum);
		
		if (sum != 100) {
			throw new IllegalArgumentException(sum + " is not equal to 100%!");
		}
		
		return map;
	}

}
