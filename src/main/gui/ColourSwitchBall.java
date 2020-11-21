package main.gui;

public class ColourSwitchBall extends GameElement implements Collidable {

	private double radius;

	/**
	 * Returns whether the object has been collected or not
	 *
	 * @return boolean
	 */
	public boolean isCollected() {
		return false;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * Changes the colour of the ball passed to it.
	 */
	public void changeColour(PlayerBall ball) {
		//Set enum or list of strings and change colour
	}


	@Override
	public boolean isCollision(PlayerBall ball) {
		return false;
	}
}
