package com.samleighton.xquiset.PigRacing.EventListeners.GameSetup;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.samleighton.xquiset.PigRacing.PigRacing;
import com.samleighton.xquiset.PigRacing.Commands.PigRacingCommands;
import com.samleighton.xquiset.PigRacing.Utilities.ChatUtils;

public class CheckpointPlaceOrBreakEvent extends PigRacingCommands implements Listener {
	
	private PigRacing plugin;
	private ItemStack itemInHand;
	private ItemMeta itemInHandMeta;
	private ArrayList<Location> cpLocals = new ArrayList<Location>();
	public static boolean saved = false;
	
	public CheckpointPlaceOrBreakEvent(PigRacing pl) {
		super(pl);
		this.plugin = pl;
	}
	
	@EventHandler
	public void onCheckpointMarkerPlace(BlockPlaceEvent e) {
		itemInHand = e.getPlayer().getInventory().getItemInMainHand();
		itemInHandMeta = itemInHand.getItemMeta();
		
		if(itemInHandMeta.getLocalizedName().equalsIgnoreCase("cpwire")) {
			cpLocals.add(e.getBlock().getLocation());
			ChatUtils.sendInfo(e.getPlayer(), "You just placed a checkpoint tripwire!");
		}
	}
	
	@EventHandler
	public void onCheckpointMarkerBreak(BlockBreakEvent e) {
		Location blockLocal = e.getBlock().getLocation();
		if(cpLocals.contains(blockLocal)) {
			cpLocals.remove(blockLocal);
			ChatUtils.sendInfo(e.getPlayer(), "You just broke a checkpoint tripwire!");
		}
	}
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.DARK_RED + "This command is for players only!");
			return false;
		}
		
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("savecp")) {
			if(args.length != 1) {
				ChatUtils.sendError(p, "Incorrect number of arguments! Remeber to specify a checkpoint position i.e. 1 or 2");
				return true;
			}
			
			try {
				Integer.valueOf(args[0]);
			} catch(NumberFormatException e) {
				ChatUtils.sendError(p, "The checkpoint position must be a number!");
				return true;
			}
			
			if(!(cpLocals.size() > 0)) {
				ChatUtils.sendError(p, "You have not placed any checkpoint markers down to create a checkpoint yet!");
				return true;
			}
			
			FileConfiguration cpConfig = plugin.getCheckpoints().getConfig();
			
			if(cpConfig.contains("checkPoints") && cpConfig.getConfigurationSection("checkPoints").getKeys(false).contains(args[0])) {
				cpConfig.getConfigurationSection("checkPoints").set(args[0], null);
			}
			
			for(Location l : cpLocals) {
				String w = l.getWorld().getName();
				double x = l.getX();
				double y = l.getY();
				double z = l.getZ();
				float pitch = l.getPitch();
				float yaw = l.getYaw();
				
				String path = "checkPoints." + String.valueOf(args[0]) + "." + String.valueOf(cpLocals.indexOf(l)) + ".";
				
				cpConfig.set(path + "world", w);
				cpConfig.set(path + "x", x);
				cpConfig.set(path + "y", y);
				cpConfig.set(path + "z", z);
				cpConfig.set(path + "pitch", pitch);
				cpConfig.set(path + "yaw", yaw);
			}
			
			plugin.getCheckpoints().save();
			cpLocals = new ArrayList<Location>();
			ChatUtils.sendSuccess(p, "Checkpoint " + args[0] + " has been saved!");
			return true;
		}
		
		return false;
	}
	
	
}
