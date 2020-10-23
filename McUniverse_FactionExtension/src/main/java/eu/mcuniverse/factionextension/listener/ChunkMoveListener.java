package eu.mcuniverse.factionextension.listener;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class ChunkMoveListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Location from = e.getFrom();
		Location to = e.getTo();
		Chunk fromChunk = from.getChunk();
		Chunk toChunk = to.getChunk();
		if (fromChunk == toChunk) {
			return;
		}

		Player p = e.getPlayer();
		FPlayer fp = FPlayers.getInstance().getByPlayer(p);
		Faction enter = Board.getInstance().getFactionAt(new FLocation(to));
		Faction last = Board.getInstance().getFactionAt(new FLocation(from));

		if (enter == last) {
			return;
		}

		ChatColor color;

		if (enter.isWilderness()) {
			color = ChatColor.DARK_GREEN;
		} else if (enter.isSafeZone()) {
			color = ChatColor.GOLD;
		} else if (enter.isWarZone()) {
			color = ChatColor.DARK_RED;
		} else if (enter.getTag().equalsIgnoreCase(fp.getFaction().getTag())) {
			color = ChatColor.GREEN;
		} else {
			color = ChatColor.getByChar(fp.getFaction().getRelationWish(enter).getColor().getChar());
		}

		String tag = color + enter.getTag();

		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(tag));

	}

}
