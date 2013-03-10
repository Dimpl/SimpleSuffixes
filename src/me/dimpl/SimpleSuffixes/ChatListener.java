package me.dimpl.SimpleSuffixes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatListener implements Listener {

	public static SimpleSuffixes plugin;
	
	public ChatListener(SimpleSuffixes instance) {
	//create plugin instance
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		//if plugin enabled, set block type
		for(Player p: Bukkit.getOnlinePlayers())
		if(plugin.isFollowing(p, event.getPlayer()))
			/*FOR P*/event.setMessage(ChatColor.DARK_GREEN + "[" + ChatColor.AQUA + "FOLLOWED" + ChatColor.DARK_GREEN + "]" + ChatColor.BOLD + event.getMessage());
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		plugin.createFollowing(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		plugin.removeFollowing(event.getPlayer());
	}	
}