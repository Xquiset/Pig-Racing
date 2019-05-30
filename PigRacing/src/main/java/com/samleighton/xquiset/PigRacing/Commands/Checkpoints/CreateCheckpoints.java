package com.samleighton.xquiset.PigRacing.Commands.Checkpoints;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.samleighton.xquiset.PigRacing.PigRacing;
import com.samleighton.xquiset.PigRacing.Commands.PigRacingCommands;
import com.samleighton.xquiset.PigRacing.Objects.Game.CheckpointMarker;
import com.samleighton.xquiset.PigRacing.Utilities.ChatUtils;

public class CreateCheckpoints extends PigRacingCommands{
	
	private Player p;
	
	public CreateCheckpoints(PigRacing pl) {
		super(pl);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			ChatUtils.notPlayerError(sender);
			return false;
		}
		
		p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("createcheckpoints")) {
			
			p.getInventory().addItem(new CheckpointMarker().getMarkerItem());
			
			ChatUtils.sendSuccess(p, "Connect this item to tripwire hooks to set it as a checkpoint tripper!");
			
			return true;
		}
		
		return false;
	}

}
