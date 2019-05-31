package com.samleighton.xquiset.PigRacing.Objects.Game;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Racer {

	private UUID racer;
	private int lastCpTripped;
	
	public Racer(UUID uuid) {
		this.racer = uuid;
		this.lastCpTripped = 0;
	}
	
	public UUID getRacerID() {
		return racer;
	}
	
	public Racer getRacer() {
		return this;
	}
	
	public Player getPlayer() {
		return Bukkit.getServer().getPlayer(getRacerID());
	}
	
	public void setLastCPTripped(int checkPointsPlayerTripped){
		this.lastCpTripped = checkPointsPlayerTripped;
	}
	
	public int getLastCPTripped() {
		return lastCpTripped;
	}
}
