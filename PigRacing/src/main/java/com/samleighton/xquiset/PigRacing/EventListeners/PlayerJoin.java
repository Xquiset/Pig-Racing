package com.samleighton.xquiset.PigRacing.EventListeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.samleighton.xquiset.PigRacing.GameState;
import com.samleighton.xquiset.PigRacing.PigRacing;

public class PlayerJoin implements Listener{
	
	private PigRacing pl;
	
	public PlayerJoin(PigRacing plugin) {
		this.pl = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(GameState.getState() == GameState.IN_LOBBY) {
			p.teleport(pl.getLobbyLocation());
			p.getInventory().setContents(new ItemStack[0]);
		}else if(GameState.getState() == GameState.RACING) {
			p.kickPlayer(ChatColor.DARK_RED + "The game has already started come back later!");
		}else {
			p.kickPlayer(ChatColor.DARK_RED + "The game is cleaning up right now try again shortly!");
		}
	}
}
