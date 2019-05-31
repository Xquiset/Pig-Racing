package com.samleighton.xquiset.PigRacing.EventListeners.InGame;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;

import com.samleighton.xquiset.PigRacing.GameState;
import com.samleighton.xquiset.PigRacing.PigRacing;
import com.samleighton.xquiset.PigRacing.Objects.Game.Checkpoint;
import com.samleighton.xquiset.PigRacing.Objects.Game.Racer;
import com.samleighton.xquiset.PigRacing.Utilities.ChatUtils;

public class CheckpointTripped implements Listener{
	
	private PigRacing plugin;
	
	public CheckpointTripped(PigRacing pl) {
		this.plugin = pl;
	}
	
	@EventHandler
	public void onCheckPointTrip(EntityInteractEvent e) {
		Player rider = null;
		
		//Getting the rider of the pig
		if(e.getEntityType() == EntityType.PIG) {
			Pig pig = (Pig) e.getEntity();
			if(pig.getPassengers() != null) {
				if(pig.getPassengers().size() == 1) {
					for(Entity passenger : pig.getPassengers()) {
						if(passenger instanceof Player) {
							rider = (Player) passenger;
						}
					}
				}
			}
		}
		
		if(rider != null) {
			if(plugin.getGame().getRacerByID(rider.getUniqueId()) != null) {
				Racer r = plugin.getGame().getRacerByID(rider.getUniqueId());
				Location trigger = e.getBlock().getLocation();
				World tw = trigger.getWorld();
				double tx = trigger.getX();
				double ty = trigger.getY();
				double tz = trigger.getZ();
				for(Checkpoint cp : plugin.getCheckpointsFromCFG()) {
					boolean tripped = false;
					for(Location cpLocal : cp.getLocations()) {
						if(cpLocal.getBlockX() == tx && cpLocal.getBlockY() == ty && cpLocal.getBlockZ() == tz && cpLocal.getWorld() == tw) {
							tripped = true;
						}
					}
					
					if(cp.getPosition() > r.getLastCPTripped() && tripped == true) {
						if(cp.getPosition() == plugin.getCheckpointsFromCFG().size() && !GameState.winnerChosen()) {
							GameState.setWinnerChosen(true);
						}
						
						r.setLastCPTripped(cp.getPosition());
						ChatUtils.sendInfo(r.getPlayer(), " You just crossed checkpoint " + r.getLastCPTripped() + "/" + plugin.getCheckpointsFromCFG().size());
					}
				}
			}
		}
	}
}
