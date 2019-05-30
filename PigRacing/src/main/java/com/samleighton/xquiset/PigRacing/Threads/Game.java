package com.samleighton.xquiset.PigRacing.Threads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.samleighton.xquiset.PigRacing.GameState;
import com.samleighton.xquiset.PigRacing.PigRacing;
import com.samleighton.xquiset.PigRacing.Objects.Game.Racer;
import com.samleighton.xquiset.PigRacing.Objects.Game.Stead;
import com.samleighton.xquiset.PigRacing.Objects.Scoreboards.GameBoard;

public class Game extends BukkitRunnable{
	
	private PigRacing plugin;
	private GameBoard gameBoard = new GameBoard();
	private int timeElapsed = 0;
	private String boardDisplay = ChatColor.BOLD + "" +ChatColor.ITALIC + "Racers";
	private String time = ChatColor.BLUE + "Time:";
	private List<Racer> racers = new ArrayList<Racer>();
	private Map<Racer, Stead> gameEntities= new HashMap<Racer, Stead>();
	private ItemStack pigWand = new ItemStack(Material.CARROT_ON_A_STICK, 1);
	private ItemMeta pigWandMeta = pigWand.getItemMeta();
	
	public Game(PigRacing pl, List<Racer> racers) {
		this.plugin = pl;
		this.racers = racers;
		
		gameBoard.addScore(time, "racers", boardDisplay, timeElapsed);
		pigWandMeta.setDisplayName(ChatColor.DARK_RED + "Guiding Staff");
		pigWand.setItemMeta(pigWandMeta);
		
		for(Racer r : racers) {
			if(plugin.getStartingLocation() != null) {
				gameBoard.addScore(r.getRacer().getName(), "racers", boardDisplay, r.getCheckpointsTripped());
				r.getRacer().setScoreboard(gameBoard.getBoard());
				
				r.getRacer().teleport(plugin.getStartingLocation());
			
				r.getRacer().getInventory().setItemInMainHand(pigWand);
				
				gameEntities.put(r, new Stead(r.getRacer()));
			}
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
	
	public Map<Racer, Stead> getGameEntities(){
		return gameEntities;
	}
	
	public List<Racer> getRacers(){
		return racers;
	}
	
	public List<Player> getRacersAsPlayers(){
		List<Player> asPlayers = new ArrayList<Player>();
		
		for(Racer r : getRacers()) {
			asPlayers.add(r.getRacer());
		}
		
		return asPlayers;
	}
	
	public void removePlayerFromGame(Player p) {
		if(getRacersAsPlayers().contains(p)) {
			racers.remove(getRacersAsPlayers().indexOf(p));
			Racer r = racers.get(getRacersAsPlayers().indexOf(p));
			gameEntities.remove(r);
		}
	}
	
	public void cleanup() {
		GameState.setState(GameState.RESETTING);
		plugin.getServer().broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.ITALIC  + "RESETTING GAME.....");
		
		for(Racer r : getGameEntities().keySet()) {
			r.getRacer().getInventory().setContents(new ItemStack[0]);
			getGameEntities().get(r).getStead().remove();
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.cancel();
		
		Bukkit.getServer().reload();
		
	}
}
