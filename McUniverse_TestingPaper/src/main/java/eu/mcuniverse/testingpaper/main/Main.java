package eu.mcuniverse.testingpaper.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {

		Bukkit.getPluginManager().registerEvents(this, this);
		
		getServer().getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void onCmd(PlayerCommandPreprocessEvent e) {
				if (e.getMessage().startsWith("/json")) {
					e.setCancelled(true);
					Player p = e.getPlayer();
					if (p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType() != Material.AIR) {
						p.sendMessage(JsonItemStack.toJson(p.getInventory().getItemInMainHand()));
					}
				}
			}
		}, this);
		
//		Player p = Bukkit.getPlayer("JayReturns");
//
//		ItemStack item = new ItemStack(Material.FIREWORK_STAR);
//		FireworkEffectMeta meta = (FireworkEffectMeta) item.getItemMeta();
//		meta.setEffect(FireworkEffect.builder().withColor(Color.LIME).trail(true).build());
//		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
//		meta.setDisplayName(ChatColor.BLUE + "MyStar");
//		meta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
//		meta.setUnbreakable(true);
//		item.setItemMeta(meta);
//		
//		p.getInventory().addItem(item);
//
////		String s = new SerializableItemStack(item).toYaml();
//		String s = JsonItemStack.toJson(item);
//		p.sendMessage(s);
//		
//		ItemStack stack = JsonItemStack.fromJson(s);
//		p.getInventory().addItem(stack);
		
		
		
//		Yaml y2 = new Yaml(new Constructor(ItemStack.class));
//		Map<String, Object> i = y2.load(s);
//		i.forEach((k, v) -> {
//			p.sendMessage(k + " --- " + v);
//		});
		
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		p.sendMessage(gson.toJson(item));

	}

	@EventHandler
	public void on(PlayerJumpEvent e) {
//		e.setCancelled(true);
		Bukkit.getScheduler().runTaskLater(this, () -> {
			e.getPlayer().setVelocity(e.getPlayer().getVelocity().setY(1));
		}, 1L);
	}

	@EventHandler
	public void onDmg(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getCause() == DamageCause.FALL) {
				e.setDamage(e.getDamage() * 0.1);
			}
		}
	}

}
