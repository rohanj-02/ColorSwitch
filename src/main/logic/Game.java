package main.logic;

import main.gui.ColourSwitchBall;
import main.gui.PlayerBall;
import main.gui.Star;
import main.gui.obstacles.Obstacle;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Game implements Serializable {

	private transient Player player;
	// Player Game 2 way association maybe recursion in serialization
	// When deserializing PLayer object, in savedGames() in a Game object
	// set Game.player = this to give reference of the player to Game object
	private ArrayList<Obstacle> listOfObstacle;
	private ArrayList<Star> listOfStar;
	private ArrayList<ColourSwitchBall> listOfSwitch;
	private PlayerBall ball;
	private int currentScore;

	public ArrayList<ColourSwitchBall> getListOfSwitch() {
		return listOfSwitch;
	}

	public void setListOfSwitch(ArrayList<ColourSwitchBall> listOfSwitch) {
		this.listOfSwitch = listOfSwitch;
	}

	public ArrayList<Star> getListOfStar() {
		return listOfStar;
	}

	public void setListOfStar(ArrayList<Star> listOfStar) {
		this.listOfStar = listOfStar;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Obstacle> getListOfObstacle() {
		return listOfObstacle;
	}

	public void setListOfObstacle(ArrayList<Obstacle> listOfObstacle) {
		this.listOfObstacle = listOfObstacle;
	}

	public PlayerBall getBall() {
		return ball;
	}

	public void setBall(PlayerBall ball) {
		this.ball = ball;
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	/**
	 * Check collision of ball with any game element
	 */
	public abstract void checkCollision();


	/**
	 * pause button click handles
	 */
	public abstract void pauseGame();
	// pause button click handles

	/**
	 * serializes game for that player
	 */
	public abstract void saveGame();
	// serializes game for that player

	public abstract void resumeGame();

	public abstract void startGame();
}
