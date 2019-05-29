package com.samleighton.xquiset.PigRacing;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.samleighton.xquiset.PigRacing.Commands.Spawns.SetLobbySpawn;
import com.samleighton.xquiset.PigRacing.Commands.Spawns.SetStartingPoint;
import com.samleighton.xquiset.PigRacing.EventListeners.PlayerJoin;
import com.samleighton.xquiset.PigRacing.Objects.Configurations.Checkpoints;
import com.samleighton.xquiset.PigRacing.Objects.Configurations.SpawnPoints;
import com.samleighton.xquiset.PigRacing.Objects.Game.Racer;
import com.samleighton.xquiset.PigRacing.Threads.Game;
import com.samleighton.xquiset.PigRacing.Threads.Lobby;

public class PigRacing extends JavaPlugin{
	
	private SpawnPoints spawns = new SpawnPoints(this);
	private Checkpoints checkpoints = new Checkpoints(this);
	public final int LOBBY_TIME = 30;
	public final int PLAYERS_TO_START_MATCH = 1;
	private PluginManager pm = getServer().getPluginManager();
	private BukkitTask lobbyThread;
	private BukkitTask gameThread;
	
	@Override
	public void onEnable() {
		GameState.setState(GameState.IN_LOBBY);
		registerCommands();
		registerListeners();
		lobbyStart();
	}
	
	@Override
	public void onDisable() {
		if(lobbyThread != null) {
			lobbyStop();
		}
		
		if(gameThread != null) {
			gameStop();
		}
	}
	
	public void lobbyStart() {
		spawns.reloadConfig();
		checkpoints.reloadConfig();
		spawns.save();
		checkpoints.save();
		
		lobbyThread = new Lobby(this, LOBBY_TIME).runTaskTimer(this, 10L, 20L);
	}
	
	public void lobbyStop() {
		lobbyThread.cancel();
	}
	
	public void gameStart(List<Racer> activeRacers) {
		if(GameState.getState() == GameState.RACING) {
			gameThread = new Game(this, activeRacers).runTaskTimer(this, 30L, 20L);
		}
	}
	
	public void gameStop() {
		gameThread.cancel();
	}
	
	public void registerCommands() {
		this.getCommand("setstartingpoint").setExecutor(new SetStartingPoint(this));
		this.getCommand("setlobbyspawn").setExecutor(new SetLobbySpawn(this));
	}
	
	public void registerListeners() {
		pm.registerEvents(new PlayerJoin(this), this);
	}
	
	public Location getLobbyLocation() {
		World w = Bukkit.getServer().getWorld(getSpawns().getConfig().getString("lobby.world"));
		double x = getSpawns().getConfig().getDouble("lobby.x");
		double y = getSpawns().getConfig().getDouble("lobby.y");
		double z = getSpawns().getConfig().getDouble("lobby.z");
		float pitch = Float.valueOf(getSpawns().getConfig().getString("lobby.pitch"));
		float yaw = Float.valueOf(getSpawns().getConfig().getString("lobby.yaw"));
		
		return new Location(w, x, y, z, pitch, yaw);
	}
	
	public SpawnPoints getSpawns() {
		spawns.reloadConfig();
		return spawns;
	}

	public Checkpoints getCheckpoints() {
		checkpoints.reloadConfig();
		return checkpoints;
	}
}
