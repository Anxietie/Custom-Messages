package me.anxietie.customMsgs.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import me.anxietie.customMsgs.Main;

public class CustomMessage implements TabExecutor {
	
	private final String configUsage = "/cmsg <reload|get|set> <|(PATH)|(PATH)> <||(VALUE)>";
	
	private final Main main;
	
	public CustomMessage(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			// Syntax checking
			if (args.length < 1) {
				sender.sendMessage(ChatColor.RED+"Incorrect syntax! Usage: "+ChatColor.GRAY+configUsage);
				return false;
			}
			// Command checking
			switch (args[0].toLowerCase()) {
				case "reload":
					// Permission checking
					if (!sender.hasPermission("cmsg.reload")) {
						sender.sendMessage(ChatColor.RED+"Insufficient permissions!");
						return true;
					}
					main.reloadConfig();
					sender.sendMessage(ChatColor.GREEN+"Config successfully reloaded!");
					break;
				case "get":
					// Permission checking
					if (!sender.hasPermission("cmsg.get")) {
						sender.sendMessage(ChatColor.RED+"Insufficient permissions!");
						return true;
					}
					// Syntax checking
					if (args.length != 2) {
						sender.sendMessage(ChatColor.RED+"Incorrect syntax! Usage: "+ChatColor.GRAY+configUsage);
						return false;
					}
					try {
						sender.sendMessage(ChatColor.GOLD+args[1]+": "+ChatColor.GREEN + main.getConfig().getString(args[1]));
					}
					catch (Exception e) {
						sender.sendMessage(ChatColor.RED+args[1]+" doesn't exist!");
					}
					break;
				case "set":
					// Permission checking
					if (!sender.hasPermission("cmsg.set")) {
						sender.sendMessage(ChatColor.RED+"Insufficient permissions!");
						return true;
					}
					// Syntax checking
					if (args.length < 3) {
						sender.sendMessage(ChatColor.RED+"Incorrect syntax! Usage: "+ChatColor.GRAY+configUsage);
						return false;
					}
					try {
						String value = "";
						// Builds string based off the arguments of what the player wants to set the path to
						for (int i = 2; i < args.length; i++)
							value += args[i]+(i == args.length-1 ? "" : " ");
						main.getConfig().set(args[1], value);
						main.saveConfig();
						sender.sendMessage(ChatColor.GOLD+args[1]+ChatColor.GRAY+" successfully changed to: "+ChatColor.GREEN+value);
					}
					catch (Exception e) {
						sender.sendMessage(ChatColor.RED+args[1]+" doesn't exist!");
					}
					break;
				// If they do some other random command
				default:
					sender.sendMessage(ChatColor.RED+"Incorrect syntax! Usage: "+ChatColor.GRAY+configUsage);
					return false;
			}
		}
		// Lol
		else
			sender.sendMessage("Lol just change the config itself if you're at the console. Rip if this is a command block");
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			List<String> arg1 = new ArrayList<>();
			arg1.add("reload");
			arg1.add("set");
			arg1.add("get");
			return arg1;
		}
		else if (args.length == 2 && (args[0].equalsIgnoreCase("get") || args[0].equalsIgnoreCase("set"))) {
			List<String> arg2 = new ArrayList<>();
			Set<String> temp = main.getConfig().getKeys(false);
			for (String key : temp)
				arg2.add(key);
			return arg2;
		}
		else
			return new ArrayList<String>();
	}
	
}
