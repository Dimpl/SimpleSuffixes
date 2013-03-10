package me.dimpl.SimpleSuffixes;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
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
		if(/*another player's instance of*/plugin.isFollowing(event.getPlayer()))
				/*for that other player*/event.setMessage(ChatColor.DARK_GREEN + "[" + ChatColor.AQUA + "FOLLOWED" + ChatColor.DARK_GREEN + "]" + ChatColor.BOLD + event.getMessage());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		/*the player's instance of */plugin.Following.clear();
		if(/*another player's instance of*/plugin.isFollowing(event.getPlayer()))
			/* for that player's instance*/plugin.stopFollowing(event.getPlayer());
	}
	
}