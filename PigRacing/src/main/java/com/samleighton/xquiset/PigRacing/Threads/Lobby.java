package com.samleighton.xquiset.PigRacing.Threads;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.samleighton.xquiset.PigRacing.GameState;
import com.samleighton.xquiset.PigRacing.PigRacing;
import com.samleighton.xquiset.PigRacing.Objects.Game.Racer;
import com.samleighton.xquiset.PigRacing.Objects.Scoreboards.GameBoard;
import com.samleighton.xquiset.PigRacing.Utilities.ChatUtils;

public class Lobby extends BukkitRunnable{
	
	private final PigRacing plugin;
	private GameBoard lobbyBoard = new GameBoard();
	private int timeLeft;
	private String boardDisplay = ChatColor.BOLD + "" +ChatColor.ITALIC + "Lobby";
	private String time = ChatColor.BLUE + "Time:";
	private String players = ChatColor.GREEN + "Players:";
	private List<Racer> racers = new ArrayList<Racer>();
	
	public Lobby(PigRacing pl, int lobbyTime) {
		this.plugin = pl;
		this.timeLeft = lobbyTime;
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.teleport(plugin.getLobbyLocation());
		}
		
		lobbyBoard.addScore(time, "lobby", boardDisplay, timeLeft);
		lobbyBoard.addScore(players, "lobby", boardDisplay, Bukkit.getOnlinePlayers().size());
	}

	@Override
	public void run() {
		if(GameState.getState() == GameState.IN_LOBBY) {
			timeLeft--;
			lobbyBoard.updateScore(time, timeLeft);
			lobbyBoard.updateScore(players, Bukkit.getOnlinePlayers().size());
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.setScoreboard(lobbyBoard.getBoard());
			}
			
			if(timeLeft == 0) {
				if(canStart()) {
					GameState.setState(GameState.RACING);
				}else {
					GameState.setState(GameState.IN_LOBBY);
				}
				
				if(GameState.getState() == GameState.RACING) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						Racer r = new Racer(p);
						racers.add(r);
					}
					plugin.lobbyStop();
					plugin.gameStart(racers);
				}else{
					ChatUtils.broadcastMessage("Not enough players to start, resetting countdown!");
					restartTimer();
				}
			}
		}
	}
	
	public void restartTimer() {
		plugin.lobbyStop();
		plugin.lobbyStart();
	}
	
	public boolean canStart() {
		return Bukkit.getOnlinePlayers().size() >= plugin.PLAYERS_TO_START_MATCH; 
	}
}
