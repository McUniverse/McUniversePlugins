package eu.mcuniverse.chat.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import eu.mcuniverse.chat.main.main;


public class JoinListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.sendMessage("");
		p.sendMessage("§8§m------------------------------------------§8]");
		p.sendMessage("         §7Welcome to §6Minecraft Universe");
		p.sendMessage("         §7Teamspeak §8§ §6134.255.219.94");
		p.sendMessage("      §7Discord §8§ §6https://discord.gg/tAumCdV");
		p.sendMessage("         §7Website §8§ §6McUniverse.eu");
		p.sendMessage("§8§m------------------------------------------§8]");
		p.sendMessage("");
		p.setGameMode(GameMode.ADVENTURE);
		if (p.hasPermission("mcuniverse.team")) {
			e.setJoinMessage("§cStaff §7| §c" + p.getName() + "§7 is now §aOnline");
			p.setGameMode(GameMode.CREATIVE);
			p.sendMessage(main.prefix + "§cYou were switched to Creative mode!");
		} else {
			e.setJoinMessage(null);
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("mcuniverse.team")) {
			e.setQuitMessage("§cStaff §7| §c" + p.getName() + "§7 is now §cOffline");
		}
	}

//	@EventHandler
//	public void onlanguage(PlayerJoinEvent e) {
//		Player p = e.getPlayer();
//		Inventory inv = Bukkit.createInventory(null, 9, "     §8-» §6Server Language §8«-");
//		ItemStack GRAY = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
//		ItemMeta GRAYmeta = GRAY.getItemMeta();
//		GRAYmeta.setDisplayName("§0");
//		GRAY.setItemMeta(GRAYmeta);
//		
//		ItemStack German = new ItemStack(Material.RED_WOOL);
//		ItemMeta Germanmeta = German.getItemMeta();
//		Germanmeta.setDisplayName("§8-» §7Server Sprache §cDeutsch §8«-");
//		German.setItemMeta(Germanmeta);
//		
//		ItemStack English = new ItemStack(Material.YELLOW_WOOL);
//		ItemMeta Englishmeta = English.getItemMeta();
//		Englishmeta.setDisplayName("§8-» §7Server Language §eEnglish §8«-");
//		English.setItemMeta(Englishmeta);
////		
////		String ger = "http://textures.minecraft.net/texture/5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f==";
////		ItemStack german = SkullCreator.itemFromUrl(ger);
////		
//		inv.setItem(0, GRAY);
//		inv.setItem(1, GRAY);
//		inv.setItem(2, GRAY);
//		inv.setItem(3, German);
//		inv.setItem(4, GRAY);
//		inv.setItem(5, English);
//		inv.setItem(6, GRAY);
//		inv.setItem(7, GRAY);
//		inv.setItem(8, GRAY);
//		p.openInventory(inv);
//	}
//	
////	public static ItemStack getStormtrooper() {
////	    // Got this base64 string from minecraft-heads.com
////	    String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L" +
////	     "3RleHR1cmUvNTIyODRlMTMyYmZkNjU5YmM2YWRhNDk3YzRmYTMwOTRjZDkzMjMxYTZiNTA1YTEyY2U3Y2Q1MTM1YmE4ZmY5MyJ9fX0=";
////	    
////	    return SkullCreator.itemFromBase64(base64);
////	}
////	
////	public static ItemStack getCheeseSkull() {
////	    String s = "http://textures.minecraft.net/texture/955d611a878e821231749b2965708cad942650672db09e26847a88e2fac2946";
////	    
////	    return SkullCreator.itemFromUrl(s);
////	}
//
//	@EventHandler
//	public void on(InventoryClickEvent e) {
//		Player p = (Player) e.getWhoClicked();
//		if (e.getView().getTitle().contains("§6Server Language")) {
//			e.setCancelled(true);
//		}
//		if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8-» §7Server Sprache §cDeutsch §8«-")) {
//		 	p.sendMessage(main.prefix + "Die Einstellungen wurden auf §cDeutsch §7gestellt.");
//		 	p.closeInventory();
//		}
//		if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8-» §7Server Language §eEnglish §8«-")) {
//		 	p.sendMessage(main.prefix + "The settings were changed to §eEnglish.");
//		 	p.closeInventory();
//		}
//	}
	
}
