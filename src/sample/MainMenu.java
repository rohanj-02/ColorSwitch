package sample;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.io.IOException;
import java.lang.ClassNotFoundException;


public class MainMenu implements Serializable{

	protected String mode;
	protected ArrayList<Player> listOfPlayers;
	protected transient Player currentPlayer;

	public void setMode(String s) {
		mode = s;
	}

	public Game newGame(){
		//if else block to create a new Game object according to mode
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
		return this.currentPlayer.savedGames;
	}

	/*
		Creates a new player and adds it to the list of players
	*/
	public void createNewPlayer(){

	}

	public void setPlayer(Player player){
		this.currentPlayer = player;	
	}

	public List<Player> getPlayerList(){
		return this.listOfPlayers;
	}

	public static void serialize() throws IOException{

    }
    
    public static void deserialize() throws IOException, ClassNotFoundException{

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