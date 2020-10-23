package eu.mcuniverse.weapons.main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.nbt.NbtBase;

import eu.mcuniverse.universeapi.item.CustomItem;
import eu.mcuniverse.weapons.commands.weaponadmin.WeaponAdminCommandManager;
import eu.mcuniverse.weapons.explosives.Grenade;
import eu.mcuniverse.weapons.explosives.Mine;
import eu.mcuniverse.weapons.listener.Canceler;
import eu.mcuniverse.weapons.listener.DamageListener;
import eu.mcuniverse.weapons.listener.LeatherDisabled;
import eu.mcuniverse.weapons.listener.ProjectileHitListener;
import eu.mcuniverse.weapons.listener.ResourcepackListener;
import eu.mcuniverse.weapons.listener.ShootListener;
import eu.mcuniverse.weapons.listener.ZoomListener;
import eu.mcuniverse.weapons.util.ItemUtil;
import eu.mcuniverse.weapons.weapon.util.WeaponManager;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Core extends JavaPlugin {

	@Getter
	private static Core instance;
	private static WeaponManager weaponManager;

	@Getter
	private static ProtocolManager protocolManager;

	@Override
	public void onEnable() {
		instance = this;
		protocolManager = ProtocolLibrary.getProtocolManager();
		
		register();

		// Generate config for hash
		getConfig().options().copyDefaults(true);
		getConfig().addDefault("url", "http://mcuniverse.eu/McUniverse.zip");
		getConfig().addDefault("hash", "SHA1-HASH");
		saveConfig();
		
		getLogger().info("Plugin enabled");

		Bukkit.getServer().getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void on(PlayerCommandPreprocessEvent e) {
				if (!e.getPlayer().isOp()) {
					return;
				}
				if (e.getMessage().contains("/nbt")) {
					e.setCancelled(true);
					Player p = e.getPlayer();
					ItemStack item = p.getInventory().getItemInMainHand();
					if (item != null) {
						Map<String, NbtBase<?>> map = ItemUtil.NBT.getValues(item);
						map.forEach((k, v) -> {
							p.sendMessage(k + " --- " + v.toString());
						});
					}
				} else if (e.getMessage().startsWith("/armor")) {
					ObjectList<ItemStack> items = new ObjectArrayList<ItemStack>();

					Class<?> clazz = CustomItem.Armor.class;
					for (Class<?> c : clazz.getClasses()) {
						for (Method m : c.getDeclaredMethods()) {
							try {
								ItemStack ret = (ItemStack) m.invoke(null);
								items.add(ret);
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
								e1.printStackTrace();
								continue;
							}
						}
					}
					items.forEach(e.getPlayer().getInventory()::addItem);
				} else if (e.getMessage().startsWith("/book")) {
					e.setCancelled(true);
					ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
					BookMeta meta = (BookMeta) book.getItemMeta();
					meta.setAuthor(ChatColor.LIGHT_PURPLE + "McUniverse.eu");
					meta.setTitle(ChatColor.DARK_RED + "Resourcepack");

					BaseComponent[] text = new ComponentBuilder("   ")
							.append("Resource Pack").color(ChatColor.BLACK).bold(true).underlined(true)
							.append("\n")
							.append("This server uses a resource pack for the cool textures and models. Download it for the bset experience.\n\n\n").bold(false).underlined(false)
							.append("    ")
							.append("Click to load!").color(ChatColor.DARK_GREEN).underlined(true).bold(true)
							.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Load the resourcepack", ChatColor.GREEN)))
							.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/resource"))
							.create();
					
					meta.spigot().setPages(text);

					book.setItemMeta(meta);

					e.getPlayer().openBook(book);
					e.getPlayer().getInventory().addItem(book);
				}
			}
		}, this);

		getProtocolManager()
				.addPacketListener(new PacketAdapter(this, ListenerPriority.HIGH, PacketType.Play.Server.NAMED_SOUND_EFFECT) {
					@Override
					public void onPacketSending(PacketEvent event) {
						if (event.getPacketType() == PacketType.Play.Server.NAMED_SOUND_EFFECT) {
							PacketContainer packet = event.getPacket();
							if (packet.getSoundEffects().read(0) == Sound.ITEM_ARMOR_EQUIP_LEATHER) {
								event.setCancelled(true);
							}
						}
					}
				});

	}

	public static WeaponManager getWeaponManager() {
		if (weaponManager == null) {
			weaponManager = new WeaponManager();
		}
		return weaponManager;
	}

	private void register() {
		registerEvents();
		registerCommands();
		
	}

	private void registerCommands() {
//		getCommand("resource").setExecutor(new ResourceCommand());
//		getCommand("resource").setTabCompleter(new ResourceCommand());
		getCommand("weaponadmin").setExecutor(new WeaponAdminCommandManager());
	}

	private void registerEvents() {
		new ShootListener(this);
		new DamageListener(this);
		new ProjectileHitListener(this);
		new ZoomListener(this);
		new Canceler(this);
		new ResourcepackListener(this);
		new LeatherDisabled(this);

		new Grenade(this);
		new Mine(this);
	}

}
