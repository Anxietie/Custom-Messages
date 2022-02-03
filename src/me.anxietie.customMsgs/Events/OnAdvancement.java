package me.anxietie.customMsgs.Events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import me.anxietie.customMsgs.Main;

import org.bukkit.ChatColor;

public class OnAdvancement implements Listener{
	
	private final Main main;
	
	public OnAdvancement(Main main) {
		this.main = main;
	}
	
	// When player completes an advancement, this is called
	@EventHandler
	public void OnPlayerAdvancement(PlayerAdvancementDoneEvent event) throws EventException {
		if (event == null)
			return;
		// Name of the advancement (example: "story/root" or "story/mine_diamond")
		String adv = event.getAdvancement().getKey().getKey();
		String message = main.getConfig().getString("adv-prefix");
		// Replaces the '/' with a '-' to match the paths in the config
		adv = adv.replace('/', '-');
		// If they want to use the universal message
		if (main.getConfig().getBoolean("use-universal-adv-msg")) {
			// In a try-catch because of weird errors thrown
			try {
				// Builds the message and broadcasts it
				message += main.getConfig().getString("universal-adv-msg");
				message = message.replaceAll("%player%", event.getPlayer().getName());
				message = message.replaceAll("%advancement%", main.getConfig().getString(adv));
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
			}
			catch (Exception e) {
				e.printStackTrace();
				event.getPlayer().sendMessage("Error");
			}
		}
		// If they don't want to use the universal message
		else {
			try {
				message = main.getConfig().getString(adv);
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
			}
			catch (Exception e) {
				e.printStackTrace();
				event.getPlayer().sendMessage("Error");
			}
		}
	}
}
