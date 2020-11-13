package sample;

public class ColourSwitch extends GameElement implements Collidable {

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
	public void changeColour(Ball ball) {
		//Set enum or list of strings and change colour
	}


	@Override
	public boolean isCollision(Ball ball) {
		return false;
	}
}
