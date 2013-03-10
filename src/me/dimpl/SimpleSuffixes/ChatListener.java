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
		if(!event.getMessage().contains("[FOLLOWED]")) {
			//Only send to players not following
			for(Player p:Bukkit.getOnlinePlayers()) {
				if(SimpleSuffixes.Following.get(p).contains(event.getPlayer()))
					event.getRecipients().remove(p);
			}
			//send elevated message
			event.getPlayer().chat(ChatColor.DARK_GREEN + "[" + ChatColor.AQUA + "FOLLOWED" + ChatColor.DARK_GREEN + "]" + ChatColor.BOLD + event.getMessage());
		}
		else {
			event.getRecipients().clear();
			for(Player p:Bukkit.getOnlinePlayers()) {
				if(SimpleSuffixes.Following.get(p).contains(event.getPlayer()))
					event.getRecipients().add(p);
			}
		}
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