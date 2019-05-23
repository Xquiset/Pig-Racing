package com.samleighton.xquiset.PigRacing.Objects;

import org.bukkit.configuration.file.FileConfiguration;

import com.samleighton.xquiset.PigRacing.PigRacing;

public abstract class Config {
	
	PigRacing pl;
	
	public Config(PigRacing pl) {
		this.pl = pl;
	}
	
	public abstract void reloadConfig();
	
	public abstract FileConfiguration getConfig();
	
	public abstract void save();

}
