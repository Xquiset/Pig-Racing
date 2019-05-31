package com.samleighton.xquiset.PigRacing.EventListeners.InGame;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.samleighton.xquiset.PigRacing.GameState;
import com.samleighton.xquiset.PigRacing.PigRacing;
import com.samleighton.xquiset.PigRacing.Threads.Game;

public class PlayerQuitGame implements Listener{
	
	private PigRacing pl;
	
	public PlayerQuitGame(PigRacing plugin) {
		this.pl = plugin;
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		if(GameState.getState() == GameState.RACING) {
			Game currentGame = pl.getGame();
			if(currentGame.hasID(e.getPlayer().getUniqueId())) {
				currentGame.removePlayerFromGame(e.getPlayer().getUniqueId());
			}
		}
	}

}
