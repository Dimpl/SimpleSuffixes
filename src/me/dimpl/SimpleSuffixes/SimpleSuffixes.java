package me.dimpl.SimpleSuffixes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleSuffixes extends JavaPlugin {
	
	public final Logger log = Logger.getLogger("Minecraft");
	public String suffixCmd;
	public String prefixCmd;
	public int sufMax;
	public int preMax;
	public String[] allowedRegex;
	public String allowedRegexString;
	public String[] wordBlacklist;
	public String wordBlacklistString;
	public String[] wordStafftags;
	public String wordStafftagsString;
	public String initiates;
	public String inittag;
	private final ChatListener chatListener = new ChatListener(this);
	public static Map<Player, List<Player>> ListenedToBy = new HashMap<Player, List<Player>>();
	String RED = ChatColor.RED.toString();
	
	CommandSender Console;

	@Override
	public void onEnable() {
		//plugin enabled
		PluginDescriptionFile pdffile = this.getDescription();
		Console = getServer().getConsoleSender();
		this.saveDefaultConfig();
		suffixCmd = this.getConfig().getString("suffix-cmd");
		prefixCmd = this.getConfig().getString("prefix-cmd");
		sufMax = this.getConfig().getInt("suffix-max-length");
		preMax = this.getConfig().getInt("prefix-max-length");
		allowedRegexString = this.getConfig().getString("allowed-regex");
		allowedRegex = allowedRegexString.split(" ");
		wordBlacklistString = this.getConfig().getString("word-blacklist");
		wordBlacklist = wordBlacklistString.split(" ");
		wordStafftagsString = this.getConfig().getString("word-stafftags");
		wordStafftags = wordStafftagsString.split(" ");
		initiates = this.getConfig().getString("initiates");
		if (initiates == null)
			initiates = "";
		inittag = this.getConfig().getString("inittag");
		//set up listener
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(chatListener, this);
		this.log.info(pdffile.getName() + " version " + pdffile.getVersion() + " is enabled.");
		//for reloads, add all players into listener map
		for (Player p:getServer().getOnlinePlayers())
			ListenedToBy.put(p, new ArrayList<Player>());
		
	}
	
	@Override
	public void onDisable() {
		//plugin disabled
		PluginDescriptionFile pdffile = this.getDescription();
		this.log.info(pdffile.getName() + " is now disabled.");
		ListenedToBy.clear();
		this.getConfig().set("initiates", initiates);
		this.saveConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equals("suffix")) {
			return onCommandDo(sender, args, "suf", suffixCmd, sufMax, false);
		}
		
		else if (cmd.getName().equals("prefix")) {
			return onCommandDo(sender, args, "pre", prefixCmd, preMax, false);
		}
		
		else if (cmd.getName().equals("suffixr")) {
			return onCommandDo(sender, args, "sufr", suffixCmd, 0, false);
		}
		
		else if (cmd.getName().equals("prefixr")) {
			return onCommandDo(sender, args, "prer", prefixCmd, 0, false);
		}
		
		else if (cmd.getName().equals("listen")) {
			if (sender.getName() == "CONSOLE") {
				sender.sendMessage(RED + "This command cannot be executed from the console.");				
				return false;
			}
			if (args.length == 0) {
				sender.sendMessage(RED + "Please specify a player to listen to.");
				return false;
			}
			
			else if (args.length >= 2) {
				sender.sendMessage(RED + "Too many args.");
				return false;
			}
			
			Player p1 = getServer().getPlayer(args[0]);
			Player p2 = (Player) sender;
			if (p1 == null) {
				sender.sendMessage(RED + "This player is offline.");				
				return false;
			}
			//toggle on
			if (!isListenedToBy(p1, p2)) {
				ListenedToBy.get(p1).add(p2);
				sender.sendMessage(ChatColor.DARK_GREEN + "You are now listening to " + p1.getDisplayName() + ".");
			}
			//toggle off
			else {
				ListenedToBy.get(p1).remove(p2);
				sender.sendMessage(ChatColor.BLUE + "You are no longer listening to " + p1.getDisplayName() + ".");
			}
			return false;
		}
		
		else if (cmd.getName().equals("listening")) {
			if (sender.getName() == "CONSOLE") {
				sender.sendMessage(RED + "This command cannot be executed from the console.");				
				return false;
			}
			Player p2 = (Player) sender;
			StringBuilder listening = new StringBuilder();
			listening.append(ChatColor.GOLD + "You are listening to " + ChatColor.AQUA);
			for (Player p1 : getServer().getOnlinePlayers()) {
				if (isListenedToBy(p1, p2))
					listening.append(" " + p1.getDisplayName() + ",");
			}
			if (listening.charAt(listening.length()-1) == ':')
				listening.append(" nobody.");
			else listening.replace(listening.length()-1, listening.length(), ".");
			sender.sendMessage(listening.toString());
			return true;
		}
		
		else if (cmd.getName().equals("initiate")) {
			if (!sender.hasPermission("simsuf.i")) {
				sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to perform that command.");				
				return false;
			}
			if (args.length == 0) {
				sender.sendMessage(RED + "Please specify a player to promote.");
				return false;
			}
			else if (args.length >= 2) {
				sender.sendMessage(RED + "Too many args.");
				return false;
			}
			
			Player player = getServer().getPlayer(args[0]);
			if (player == null) {
				sender.sendMessage(RED + "This player is offline.");
				return false;
			}
			
			if (!initiates.contains(player.getDisplayName())) {
				initiates = initiates.concat(player.getDisplayName() + " ");
				sender.sendMessage(ChatColor.DARK_GREEN + player.getDisplayName() + " is now an initiate!");
				return onCommandDo(player, inittag.split(" "), "pre", prefixCmd, 0, true);
			}
			
			else {
				initiates = initiates.replaceAll(player.getDisplayName() + " ", "");
				sender.sendMessage(ChatColor.BLUE + player.getDisplayName() + " is no longer an initiate!");
				return onCommandDo(player, player.getDisplayName().split(" "), "prer", prefixCmd, 0 , true);
			}
		}
		
		else if (cmd.getName().equals("initiates")) {
			if (!sender.hasPermission("simsuf.i")) {
				sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to perform that command.");				
				return false;
			}
			
			String initiatess = initiates;
			if (initiatess == "")
				initiatess = "none";
			sender.sendMessage(ChatColor.GRAY + "Initiates: " + initiatess);
			return true;
		}
		
		else return false;
	}
	
	public boolean onCommandDo(CommandSender sender, String[] args, String type, String cmd_fix, Integer max, Boolean bypass) {
		if (type == "pre" || type == "suf") {
			if (!sender.hasPermission("simsuf." + type.substring(0, 1)) && !bypass) {
				sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to perform that command.");				
				return false;
			}
			if (sender.getName() == "CONSOLE") {
				sender.sendMessage(RED + "This command cannot be executed from the console.");				
				return false;
			}
			if (args.length == 0) {
				sender.sendMessage(RED + "Please specify a " + type + "fix.");
				return false;
			}
			
			//String[] --> String
			String strArgs = Arrays.toString(args);
			strArgs = strArgs.substring(1, strArgs.length()-1).replaceAll(",", "");
			
			int length = Count(strArgs, sender.hasPermission("simsuf.staff"), bypass);
			
			//blacklisted word
			if (length == -1) {
				sender.sendMessage(RED + "That " + type + "fix is not allowed.");				
				return false;				
			}
			
			//test length
			if (length > max) {
				sender.sendMessage(RED + WordUtils.capitalize(type) + "fix too long (max " + max + " chars).");
				return false;
			}
			else {
				String cmd = cmd_fix.replace("%P", sender.getName());
				cmd = cmd.replace("%M", strArgs);
				log.info(cmd);
				getServer().dispatchCommand(Console, cmd);
				if (!bypass)
					sender.sendMessage(ChatColor.AQUA + "Your " + type + "fix = \"" + strArgs + "\"");
				return true;
			}
		}
		
		else if(type == "prer" || type == "sufr") {
			if (!sender.hasPermission("simsuf.r") && !bypass) {
				sender.sendMessage(ChatColor.DARK_RED + "You do not have permission to perform that command.");				
				return false;
			}
			
			if (args.length == 0) {
				sender.sendMessage(RED + "Please specify a player.");
				return false;
			}
			else if (args.length >= 2) {
				sender.sendMessage(RED + "Too many args.");
				return false;
			}
			else {
				Player player = this.getServer().getPlayer(args[0]);
				if (player == null) {
					sender.sendMessage(ChatColor.RED + "Player is offline.");				
					return false;
				}
				String cmd = cmd_fix.replace("%P", player.getName());
				cmd = cmd.replace("%M &f", "");
				cmd = cmd.replace(" %M&f", "");
				getServer().dispatchCommand(Console, cmd);
				if (!bypass)
					sender.sendMessage(ChatColor.AQUA + player.getName() + "'s " + type.substring(0, 3) + "fix has been reset.");
				return true;
			}
		}
		return false;
	}
	
	public int Count(String string, Boolean staff, Boolean bypass) {
		if (bypass)
			return 0;
		//count extra chars in string
		String allowed = string;
		for (String regex : allowedRegex)
			allowed = allowed.replaceAll("\\" + regex, "");
		for (String blacklist : wordBlacklist) {
			if (allowed.toLowerCase().contains(blacklist))
				return -1;
		}
		if (!staff) {
			for (String stafftags : wordStafftags) {
				if (allowed.toLowerCase().contains(stafftags))
					return -1;
			}
		}
		return allowed.length();
	}
	
	public boolean isListenedToBy(Player p1, Player p2) {
		return ListenedToBy.get(p1).contains(p2);
	}
	
	public void createListenedToBy(Player p1) {
		ListenedToBy.put(p1, new ArrayList<Player>());
	}
	
	public void removeListenedToBy(Player p2) {
		for(Player p1: getServer().getOnlinePlayers()) {
			if(ListenedToBy.get(p1).contains(p2))
				ListenedToBy.get(p1).remove(p2);
		}
		ListenedToBy.remove(p2);
	}
	
}
