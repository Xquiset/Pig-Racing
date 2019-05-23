package com.samleighton.xquiset.PigRacing;

public enum GameState {
	IN_LOBBY(true), RACING(false), FINISHED(false), RESETTING(false);
	
	private boolean joinable;
	
	private static GameState currentState;
	
	GameState(boolean joinable) {
		this.joinable = joinable;
	}
	
	public boolean isJoinable() {
		return this.joinable;
	}
	
	public static boolean isState(GameState state) {
		return GameState.currentState == state;
	}
	
	public static void setState(GameState state) {
		currentState = state;
	}
	
	public static GameState getState() {
		return currentState;
	}
}
