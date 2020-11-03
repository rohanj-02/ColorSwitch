package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.ClassNotFoundException;

public class Player implements Serializable{
	private String name;
	private int highScore;
	private ArrayList<Game> savedGames;
	private int numberOfStars;
	// private Game currentGame;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	public ArrayList<Game> getSavedGame() {
		return savedGames;
	}

	public void setSavedGame(ArrayList<Game> savedGame) {
		this.savedGames = savedGame;
	}

	public int getNumberOfStars() {
		return numberOfStars;
	}

	public void setNumberOfStars(int numberOfStars) {
		this.numberOfStars = numberOfStars;
	}

	public void addToSavedGames(Game currentGame){
		this.savedGames.add(currentGame);		
	}

    public static void serialize() throws IOException{

	}
    
    public static void deserialize() throws IOException, ClassNotFoundException{

	}

}
