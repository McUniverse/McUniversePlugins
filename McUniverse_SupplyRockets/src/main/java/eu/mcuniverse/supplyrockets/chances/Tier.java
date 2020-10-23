package eu.mcuniverse.supplyrockets.chances;

import eu.mcuniverse.supplyrockets.chances.random.RandomCollection;

public enum Tier {

	TIER_ONE {
		@Override
		public RandomCollection<Rarity> getChances() {
			return chances(60, 30, 10);
//			return new RandomCollection<Rarity>().add(60, Rarity.COMMON).add(30, Rarity.UNCOMMON).add(10, Rarity.RARE);
		}
	},
	TIER_TWO {
		@Override
		public RandomCollection<Rarity> getChances() {
			return chances(40, 40, 20);
		}
	},
	METEORITE_SMALL {
		@Override
		public RandomCollection<Rarity> getChances() {
			return chances(30, 45, 25);
		}
	},
	MATEORITE_MEDIUM {
		@Override
		public RandomCollection<Rarity> getChances() {
			return chances(30, 45, 25);
		}
	},
	METEORITE_LARGE {
		@Override
		public RandomCollection<Rarity> getChances() {
			return chances(30, 45, 25);
		}
	},
	METEORITE_EXTRA_LARGE {
		@Override
		public RandomCollection<Rarity> getChances() {
			return chances(30, 45, 25);
		}
	};

	public abstract RandomCollection<Rarity> getChances();

	private static RandomCollection<Rarity> chances(double common, double uncommon, double rare) {
		return new RandomCollection<Rarity>().add(common, Rarity.COMMON).add(uncommon, Rarity.UNCOMMON).add(rare,
				Rarity.RARE);
	}

}
