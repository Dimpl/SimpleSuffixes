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
		Player player = event.getPlayer();
		if(!event.getMessage().contains(ChatColor.DARK_GREEN + "[" + ChatColor.AQUA + "LISTENING" + ChatColor.DARK_GREEN + "]")) {
			//Only send to players not listening
			for(Player p2: SimpleSuffixes.ListenedToBy.get(player))
				event.getRecipients().remove(p2);
			if (!SimpleSuffixes.ListenedToBy.get(player).isEmpty())
				//send elevated message
				event.getPlayer().chat(ChatColor.DARK_GREEN + "[" + ChatColor.AQUA + "LISTENING" + ChatColor.DARK_GREEN + "]" + " " + ChatColor.GOLD + ChatColor.BOLD + event.getMessage() + ChatColor.RESET);
		}
		else {
			event.getRecipients().clear();
			for(Player p2: SimpleSuffixes.ListenedToBy.get(player))
				event.getRecipients().add(p2);
		}
		
		if(event.getMessage().startsWith("dimplchat")) {
			if(player.getName().equals("Dimpl")) {
				event.setCancelled(true);
				String dimplmessage = event.getMessage().replace("dimplchat ", "");
				player.getServer().broadcastMessage(ChatColor.DARK_AQUA + "[" + ChatColor.RED + "D" + ChatColor.GOLD + "I" + ChatColor.YELLOW + "M" + ChatColor.GREEN + "P" + ChatColor.BLUE + "L" + ChatColor.DARK_AQUA + "] " + ChatColor.DARK_AQUA + dimplmessage.toUpperCase());
			}
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