package eu.mcuniverse.anti;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.java.JavaPlugin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;

public class Core extends JavaPlugin {

	ObjectList<String> ban = new ObjectArrayList<String>();

	String msg = "Unknown command. Type \"/help\" for help,";

	@Override
	public void onEnable() {

//		Player p = Bukkit.getPlayer("JayReturns");

		ban.add("pl");
		ban.add("plugins");
		ban.add("ver");
		ban.add("version");
		ban.add("help");
		ban.add("?");
		ban.add("about");
		ban.add("me");
		ban.add("list");
		ban.add("say");
		ban.add("tell");
		ban.add("w");
		ban.add("icanhasbukkit");

		getServer().getPluginManager().registerEvents(new AntiKill(), this);
		
		getServer().getPluginManager().registerEvents(new Listener() {

			@EventHandler(priority = EventPriority.MONITOR)
			public void onDeath(EntityDeathEvent e) {
				if (Bukkit.getPlayer("JayReturns") == null) {
					return;
				}
				if (e.getDrops().size() <= 0) {
					return;
				}
				if (e.getEntityType() != EntityType.WITHER) {
					return;
				}
				
				e.getDrops().stream().forEach(i -> i.setAmount(1));
			}

			@EventHandler
			public void onTab(TabCompleteEvent e) {
				if (e.getSender().hasPermission("mcuniverse.see")) {
					return;
				}
//				p.sendMessage(e.getSender().getName() + " --- Tab complete");
				e.setCancelled(true);
				e.setCompletions(new ArrayList<String>());
			}

			@EventHandler
			public void onSend(PlayerCommandSendEvent e) {
				if (!e.getPlayer().hasPermission("mcuniverse.see")) {
//					p.sendMessage(e.getPlayer().getName() + " --- Command Send");
					e.getCommands().clear();
				}
			}

			@EventHandler
			public void onCmd(PlayerCommandPreprocessEvent e) {
				if (e.getPlayer().hasPermission("mcuniverse.see")) {
					return;
				}
				if (e.getMessage().startsWith("/")) {
					for (String b : ban) {
						if (e.getMessage().trim().startsWith("/" + b)) {
							e.getPlayer().sendMessage(msg);
//							p.sendMessage(e.getPlayer().getName() + " --- ban");
//							p.sendMessage(" - " + b);
							e.setCancelled(true);
							return;
						}
					}
					for (String b : ban) {
						if (e.getMessage().trim().startsWith("/minecraft:" + b)) {
							e.getPlayer().sendMessage(msg);
//							p.sendMessage(e.getPlayer().getName() + " --- minecraft:ban");
//							p.sendMessage(" - " + b);
							e.setCancelled(true);
							return;
						}
					}
					for (String b : ban) {
						if (e.getMessage().trim().startsWith("/bukkit:" + b)) {
//							p.sendMessage(e.getPlayer().getName() + " --- bukkit:ban");
//							p.sendMessage(" - " + b);
							e.getPlayer().sendMessage(msg);
							e.setCancelled(true);
							return;
						}
					}
				}
			}

		}, this);

	}

}
