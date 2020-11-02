package sample;

import java.util.ArrayList;

public abstract class MainMenu {

	protected String mode;
	ArrayList<Player> listOfPlayers;
	Player currentPlayer;

	public void setMode(String s) {
		mode = s;
	}

	public abstract Game newGame();
	// Main menu screen starts a new game
	// initialise player

//	another implementation
//	abstract class obstacle
//	interface motion
//	see implementation of javafx snake game
//	dynamic gui -> rotation
//	motion -> scrollable and rotational
//	ball composition in game list of game
//	ball implements motion
//	collectables switch and stars
//	player to store score
//	player has a list of games
//	game and player serializable
//	gameplay and game class

}