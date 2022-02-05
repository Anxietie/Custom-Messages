package me.anxietie.customMsgs.Events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import me.anxietie.customMsgs.Main;

import org.bukkit.ChatColor;
import org.bukkit.Sound;

public class OnAdvancement implements Listener{
	
	private final Main main;
	
	public OnAdvancement(Main main) {
		this.main = main;
	}
	
	// When player completes an advancement, this is called
	@EventHandler
	public void OnPlayerAdvancement(PlayerAdvancementDoneEvent event) throws EventException {
		// If they have them disabled
		if (!main.getConfig().getBoolean("amsg-enabled"))
			return;
		
		if (event == null)
			return;
		// Name of the advancement (example: "story/root" or "story/mine_diamond")
		String adv = event.getAdvancement().getKey().getKey();
		if (adv.contains("recipes"))
			return;
		String message = main.getConfig().getString("adv-prefix");
		// Replaces the '/' with a '-' to match the paths in the config
		adv = adv.replace('/', '-');
		// If they want to use the universal message
		if (main.getConfig().getBoolean("use-universal-adv-msg")) {
			// In a try-catch because of weird errors thrown
			try {
				// Builds the message and broadcasts it
				message = main.getConfig().getString("universal-adv-msg");
				if (message.contains("%player%"))
					message = message.replaceAll("%player%", event.getPlayer().getName());
				if (message.contains("%advancement%"))
					message = message.replaceAll("%advancement%", main.getConfig().getString(adv));
				event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.5f, 1);
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
			}
			catch (Exception e) {
				main.getLogger().info("Error getting advancement for "+event.getPlayer().getName());
			}
		}
		// If they don't want to use the universal message
		else {
			try {
				message += main.getConfig().getString(adv);
				event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.5f, 1);
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
			}
			catch (Exception e) {
				main.getLogger().info("Error getting advancement for "+event.getPlayer().getName());
			}
		}
	}
}
