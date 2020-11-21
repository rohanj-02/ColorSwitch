package main.gui;

import main.logic.Game;

public class Star extends GameElement implements Collidable {
	// private int[] position;
	private int amount;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Returns whether the star has been collected or not
	 *
	 * @return boolean
	 */
	public boolean isCollected() {
		return false;
	}

	/**
	 * Increase the score of the game
	 */
	public void increaseScore(Game game) {

	}

	@Override
	public boolean isCollision(PlayerBall ball) {
		return false;
	}
}
