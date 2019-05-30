package com.samleighton.xquiset.PigRacing.Objects.Game;

import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;

public class Stead {
	
	private Pig pig;
	private Player rider;
	
	public Stead(Player p) {
		this.rider = p;
		pig = p.getWorld().spawn(p.getLocation(), Pig.class);
		pig.setSaddle(true);
		pig.addPassenger(rider);
	}
	
	public Pig getStead() {
		return pig;
	}
	
	public Player getRider() {
		return (Player)getStead().getPassengers().get(0);
	}
	
	public void setRider(Player p) {
		getStead().addPassenger(p);
	}
	
}
