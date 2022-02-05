package me.anxietie.customMsgs.Events;

import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.anxietie.customMsgs.Main;
import org.bukkit.ChatColor;

public class OnDeath implements Listener{
	
	private final Main main;
	
	public OnDeath(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) throws EventException {
		// If they have them disabled
		if (!main.getConfig().getBoolean("dmsg-enabled"))
			return;
		
		if (event == null)
			return;
		// Gets the cause of death
		String message = main.getConfig().getString("death-prefix");
		String cause = event.getEntity().getLastDamageCause().getCause().toString();
		// If they want to use the universal message
		if (main.getConfig().getBoolean("use-universal-death-msg")) {
			// In a try-catch because of errors thrown
			try {
				// Builds the message based off the values in the config
				message = main.getConfig().getString("universal-death-msg");
				if (message.contains("%player%"))
					message = message.replaceAll("%player%", event.getEntity().getName());
				if (message.contains("%cause%"))
					message = message.replaceAll("%cause%", main.getConfig().getString(cause));
				event.setDeathMessage(ChatColor.translateAlternateColorCodes('&', message));
			}
			catch (Exception e) {
				main.getLogger().info("Error setting death message for "+event.getEntity().getName());
			}
		}
		// If they don't want to use the universal message
		else {
			try {
				message += main.getConfig().getString(cause);
				if (message.contains("%player%"))
					message = message.replaceAll("%player%", event.getEntity().getName());
				event.setDeathMessage(ChatColor.translateAlternateColorCodes('&', message));
			}
			catch (Exception e) {
				main.getLogger().info("Error setting death message for "+event.getEntity().getName());
			}
		}
	}
}
