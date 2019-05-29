package com.samleighton.xquiset.PigRacing.Objects.Scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class GameBoard {
	private ScoreboardManager manager;
	private Scoreboard gameBoard;
	
	public GameBoard() {
		setManager(Bukkit.getScoreboardManager());
		setGameBoard(getManager().getNewScoreboard());
	}

	/**
	 * @return the manager
	 */
	public ScoreboardManager getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(ScoreboardManager manager) {
		this.manager = manager;
	}

	/**
	 * @return the gameBoard
	 */
	public Scoreboard getBoard() {
		return gameBoard;
	}

	/**
	 * @param gameBoard the gameBoard to set
	 */
	public void setGameBoard(Scoreboard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	public void addScore(String name, String criteria, String displayName, int score) {
		Objective scoreObj = getBoard().registerNewObjective(name, criteria, displayName, RenderType.INTEGER);
		scoreObj.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score newScore = scoreObj.getScore(name);
		newScore.setScore(score);
	}
	
	public void updateScore(String scoreName, int score) {
		Objective scoreObj = getBoard().getObjective(DisplaySlot.SIDEBAR);
		if(scoreObj != null) {
			Score updateScore = scoreObj.getScore(scoreName);
			updateScore.setScore(score);
		}
	}
}
