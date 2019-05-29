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

public class SpawnPoints extends Config{
	
	private PigRacing pl;
	private FileConfiguration spawnPointConfig = null;
	private File spawnPointFile = null;
	
	public SpawnPoints(PigRacing pl) {
		super(pl);
		this.pl = pl;
	}

	@Override
	public FileConfiguration getConfig() {
		if(spawnPointConfig == null) {
			reloadConfig();
		}
		return spawnPointConfig;
	}

	@Override
	public void reloadConfig() {
		if (spawnPointFile == null) {
			spawnPointFile = new File(pl.getDataFolder(), "spawnpoints.yml");
	    }
		
		spawnPointConfig = YamlConfiguration.loadConfiguration(spawnPointFile);

	    // Look for defaults in the jar
	    Reader defConfigStream;
		try {
			defConfigStream = new InputStreamReader(pl.getResource("spawnpoints.yml"), "UTF8");
			if (defConfigStream != null) {
		        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
		        spawnPointConfig.setDefaults(defConfig);
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save() {
		if(spawnPointConfig == null || spawnPointFile == null) {
			return;
		}
		
		try {
			getConfig().save(spawnPointFile);
		} catch (IOException e) {
			pl.getLogger().log(Level.SEVERE, ChatColor.DARK_RED + "Could not save config!", e);
		}
	}
}
