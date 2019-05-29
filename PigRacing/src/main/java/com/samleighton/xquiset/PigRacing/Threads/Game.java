package com.samleighton.xquiset.PigRacing.Threads;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import com.samleighton.xquiset.PigRacing.PigRacing;
import com.samleighton.xquiset.PigRacing.Objects.Game.Racer;
import com.samleighton.xquiset.PigRacing.Objects.Scoreboards.GameBoard;

public class Game extends BukkitRunnable{
	
	@SuppressWarnings("unused")
	private PigRacing plugin;
	private GameBoard gameBoard = new GameBoard();
	private int timeElapsed = 0;
	private String boardDisplay = ChatColor.BOLD + "" +ChatColor.ITALIC + "Racers";
	private String time = ChatColor.BLUE + "Time:";
	private List<Racer> racers = new ArrayList<Racer>();
	
	public Game(PigRacing pl, List<Racer> activeRacers) {
		this.plugin = pl;
		this.racers = activeRacers;
		gameBoard.addScore(time, "racers", boardDisplay, timeElapsed);
		
		for(Racer r : racers) {
			gameBoard.addScore(r.getRacer().getName(), "racers", boardDisplay, r.getCheckpointsTripped());
			r.getRacer().setScoreboard(gameBoard.getBoard());
		}
	}

	@Override
	public void run() {
		timeElapsed++;
		gameBoard.updateScore(time, timeElapsed);
		
		for(Racer r : racers) {
			r.getRacer().setScoreboard(gameBoard.getBoard());
		}
	}
}
