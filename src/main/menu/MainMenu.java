package main.menu;

import main.exceptions.UserDoesNotExist;
import main.logic.Game;
import main.logic.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MainMenu implements Serializable {

	protected String mode;
	protected ArrayList<Player> listOfPlayers;
	protected transient Player currentPlayer;

	public MainMenu() {
		// Implement serialisation here
		this.listOfPlayers = new ArrayList<>();
		this.currentPlayer = new Player("user");
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String s) {
		mode = s;
	}

	public ArrayList<Player> getListOfPlayers() {
		return listOfPlayers;
	}

	public void setListOfPlayers(ArrayList<Player> listOfPlayers) {
		this.listOfPlayers = listOfPlayers;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	// Main menu screen starts a new game
	// initialise player

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}


	public List<Game> showSavedGames(Player player) {
		// Returns the list of saved games of a user
		return this.currentPlayer.getSavedGames();
	}

	/**
	*	Creates a new player and adds it to the list of players
	*/
	public void createNewPlayer(String name) {
		this.currentPlayer = new Player(name);
		this.listOfPlayers.add(currentPlayer);
	}

	public void setPlayer(Player player) {
		this.currentPlayer = player;
	}

	public void setCurrentPlayer(String name) throws UserDoesNotExist {
		for (Player p : this.listOfPlayers) {
			if (name.equals(p.getName())){
				this.currentPlayer = p;
				System.out.println("Found a player match!");
				return;
			}
		}
		throw new UserDoesNotExist("The user " + name + " is not in the database! Automatically creating a new player..");
	}

	public List<Player> getPlayerList() {
		return this.listOfPlayers;
	}

	public void addGame(Game game) {
		System.out.println(game);
		System.out.println(this.currentPlayer);
		this.currentPlayer.setCurrentGame(game);
	}

	public void addToSavedGames(Game game) {
		this.currentPlayer.addToSavedGames(game);
	}

}