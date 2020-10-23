package de.jayreturns.rocket;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import de.jayreturns.main.Main;
import de.jayreturns.planets.Planet;
import de.jayreturns.util.ItemUtil;
import de.jayreturns.util.Messages;
import de.jayreturns.util.RocketUtil;

public class RocketLauncher implements Listener {

  public static void openLaunchMenu(Player p) {
    if (RocketUtil.testDistanceToRocket(p)) {
      p.sendMessage(Messages.prefix + "§cDu bist zu weit von deiner Rakete entfernt!");
      return;
    }

    Inventory inv = Bukkit.createInventory(p, 54, "§5Start your rocket?");

    for (int i = 0; i < 54; i++) {
      inv.setItem(i, ItemUtil.getFiller());
    }

    for (int i = 0; i < 9; i++) {
      ItemStack item =
          ItemUtil.createItem(Material.PAPER, capitalize(Planet.values()[i].toString()));
      if (!RocketUtil.accesiblePlanets(Rocket.getRocket(p).getTier())
          .contains(Planet.values()[i])) {
        item.setType(Material.REDSTONE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList("", "§cNot accessible with your rocket tier!"));
        item.setItemMeta(meta);
      }
      inv.setItem(9 + i, item);
    }

    Arrays.asList(30, 31, 32, 39, 40, 41, 48, 49, 50).forEach(item -> {
      inv.setItem(item, ItemUtil.createItem(Material.LIME_WOOL, "§2Confirm launch"));
    });
    inv.setItem(53, ItemUtil.createItem(Material.BARRIER, "§4Abort launch"));

    p.openInventory(inv);
  }

  @EventHandler
  public void onClick(InventoryClickEvent e) {
    Player p = (Player) e.getWhoClicked();
    if (e.getView().getTitle().contains("§5Start your rocket?")) {
      e.setCancelled(true);
      if (e.getCurrentItem().getType() == Material.PAPER) {
        Inventory inv = e.getClickedInventory();
        for (int i = 0; i < 9; i++) {
          ItemStack is = inv.getItem(9 + i);
          is.removeEnchantment(Enchantment.DURABILITY);
        }
        ItemStack click = inv.getItem(e.getSlot());
        ItemMeta meta = click.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setLore(Arrays.asList("", "§eSelected planet"));
        click.setItemMeta(meta);
        inv.setItem(e.getSlot(), click);

        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
      } else if (e.getCurrentItem().getType() == Material.LIME_WOOL) {
        Planet planet = null;
        Planet current = Planet.getPlanet(p);
        boolean found = false;
        for (int i = 0; i < 9; i++) {
          ItemStack is = e.getClickedInventory().getItem(9 + i);
          if (is.getEnchantments().containsKey(Enchantment.DURABILITY)) {
            planet = Planet.valueOf(is.getItemMeta().getDisplayName().toUpperCase());
            found = true;
          }
        }
        if (!found || planet == null) {
          p.sendMessage(Messages.prefix + "§cPlease select a planet!");
          return;
        }
        if (current != null && planet == current) {
          p.sendMessage(Messages.prefix + "§cYou're already on this planet");
          return;
        }

        p.sendTitle("§2Starting rocket", "", 0, 20, 0);
        p.closeInventory();
        new Countdown(p, planet);
      } else if (e.getCurrentItem().getType() == Material.BARRIER) {
        p.closeInventory();
      }
    }
  }

  private class Countdown extends BukkitRunnable {
    int count = 10;
    Player p;
    Planet planet;

    public Countdown(Player p, Planet planet) {
      this.p = p;
      this.planet = planet;
      runTaskTimer(Main.getInstance(), 40L, 20L);
    }

    @Override
    public void run() {
      p.sendTitle("§d§l" + count, "", 5, 10, 5);
      count--;
      if (count <= -1) {
        cancel();
        p.sendTitle("§aLaunching...", "§oTo be continued...", 10, 70, 20);
        RocketTeleporter.teleportToNewPlanet(p, planet);
      }
      p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0.25f);
    }
  }

  public static String capitalize(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }

    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
  }

}
