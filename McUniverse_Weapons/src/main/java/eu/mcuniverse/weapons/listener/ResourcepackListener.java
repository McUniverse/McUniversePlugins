package eu.mcuniverse.weapons.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status;

import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.weapons.main.Core;
import net.md_5.bungee.api.ChatColor;

public class ResourcepackListener implements Listener {

	public ResourcepackListener(final Core plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onResourcePackJoin(PlayerResourcePackStatusEvent e) {
//		e.getPlayer().sendMessage(e.getStatus().toString());
		if (e.getStatus() == Status.DECLINED) {
//			e.getPlayer().sendMessage(Messages.WARNING + "For the best experience download our custom resource pack!");
			e.getPlayer().sendMessage(UniverseAPI.getWarning() + "You have to download the resoucepack to play on this server!");
		}
		if (e.getStatus() == Status.SUCCESSFULLY_LOADED) {
			e.getPlayer().sendMessage(UniverseAPI.getPrefix() + ChatColor.GREEN + "Successfully downloaded resourcepack");
		}
		if (e.getStatus() == Status.FAILED_DOWNLOAD) {
			e.getPlayer().sendMessage(UniverseAPI.getWarning() + "Download failed!");
		}
	}
	
//	@EventHandler
//	public void onJoin(PlayerJoinEvent e) {
//		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
//		BookMeta meta = (BookMeta) book.getItemMeta();
//		meta.setAuthor(ChatColor.LIGHT_PURPLE + "McUniverse.eu");
//		meta.setTitle(ChatColor.DARK_RED + "Resourcepack");
//
//		BaseComponent[] text = new ComponentBuilder("   ")
//				.append("Resource Pack").color(ChatColor.BLACK).bold(true).underlined(true)
//				.append("\n")
//				.append("This server uses a resource pack for the cool textures and models. Download it for the bset experience.\n\n\n").bold(false).underlined(false)
//				.append("    ")
//				.append("Click to load!").color(ChatColor.DARK_GREEN).underlined(true).bold(true)
//				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Load the resourcepack", ChatColor.GREEN)))
//				.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/resource"))
//				.create();
//		
//		meta.spigot().setPages(text);
//
//		book.setItemMeta(meta);
//
//		e.getPlayer().openBook(book);
//	}
	
}
