package com.samleighton.xquiset.PigRacing.Objects.Game;

import org.bukkit.entity.Player;

public class Racer {

	private Player racer;
	private int checkPointsTripped;
	
	public Racer(Player player) {
		this.racer = player;
		this.checkPointsTripped = 0;
	}
	
	public Player getRacer() {
		return racer;
	}
	
	public void setCheckpointsTripped(int checkPointsPlayerTripped){
		this.checkPointsTripped = checkPointsPlayerTripped;
	}
	
	public int getCheckpointsTripped() {
		return checkPointsTripped;
	}
}
