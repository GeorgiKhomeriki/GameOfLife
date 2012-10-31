package main;

import engine.Game;
import game.GameState;

public class Main {

	public static void main(String[] args) {
		Game.get().addState(new GameState());
		Game.get().setCurrentState(GameState.STATE_NAME);
		Game.get().start();
	}

}
