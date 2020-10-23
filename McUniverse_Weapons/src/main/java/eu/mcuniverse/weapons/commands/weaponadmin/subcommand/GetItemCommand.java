package eu.mcuniverse.weapons.commands.weaponadmin.subcommand;

import org.bukkit.entity.Player;

import eu.mcuniverse.weapons.commands.Subcommand;
import eu.mcuniverse.weapons.data.Messages;
import eu.mcuniverse.weapons.item.Items;
import eu.mcuniverse.weapons.main.Core;
import eu.mcuniverse.weapons.weapon.util.AmmunitionType;

public class GetItemCommand extends Subcommand {

	@Override
	public String getName() {
		return "getitem";
	}

	@Override
	public String getDescription() {
		return "Get all the weapon items";
	}

	@Override
	public String getSyntax() {
		return "/weaponadmin getitem [ammunition]";
	}

	@Override
	public void perform(Player player, String[] args) {
		if (args.length > 1) {
			player.getInventory().addItem(Items.getAmmunition(AmmunitionType.NORMAL, 64));
			player.getInventory().addItem(Items.getAmmunition(AmmunitionType.ROCKET, 64));
			player.getInventory().addItem(Items.getAmmunition(AmmunitionType.LASER, 64));
			player.sendMessage(Messages.PREFIX + "You got ammunition!");
			return;
		}
		Core.getWeaponManager().getItems().stream().forEach(player.getInventory()::addItem);
//		Core.getWeaponManager().getWeapons().forEach(weapon -> {
//			player.getInventory().addItem(WeaponManager.getItem(weapon));
//		});
		player.getInventory().addItem(Items.getAmmunition(AmmunitionType.NORMAL, 64));
		player.getInventory().addItem(Items.getAmmunition(AmmunitionType.ROCKET, 64));
		player.getInventory().addItem(Items.getAmmunition(AmmunitionType.LASER, 64));
		player.sendMessage(Messages.PREFIX + "You got the items!");
	}

}
