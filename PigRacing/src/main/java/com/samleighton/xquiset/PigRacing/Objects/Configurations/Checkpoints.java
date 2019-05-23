package com.samleighton.xquiset.PigRacing.Objects.Configurations;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.samleighton.xquiset.PigRacing.PigRacing;
import com.samleighton.xquiset.PigRacing.Objects.Config;

public class Checkpoints extends Config{
	
	private PigRacing pl;
	private FileConfiguration checkpointConfig = null;
	private File checkpointFile = null;
	
	public Checkpoints(PigRacing pl) {
		super(pl);
		this.pl = pl;
	}

	@Override
	public FileConfiguration getConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reloadConfig() {
		if (checkpointFile == null) {
			checkpointFile = new File(pl.getDataFolder(), "checkpoints.yml");
	    }
		
	    checkpointConfig = YamlConfiguration.loadConfiguration(checkpointFile);

	    // Look for defaults in the jar
	    Reader defConfigStream;
		try {
			defConfigStream = new InputStreamReader(pl.getResource("checkpoints.yml"), "UTF8");
			if (defConfigStream != null) {
		        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
		        checkpointConfig.setDefaults(defConfig);
		    }
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void save() {
		if(checkpointConfig == null || checkpointFile == null) {
			return;
		}
		
		try {
			getConfig().save(checkpointFile);
		} catch (IOException e) {
			pl.getLogger().log(Level.SEVERE, ChatColor.DARK_RED + "Could not save config!", e);
		}
		
	}
}