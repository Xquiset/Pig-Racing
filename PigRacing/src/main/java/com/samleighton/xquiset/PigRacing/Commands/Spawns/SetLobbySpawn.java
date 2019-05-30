package com.samleighton.xquiset.PigRacing.Commands.Spawns;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.samleighton.xquiset.PigRacing.PigRacing;
import com.samleighton.xquiset.PigRacing.Commands.PigRacingCommands;
import com.samleighton.xquiset.PigRacing.Utilities.ChatUtils;

public class SetLobbySpawn extends PigRacingCommands{
	
	private PigRacing plugin;
	
	public SetLobbySpawn(PigRacing pl) {
		super(pl);
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("setlobbyspawn")) {
			if(!(sender instanceof Player)) {
				ChatUtils.notPlayerError(sender);
				return false;
			}
			
			//Player sending command
			Player p = (Player) sender;
			
			setLobbySpawn(p);
			
			ChatUtils.sendSuccess(p, "Saved Lobby Spawn successfully!");
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * @param p The player whose location to grab and set the lobby spawn at
	 */
	public void setLobbySpawn(Player p) {

		Location playerLocal = p.getLocation();
		
		FileConfiguration spawns = plugin.getSpawns().getConfig();
		
		spawns.set("lobby.world", playerLocal.getWorld().getName());
		spawns.set("lobby.x", playerLocal.getX());
		spawns.set("lobby.y", playerLocal.getY());
		spawns.set("lobby.z", playerLocal.getZ());
		spawns.set("lobby.pitch", playerLocal.getPitch());
		spawns.set("lobby.yaw", playerLocal.getYaw());
		
		plugin.getSpawns().save();
	}
}
