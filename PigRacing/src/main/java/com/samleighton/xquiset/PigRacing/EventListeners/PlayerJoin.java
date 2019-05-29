package com.samleighton.xquiset.PigRacing.EventListeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
		}else if(GameState.getState() == GameState.RACING) {
			p.kickPlayer(ChatColor.DARK_RED + "The game has already started come back later!");
		}
	}
}
