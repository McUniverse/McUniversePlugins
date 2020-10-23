package eu.mcuniverse.essentials.tablist;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;

import lombok.Getter;

public class RankManager {

	private static RankManager instance;

	@Getter
	private Scoreboard scoreboard;
	
	private RankManager() {
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();				
	}

	public static RankManager getInstance() {
		if (instance == null) {
			instance = new RankManager();
		}
		return instance;
	}

}
