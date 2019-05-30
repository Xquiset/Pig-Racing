package com.samleighton.xquiset.PigRacing.Commands.Spawns;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
				ChatUtils.notPlayerError(sender);
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
		
		plugin.getSpawns().getConfig().set("start.world", playerLocal.getWorld().getName());
		plugin.getSpawns().getConfig().set("start.x", playerLocal.getX());
		plugin.getSpawns().getConfig().set("start.y", playerLocal.getY());
		plugin.getSpawns().getConfig().set("start.z", playerLocal.getZ());
		plugin.getSpawns().getConfig().set("start.pitch", playerLocal.getPitch());
		plugin.getSpawns().getConfig().set("start.yaw", playerLocal.getYaw());
		
		plugin.getSpawns().save();
	}
}
