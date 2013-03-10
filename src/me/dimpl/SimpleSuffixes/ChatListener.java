package me.dimpl.SimpleSuffixes;

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
		if(!event.getMessage().contains(ChatColor.DARK_GREEN + "[" + ChatColor.AQUA + "LISTENING" + ChatColor.DARK_GREEN + "]")) {
			//Only send to players not listening
			for(Player p2: SimpleSuffixes.ListenedToBy.get(event.getPlayer()))
				event.getRecipients().remove(p2);
			if (!SimpleSuffixes.ListenedToBy.get(event.getPlayer()).isEmpty())
				//send elevated message
				event.getPlayer().chat(ChatColor.DARK_GREEN + "[" + ChatColor.AQUA + "LISTENING" + ChatColor.DARK_GREEN + "]" + " " + ChatColor.GOLD + ChatColor.BOLD + event.getMessage() + ChatColor.RESET);
		}
		else {
			event.getRecipients().clear();
			for(Player p2: SimpleSuffixes.ListenedToBy.get(event.getPlayer()))
				event.getRecipients().add(p2);
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		plugin.createListenedToBy(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		plugin.removeListenedToBy(event.getPlayer());
	}	
}