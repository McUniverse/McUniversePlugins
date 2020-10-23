package eu.mcuniverse.weapons.commands.weaponadmin.subcommand;

import java.util.*;

import org.bukkit.entity.*;

import com.google.common.collect.*;

import eu.mcuniverse.weapons.commands.*;
import eu.mcuniverse.weapons.data.*;
import eu.mcuniverse.weapons.main.*;
import eu.mcuniverse.weapons.weapon.util.*;
import net.md_5.bungee.api.*;

public class GetStatsCommand extends Subcommand {

	@Override
	public String getName() {
		return "getstats";
	}

	@Override
	public String getDescription() {
		return "Get stats of weapon";
	}

	@Override
	public String getSyntax() {
		return "/weaponadmin getstats <weapontype>";
	}

	@Override
	public void perform(Player player, String[] args) {
		if (args.length != 2) {
			player.sendMessage(Messages.WARNING + "/weaponadmin getstats <weapontype>");
		}
		String w = args[1];
		WeaponType type = null;
		try {
			type = WeaponType.valueOf(w.toUpperCase());
		} catch (Exception e) {
			player.sendMessage(Messages.WARNING + "This weapon does not exist: " + w);
			return;
		}
		Weapon weapon = Core.getWeaponManager().getWeapon(type);
		List<String> lore = Lists.newLinkedList();
		lore.add(ChatColor.YELLOW + "Weapontype: " + ChatColor.AQUA + weapon.getWeaponType());
		lore.add(ChatColor.YELLOW + "Damage: " + ChatColor.AQUA + weapon.getDamage() + ChatColor.DARK_RED + "\u2611"); //Heart
		lore.add(ChatColor.YELLOW + "Range: " + ChatColor.AQUA + weapon.getRange());
		lore.add(ChatColor.YELLOW + "Magazine size: " + ChatColor.AQUA + weapon.getMagazineSize());
		lore.add(ChatColor.YELLOW + "Reloading time: " + ChatColor.AQUA + (weapon.getReloadingTime() / 20) + "s");
		lore.add(ChatColor.YELLOW + "Ammunitiontype: " + ChatColor.AQUA + weapon.getAmmunitionType());
		lore.forEach(s -> {
			player.sendMessage(s);
		});
	}

}
