package com.samleighton.xquiset.PigRacing.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtils {
	public static void broadcastMessage(String msg) {
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(starter() + ChatColor.YELLOW + msg);
		}
	}
	
	public static void sendInfo(Player p, String msg) {
		p.sendMessage(starter() + ChatColor.WHITE + msg);
	}
	
	public static void sendError(Player p, String msg) {
		p.sendMessage(starter() + ChatColor.DARK_RED + msg);
	}
	
	public static void sendSuccess(Player p, String msg) {
		p.sendMessage(starter() + ChatColor.DARK_GREEN + msg);
	}
	
	private static String starter() {
		return ChatColor.DARK_GRAY + "[" + ChatColor.LIGHT_PURPLE + "Pig Racers" + ChatColor.DARK_GRAY + "] ";
	}
}
