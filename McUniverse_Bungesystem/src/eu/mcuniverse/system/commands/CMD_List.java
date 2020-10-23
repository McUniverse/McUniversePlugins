package eu.mcuniverse.system.commands;

import eu.mcuniverse.system.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_List extends Command {

	public CMD_List() {
		super("list");
	}
	
	private final String onlinePrefix = ChatColor.GREEN + "Online " + ChatColor.GRAY + "on " + ChatColor.YELLOW;

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;

			TextComponent comp = new TextComponent(Bungee.prefix + ChatColor.GRAY + "[");
				
				p.sendMessage(Bungee.prefix + "There are §e" + ProxyServer.getInstance().getPlayers().size() + " §7Players §aonline:");
				ProxyServer.getInstance().getPlayers().forEach(player -> {
					ComponentBuilder cb = new ComponentBuilder();
					cb.append(new TextComponent(player.getName()));
					String info = player.getServer().getInfo().getName();
					ComponentBuilder msg = new ComponentBuilder(onlinePrefix + info);
					cb.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, msg.create()));
					cb.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + info));
					cb.append(", ");
					
					for (BaseComponent c : cb.create()) {
						comp.addExtra(c);
					}
				});
				
				comp.addExtra(new TextComponent(ChatColor.GRAY + "]"));
				
				p.sendMessage(comp);
				
				
				

			}
		}
	}

