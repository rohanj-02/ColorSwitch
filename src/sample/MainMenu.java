package sample;

import java.util.*;

public class MainMenu {
	private Player player;

	public MainMenu() {

	}

	public Game newGame() {
		// Main menu screen starts a new game
		// initialise player
		return new Classic();
	}

	public Game loadGame() {
		// Main menu screen shows a list of saved games and loads them
		// initialise player
		// will call showSavedGames -> javafx gui render-> select and give loaded game
		return new Classic();
	}

	public List<Game> showSavedGames(Player player) {
		// Returns the list of saved games of a user
		return new ArrayList<Game>();
	}

	// public void exitGame(Player player) {
	// // Serializes the game if any and just exit the game
	// return;
	// }

}