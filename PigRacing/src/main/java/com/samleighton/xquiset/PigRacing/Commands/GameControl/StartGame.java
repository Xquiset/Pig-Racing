package com.samleighton.xquiset.PigRacing.Commands.GameControl;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.samleighton.xquiset.PigRacing.GameState;
import com.samleighton.xquiset.PigRacing.PigRacing;
import com.samleighton.xquiset.PigRacing.Commands.PigRacingCommands;
import com.samleighton.xquiset.PigRacing.Utilities.ChatUtils;

public class StartGame extends PigRacingCommands{

	private PigRacing pl;
	
	public StartGame(PigRacing pl) {
		super(pl);
		this.pl = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("startrace")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.DARK_RED + "This command is for players only!");
			}
			
			Player p = (Player) sender;
			
			if(GameState.getState() != GameState.IN_LOBBY) {
				ChatUtils.sendError(p, "You must be in the lobby to perfom this command!");
			} else {
				GameState.setState(GameState.RACING);
				pl.lobbyStop();
				pl.gameStart();
			}
			
			return true;
		}
		
		return false;
	}

}
