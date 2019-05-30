package com.samleighton.xquiset.PigRacing.Commands.GameControl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.samleighton.xquiset.PigRacing.GameState;
import com.samleighton.xquiset.PigRacing.PigRacing;
import com.samleighton.xquiset.PigRacing.Commands.PigRacingCommands;
import com.samleighton.xquiset.PigRacing.Utilities.ChatUtils;

public class EndGame extends PigRacingCommands{

	private PigRacing plugin;
	
	public EndGame(PigRacing pl) {
		super(pl);
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			ChatUtils.notPlayerError(sender);
			return false;
		}
		
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("endrace")) {
			if(GameState.getState() != GameState.RACING){
				ChatUtils.sendError(p, "The game is not in session. Try again when the game is playing");
				return true;
			} else {
				plugin.gameStop();
				ChatUtils.sendSuccess(p, "You have sucessfully stopped the game!");
				return true;
			}
		}
		
		return false;
	}

}
