package eu.mcuniverse.rocket.commands.rocketadmin.subcommand;

import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.universeapi.command.SubCommand;
import eu.mcuniverse.universeapi.item.CustomItem;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class CustomItemCommand extends SubCommand {

	@Override
	public String getName() {
		return "customitem";
	}

	@Override
	public String getDescription() {
		return "Get all custom items";
	}

	@Override
	public String getSyntax() {
		return "/rocketadmin customitem";
	}

	@Override
	public void perform(Player player, String[] args) {
		Inventory inv = Bukkit.createInventory(null, 54, "Custom Items");
		ObjectArrayList<ItemStack> items = new ObjectArrayList<>();
		items.add(CustomItem.airfilter);
		items.add(CustomItem.whiteleather);
		items.add(CustomItem.enhancedcoal);
		items.add(CustomItem.enhancedredstone);
		items.add(CustomItem.enhancedGlass);
		items.add(CustomItem.lighter);
		items.add(CustomItem.enhancedIron);
		items.add(CustomItem.CircutBoard);
		items.add(CustomItem.enhancedWood);
		items.add(CustomItem.goldWire);
		items.add(CustomItem.enhancedChest);
		items.add(CustomItem.ghastt);
		items.add(CustomItem.grenade);
		items.add(CustomItem.c4);
		items.add(CustomItem.igniter);
		items.add(CustomItem.ammu);
		items.add(CustomItem.rocketammu);
		items.add(CustomItem.laserammu);
		items.add(CustomItem.enhancedCompass);
		items.add(CustomItem.cobweb);

		items.add(CustomItem.Rocket.getEnhancedCompass());
		items.add(CustomItem.Rocket.getOil());
		items.add(CustomItem.Rocket.getRocketFuel());

		items.add(CustomItem.Oil.getBlueprint());
		
		items.stream().filter(i -> i.getType().getMaxStackSize() > 1).forEach(i -> i.setAmount(64));
		
		AtomicInteger i = new AtomicInteger(0);
		items.stream().forEach(item -> inv.setItem(i.getAndIncrement(), item));
		
		player.openInventory(inv);
		
	}

}
