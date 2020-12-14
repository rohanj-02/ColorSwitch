package main.logic;

import main.exceptions.GameDoesNotExistException;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
	public static final long serialVersionUID = 2;
	private final String name;
	private int highScore;
	private ArrayList<Game> savedGames;
	private int numberOfStars = 0;
	// save the stats of the player after every game
	private transient Game currentGame;

	public Player(String name) {
		this.savedGames = new ArrayList<>(5);
		this.name = name;
	}

	public Game getCurrentGame() {
		return currentGame;
	}

	public void setCurrentGame(Game currentGame) {
		this.currentGame = currentGame;
	}

	public String getName() {
		return name;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	public ArrayList<Game> getSavedGames() {
		return savedGames;
	}

	public void setSavedGames(ArrayList<Game> savedGame) {
		this.savedGames = savedGame;
	}

	public int getNumberOfStars() {
		return numberOfStars;
	}

	public void setNumberOfStars(int numberOfStars) {
		this.numberOfStars = numberOfStars;
	}

	public void addToSavedGames(Game currentGame) {
		this.savedGames.add(currentGame);
	}

	public void createNewGame(String mode) {

	}

	/**
	 * Sets the currentGame to the index pointed in the list of savedGames and returns the game object
	 *
	 * @param index the index of savedGames
	 * @return Returns the game at index index
	 * @throws GameDoesNotExistException on not finding the game
	 */
	public Game setCurrentGame(int index) throws GameDoesNotExistException {
		if (index > this.savedGames.size() || index < 0) {
			throw new GameDoesNotExistException("Index out of bounds");
		}
		this.setCurrentGame(this.savedGames.get(index - 1));
		this.savedGames.remove(this.currentGame);
		return this.currentGame;
	}
}
