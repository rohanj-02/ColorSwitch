package main.menu;

import main.exceptions.GameDoesNotExistException;
import main.exceptions.SameNameException;
import main.exceptions.UserDoesNotExistException;
import main.logic.Game;
import main.logic.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static main.Constants.DATABASE_FILENAME;

// TODO Remove collected stars from listOfStars and similarly for switches
// TODO Check score not being serialised

public class MainMenu implements Serializable {

	private transient static MainMenu mainMenu = null;
	public static final long serialVersionUID = 1;
	protected String mode;
	protected ArrayList<Player> listOfPlayers;
	protected transient Player currentPlayer;

	public static MainMenu getInstance(){
		if(mainMenu == null){
			try{
				mainMenu = new MainMenu();
				deserialize();
				return mainMenu;
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				return new MainMenu();
			}
		}
		else{
			return mainMenu;
		}
	}

	public static void deserialize() throws IOException, ClassNotFoundException, NullPointerException {
		ObjectInputStream in;
		in = new ObjectInputStream(new FileInputStream(DATABASE_FILENAME));
		mainMenu.listOfPlayers = (ArrayList<Player>) in.readObject();
		for(Player player: mainMenu.listOfPlayers){
			System.out.println("Name: " + player.getName());
		}
		in.close();

	}

	public static void serialize() throws IOException {
		ObjectOutputStream out;
		out = new ObjectOutputStream(new FileOutputStream(DATABASE_FILENAME));
		out.writeObject(mainMenu.listOfPlayers);
	}

	private MainMenu() {
		// Implement serialisation here
		this.listOfPlayers = new ArrayList<>();
		this.currentPlayer = new Player("adminuser");
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

	public void setCurrentPlayer(String name) throws UserDoesNotExistException {
		for (Player p : this.listOfPlayers) {
			if (name.equals(p.getName())) {
				this.currentPlayer = p;
				System.out.println("Found a player match!");
				return;
			}
		}
		throw new UserDoesNotExistException("The user " + name + " is not in the database! Automatically creating a new player..");
	}

	public List<Game> showSavedGames(Player player) {
		// Returns the list of saved games of a user
		return this.currentPlayer.getSavedGames();
	}

	/**
	 * Creates a new player and adds it to the list of players
	 */
	public void createNewPlayer(String name) throws SameNameException {
		for (Player i : this.listOfPlayers) {
			if (i.getName().equals(name)) {
				throw new SameNameException("Player already exists! ");
			}
		}
		this.currentPlayer = new Player(name);
		this.listOfPlayers.add(currentPlayer);
	}

	public void setPlayer(Player player) {
		this.currentPlayer = player;
	}

	public List<Player> getPlayerList() {
		return this.listOfPlayers;
	}

	public void addGame(Game game) {
		this.currentPlayer.setCurrentGame(game);
	}

	public void addPlayerToGame(Game game) {
		game.setPlayer(this.currentPlayer);
	}

	public void addToSavedGames(Game game) {
		this.currentPlayer.addToSavedGames(game);
	}

	public int getNumberOfGames() {
		return this.currentPlayer.getSavedGames().size();
	}

	public Game getGame(int index) throws GameDoesNotExistException {
		return this.currentPlayer.setCurrentGame(index);
//		return this.currentPlayer.getCurrentGame();
	}
}