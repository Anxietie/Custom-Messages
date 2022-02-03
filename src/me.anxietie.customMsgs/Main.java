package me.anxietie.customMsgs;

import org.bukkit.plugin.java.JavaPlugin;

import me.anxietie.customMsgs.Commands.CustomMessage;
import me.anxietie.customMsgs.Events.OnAdvancement;
import me.anxietie.customMsgs.Events.OnDeath;

public class Main extends JavaPlugin{
	@Override
	public void onEnable() {
		getLogger().info("OnEnable has been invoked!");
		
		saveDefaultConfig();
		
		getServer().getPluginManager().registerEvents(new OnDeath(this), this);
		getServer().getPluginManager().registerEvents(new OnAdvancement(this), this);
		this.getCommand("CustomMessage").setExecutor(new CustomMessage(this));
		this.getCommand("CustomMessage").setTabCompleter(new CustomMessage(this));
	}
		
	@Override
	public void onDisable() {
		getLogger().info("OnDisable has been invoked!");
	}
}
