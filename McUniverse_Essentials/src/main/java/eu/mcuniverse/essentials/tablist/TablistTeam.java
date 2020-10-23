package eu.mcuniverse.essentials.tablist;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import eu.mcuniverse.universeapi.api.UniverseAPI;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class TablistTeam {

	public static Object2ObjectOpenHashMap<String, String> teams;

	static {
		teams = new Object2ObjectOpenHashMap<String, String>();
	}

	public void create(String name, int rank, String prefix, String suffix, String permission) {
		String fullname = "000" + rank + "_" + name;
		Scoreboard board = RankManager.getInstance().getScoreboard();
		Team team = board.getTeam(fullname);

		if (team != null) {
			team.unregister();
		}

		team = board.registerNewTeam(fullname);

		if (prefix != null) {
			team.setPrefix(prefix);
		}

		if (suffix != null) {
			team.setSuffix(suffix);
		}

//		teams.put(permission, fullname);
		teams.put(name, fullname);
	}

	public void addPlayer(Player p) {
		Team team = null;

//		Bukkit.getPlayer("JayReturns").sendMessage(p.getName());
		for (String group : teams.keySet()) {
//			if (perm == null || p.hasPermission(perm)) {
//			Bukkit.getPlayer("JayReturns").sendMessage(UniverseAPI.getLuckPermsUtil().getGroup(p.getUniqueId()) + " --- " + group + " --- " + UniverseAPI.getLuckPermsUtil().getGroup(p.getUniqueId()).toLowerCase().equalsIgnoreCase(group.toLowerCase()));
			if (group == null || UniverseAPI.getLuckPermsUtil().getGroup(p.getUniqueId()).toLowerCase().equalsIgnoreCase(group.toLowerCase())) {
			String currentTeamName = teams.get(group);

//			Bukkit.getPlayer("JayReturns").sendMessage((team == null) + " <- Null?");
//			if (team != null) {
//				Bukkit.getPlayer("JayReturns").sendMessage(this.getRank(team.getName()) + " <- Team?");
//				Bukkit.getPlayer("JayReturns").sendMessage(this.getRank(currentTeamName) + " <- CurrentTeam?");
//				Bukkit.getPlayer("JayReturns").sendMessage((this.getRank(currentTeamName) < this.getRank(team.getName())) + " <- Rank?");
//			}
				if (team == null || this.getRank(currentTeamName) < this.getRank(team.getName())) {
					team = RankManager.getInstance().getScoreboard().getTeam(currentTeamName);
				}
			}
		}

		if (team != null) {
			team.addEntry(p.getName());
		}
	}

	public void update() {
		Bukkit.getOnlinePlayers().forEach(online -> {
			this.removePlayer(online);
			this.addPlayer(online);
		});
	}

	public void removePlayer(Player p) {
		for (String teamName : teams.values()) {
			Team team = RankManager.getInstance().getScoreboard().getTeam(teamName);
			if (team != null && team.hasEntry(p.getName())) {
				team.removeEntry(p.getName());
			}
		}
	}
	
	private int getRank(String teamName) {
		if (!teamName.contains("_")) {
			return -1;
		}
		
		String[] array = teamName.split("_");
		try {
			int i = Integer.parseInt(array[0]);
			return i;
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
}
