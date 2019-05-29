package com.samleighton.xquiset.PigRacing.Commands.Spawns;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.samleighton.xquiset.PigRacing.PigRacing;
import com.samleighton.xquiset.PigRacing.Commands.PigRacingCommands;
import com.samleighton.xquiset.PigRacing.Utilities.ChatUtils;

public class SetStartingPoint extends PigRacingCommands{

	private PigRacing plugin;
	
	public SetStartingPoint(PigRacing pl) {
		super(pl);
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("setstartingpoint")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.DARK_RED + "This Command is for players only!");
				return false;
			}
			
			//Player sending command
			Player p = (Player) sender;
			
			setStartingSpawn(p);
			
			ChatUtils.sendSuccess(p, "Saved Starting Point successfully!");
			return true;
			
		}
		
		return false;
	}
	
	
	
	/**
	 * @param p The player whose location to grab and set the race start at
	 */
	public void setStartingSpawn(Player p) {

		Location playerLocal = p.getLocation();
		FileConfiguration spawns = plugin.getSpawns().getConfig();
		
		spawns.set("startingPlace.world", playerLocal.getWorld().getName());
		spawns.set("startingPlace.x", playerLocal.getX());
		spawns.set("startingPlace.y", playerLocal.getY());
		spawns.set("startingPlace.z", playerLocal.getZ());
		spawns.set("startingPlace.pitch", playerLocal.getPitch());
		spawns.set("startingPlace.yaw", playerLocal.getYaw());
		
		plugin.getSpawns().save();
	}

}
