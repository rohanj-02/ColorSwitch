package sample;

public class Star extends GameElement implements Collidable {
	// private int[] position;

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
	public boolean isCollision(Ball ball) {
		return false;
	}
}
