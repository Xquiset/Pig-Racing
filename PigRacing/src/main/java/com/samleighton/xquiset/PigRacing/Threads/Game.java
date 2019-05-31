package com.samleighton.xquiset.PigRacing.Threads;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

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
	private Map<Racer, Stead> gameEntities = new HashMap<Racer, Stead>();
	private ItemStack pigWand = new ItemStack(Material.CARROT_ON_A_STICK, 1);
	private ItemMeta pigWandMeta = pigWand.getItemMeta();
	
	public Game(PigRacing pl) {
		this.plugin = pl;
		
		gameBoard.addScore(time, "racers", boardDisplay, timeElapsed);
		pigWandMeta.setDisplayName(ChatColor.DARK_RED + "Guiding Staff");
		pigWand.setItemMeta(pigWandMeta);
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			Racer r = new Racer(p.getUniqueId());
			if(plugin.getStartingLocation() != null) {
				gameBoard.addScore(r.getPlayer().getName(), "racers", boardDisplay, r.getLastCPTripped());
				r.getPlayer().setScoreboard(gameBoard.getBoard());
				
				r.getPlayer().teleport(plugin.getStartingLocation());
			
				r.getPlayer().getInventory().setItemInMainHand(pigWand);
				
				gameEntities.put(r, new Stead(r.getPlayer()));
			}
		}
	}

	@Override
	public void run() {
		timeElapsed++;
		gameBoard.updateScore(time, timeElapsed);
		
		if(GameState.winnerChosen()) {
			new BukkitRunnable() {
				@Override
				public void run() {
						
				}
			}.runTaskTimer(plugin, 0L, 20L);
		}
		
		for(Racer r : getGameEntities().keySet()) {
			gameBoard.updateScore(r.getPlayer().getName(), r.getLastCPTripped());
			r.getPlayer().setScoreboard(gameBoard.getBoard());
		}
	}
	
	public Map<Racer, Stead> getGameEntities(){
		return gameEntities;
	}
	
	public Racer getRacerByID(UUID uuid) {
		
		for(Racer r : getGameEntities().keySet()) {
			if(r.getRacerID().equals(uuid)) {
				return r;
			}
		}
		
		return null;
	}
	
	public boolean hasID(UUID uuid) {
		for(Racer r : getGameEntities().keySet()) {
			if(r.getRacerID().equals(uuid)) return true;
		}
		
		return false;
	}
	
	public void removePlayerFromGame(UUID uuid) {
		for(Racer r : getGameEntities().keySet()) {
			if(!r.getRacerID().equals(uuid)) break;
			getGameEntities().remove(r);
		}
	}
	
	public void cleanup() {
		GameState.setState(GameState.RESETTING);
		plugin.getServer().broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.ITALIC  + "RESETTING GAME.....");
		
		for(Racer r : getGameEntities().keySet()) {
			Player p = Bukkit.getServer().getPlayer(r.getRacerID());
			p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			p.getPlayer().getInventory().setContents(new ItemStack[0]);
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
