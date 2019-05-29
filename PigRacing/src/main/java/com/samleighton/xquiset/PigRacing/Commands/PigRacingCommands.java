package com.samleighton.xquiset.PigRacing.Commands;

import org.bukkit.command.CommandExecutor;

import com.samleighton.xquiset.PigRacing.PigRacing;

public abstract class PigRacingCommands implements CommandExecutor{

	PigRacing plugin;
	
	public PigRacingCommands(PigRacing pl) {
		this.plugin = pl;
	}
}
