package com.samleighton.xquiset.PigRacing.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.samleighton.xquiset.PigRacing.PigRacing;

public abstract class PigRacingCommands implements CommandExecutor{

	PigRacing plugin;
	
	public PigRacingCommands(PigRacing pl) {
		this.plugin = pl;
	}
	
	public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);
}
