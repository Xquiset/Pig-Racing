package com.samleighton.xquiset.PigRacing.Objects.Game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CheckpointMarker {
	
	private ItemStack checkPointWire = new ItemStack(Material.STRING, 1);
	private ItemMeta checkPointMeta = checkPointWire.getItemMeta();
	private List<String> checkPointLore = new ArrayList<String>();
	
	public CheckpointMarker() {
		checkPointLore.add(ChatColor.DARK_RED + "Use this tripwire to set your checkpoints");
		checkPointMeta.setLore(checkPointLore);
		checkPointMeta.setDisplayName(ChatColor.BLUE + "Checkpoint Wire");
		checkPointMeta.setLocalizedName("cpwire");
		
		checkPointWire.setItemMeta(checkPointMeta);
	}
	
	public ItemMeta getMeta() {
		return checkPointMeta;
	}
	
	public ItemStack getMarkerItem() {
		return checkPointWire;
	}
	
	public List<String> getLore(){
		return checkPointLore;
	}

}
