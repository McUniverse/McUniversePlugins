package eu.mcuniverse.system.commands;

import eu.mcuniverse.system.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Teamchat extends Command {
	
	public CMD_Teamchat() {
		super("CMD_Teamchat", "", new String[] {"teamchat", "tc"});
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
	  if(sender instanceof ProxiedPlayer) {
		  ProxiedPlayer p = (ProxiedPlayer)sender;
		if(p.hasPermission("mcuniverse.teamchat")) {
		   if(args.length == 0) {
			   p.sendMessage(Bungee.teamchat + "§cUse: /tc <Message>");

		   } else {
	            StringBuilder sb = new StringBuilder();
	            int i = 0;
	            while (i < args.length) {
	                sb.append(String.valueOf(args[i]) + " ");
	                ++i;
	            }
	            String st = sb.toString();
				for(ProxiedPlayer all : Bungee.instance.getProxy().getPlayers()) {
					if(all.hasPermission("mcuniverse.teamchat")) {
					all.sendMessage(Bungee.teamchat + p.getName() + " §8» §c" + st.replace("&", "§"));
					}
					System.out.println(Bungee.teamchat + p.getName() + " §8» §c" + st.replace("&", "§"));
			}
	            
			}
		   } else {
			   p.sendMessage(Bungee.perms);
		   }
	  } else {
		  sender.sendMessage(Bungee.teamchat + "§cNo Player");
	  }
		
	}

}