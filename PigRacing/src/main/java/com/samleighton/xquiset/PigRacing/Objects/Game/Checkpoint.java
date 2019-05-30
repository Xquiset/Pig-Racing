package com.samleighton.xquiset.PigRacing.Objects.Game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;


public class Checkpoint {
	
	private List<Location> locations = new ArrayList<Location>();
	private int position;
	
	public Checkpoint(List<Location> checkpointLocations, int position) {
		this.locations = checkpointLocations;
		this.position = position;
	}
	
	public void setLocations(List<Location> checkpointLocations) {
		this.locations = checkpointLocations;
	}
	
	public List<Location> getLocations(){
		return locations;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int pos) {
		this.position = pos;
	}
}
