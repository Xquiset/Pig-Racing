package com.samleighton.xquiset.PigRacing;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.samleighton.xquiset.PigRacing.Commands.Checkpoints.CreateCheckpoints;
import com.samleighton.xquiset.PigRacing.Commands.GameControl.EndGame;
import com.samleighton.xquiset.PigRacing.Commands.GameControl.StartGame;
import com.samleighton.xquiset.PigRacing.Commands.Spawns.SetLobbySpawn;
import com.samleighton.xquiset.PigRacing.Commands.Spawns.SetStartingPoint;
import com.samleighton.xquiset.PigRacing.EventListeners.PlayerJoin;
import com.samleighton.xquiset.PigRacing.EventListeners.GameSetup.CheckpointPlaceOrBreakEvent;
import com.samleighton.xquiset.PigRacing.EventListeners.InGame.CheckpointTripped;
import com.samleighton.xquiset.PigRacing.EventListeners.InGame.PlayerQuitGame;
import com.samleighton.xquiset.PigRacing.Objects.Configurations.Checkpoints;
import com.samleighton.xquiset.PigRacing.Objects.Configurations.SpawnPoints;
import com.samleighton.xquiset.PigRacing.Objects.Game.Checkpoint;
import com.samleighton.xquiset.PigRacing.Threads.Game;
import com.samleighton.xquiset.PigRacing.Threads.Lobby;

public class PigRacing extends JavaPlugin{
	
	private SpawnPoints spawns = new SpawnPoints(this);
	private Checkpoints checkpoints = new Checkpoints(this);
	private CheckpointPlaceOrBreakEvent cpCmdAndEvent = new CheckpointPlaceOrBreakEvent(this);
	private List<Checkpoint> unsavedCheckpoints = new ArrayList<Checkpoint>();
	private PluginManager pm = getServer().getPluginManager();
	private BukkitTask lobbyThread;
	private BukkitTask gameThread;
	private Game game;
	private Lobby lobby;
	public final int LOBBY_TIME = 60;
	public final int PLAYERS_TO_START_MATCH = 2;
	
	@Override
	public void onEnable() {
		GameState.setState(GameState.IN_LOBBY);
		registerCommands();
		registerListeners();
		getSpawns().reloadConfig();
		getCheckpoints().reloadConfig();
		lobbyStart();
	}
	
	@Override
	public void onDisable() {
		if(GameState.getState() == GameState.RACING) {
			gameStop();
		}
	}
	
	public void registerCommands() {
		this.getCommand("setstartingpoint").setExecutor(new SetStartingPoint(this));
		this.getCommand("setlobbyspawn").setExecutor(new SetLobbySpawn(this));
		this.getCommand("startrace").setExecutor(new StartGame(this));
		this.getCommand("createcheckpoints").setExecutor(new CreateCheckpoints(this));
		this.getCommand("endrace").setExecutor(new EndGame(this));
		this.getCommand("savecp").setExecutor(cpCmdAndEvent);
	}
	
	public void registerListeners() {
		pm.registerEvents(new PlayerJoin(this), this);
		pm.registerEvents(new PlayerQuitGame(this), this);
		pm.registerEvents(cpCmdAndEvent, this);
		pm.registerEvents(new CheckpointTripped(this), this);
	}
	
	public void lobbyStart() {
		lobby = new Lobby(this);
		lobbyThread = getLobby().runTaskTimer(this, 10L, 20L);
	}
	
	public void lobbyStop() {
		lobbyThread.cancel();
		lobby = null;
		lobbyThread = null;
	}
	
	public void gameStart() {
		game = new Game(this);
		gameThread = game.runTaskTimer(this, 10L, 20L);
	}
	
	public void gameStop() {
		getGame().cleanup();
		gameThread.cancel();
		game = null;
		gameThread = null;
	}
	
	public Lobby getLobby() {
		return lobby;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Location getLobbyLocation() {
		if(getSpawns().getConfig().contains("lobby")) {
			World w = Bukkit.getServer().getWorld(getSpawns().getConfig().getString("lobby.world"));
			double x = getSpawns().getConfig().getDouble("lobby.x");
			double y = getSpawns().getConfig().getDouble("lobby.y");
			double z = getSpawns().getConfig().getDouble("lobby.z");
			float pitch = Float.parseFloat(getSpawns().getConfig().getString("lobby.pitch"));
			float yaw = Float.parseFloat(getSpawns().getConfig().getString("lobby.yaw"));
			
			return new Location(w,x,y,z,yaw,pitch);
		}
		return null;
	}
	
	public Location getStartingLocation() {
		if(getSpawns().getConfig().contains("start")) {
			World w = Bukkit.getServer().getWorld(getSpawns().getConfig().getString("start.world"));
			double x = getSpawns().getConfig().getDouble("start.x");
			double y = getSpawns().getConfig().getDouble("start.y");
			double z = getSpawns().getConfig().getDouble("start.z");
			float pitch = Float.parseFloat(getSpawns().getConfig().getString("start.pitch"));
			float yaw = Float.parseFloat(getSpawns().getConfig().getString("start.yaw"));
			
			return new Location(w,x,y,z,yaw,pitch);
		}
		return null;
	}
	
	public List<Checkpoint> getCheckpointsFromCFG(){
		//The checkpoints file configuration
		FileConfiguration c = getCheckpoints().getConfig();
		
		if(c.contains("checkPoints")) {
			List<Checkpoint> cps = new ArrayList<Checkpoint>();
			for(String position : c.getConfigurationSection("checkPoints").getKeys(false)) {
				List<Location> cpLocals = new ArrayList<Location>();
				for(String markerIndex : c.getConfigurationSection("checkPoints." + position).getKeys(false)) {
					String path = "checkPoints." + position + "." + markerIndex + ".";
					World w = Bukkit.getServer().getWorld(c.getString(path + "world"));
					double x = c.getDouble(path + "x");
					double y = c.getDouble(path + "y");
					double z = c.getDouble(path + "z");
					float pitch = Float.parseFloat(c.getString(path + "pitch"));
					float yaw = Float.parseFloat(c.getString(path + "yaw"));
					
					Location cpLocal = new Location(w,x,y,z,yaw,pitch);
					cpLocals.add(cpLocal);
				}
				cps.add(new Checkpoint(cpLocals, Integer.valueOf(position)));
			}
			
			return cps;
		}
		
		return null;
	}
	
	public SpawnPoints getSpawns() {
		return spawns;
	}

	public Checkpoints getCheckpoints() {
		return checkpoints;
	}
	
	public List<Checkpoint> getUnsavedCheckpoints() {
		return unsavedCheckpoints;
	}
}
