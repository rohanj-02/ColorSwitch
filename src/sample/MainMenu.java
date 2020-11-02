package sample;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {

	protected String mode;
	ArrayList<Player> listOfPlayers;
	Player currentPlayer;

	public void setMode(String s) {
		mode = s;
	}

	public Game newGame(){
		return new Classic();
	}
	// Main menu screen starts a new game
	// initialise player

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