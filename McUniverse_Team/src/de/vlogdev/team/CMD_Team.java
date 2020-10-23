package de.vlogdev.team;

import java.util.List;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Team extends Command{

	public CMD_Team() {
		super("CMD_Team", "", new String[] {"staff", "team"});
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer){
			ProxiedPlayer pp = (ProxiedPlayer) sender;

			pp.sendMessage(new ComponentBuilder("§7[]§8§m--------------§7[] §6McUniverse §7[]§8§m--------------§7[]").create());
			pp.sendMessage(new ComponentBuilder("").create());
			
			sendTeamMembers(pp, "§4Admin §8» ", Team.admins);
			sendTeamMembers(pp, "§bSrDev §8» ", Team.srdevs);
			sendTeamMembers(pp, "§bDev §8» ", Team.devs);
			sendTeamMembers(pp, "§cSrMod §8» ", Team.srmods);
			sendTeamMembers(pp, "§cMod §8» ", Team.mods);
			sendTeamMembers(pp, "§aHBuild §8» ", Team.harchis);
			sendTeamMembers(pp, "§aBuild §8» ", Team.archis);
			sendTeamMembers(pp, "§eSup §8» ", Team.sups);
			sendTeamMembers(pp, "§dRekrut §8» ", Team.rekrut);
			
			pp.sendMessage(new ComponentBuilder("").create());
			pp.sendMessage(new ComponentBuilder("§7[]§8§m--------------§7[] §6McUniverse §7[]§8§m--------------§7[]").create());
			
		} else {
			sender.sendMessage(new TextComponent("No Player"));
		}
	}

	public void sendTeamMembers(ProxiedPlayer pp, String prefix, List<String> list){
		TextComponent comp = new TextComponent(prefix);
		if(!list.isEmpty()){
			for(String s : list){
				ComponentBuilder cb = new ComponentBuilder("");
				cb.append((Team.team.getProxy().getPlayer(s) != null ? "§a" : "§7") + s);
				if(Team.team.getProxy().getPlayer(s) != null){
						cb.event(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§aOnline §7of §e" + Team.team.getProxy().getPlayer(s).getServer().getInfo().getName()).create()));
				}
				
				for(BaseComponent bc : cb.create())
					comp.addExtra(bc);
				comp.addExtra(" §8❘ ");
			}
			pp.sendMessage(comp);
		} else {
			ComponentBuilder cb = new ComponentBuilder("§eGesucht!");
			for(BaseComponent base : cb.create())
				comp.addExtra(base);
			pp.sendMessage(comp);
		}
		
	}
}
