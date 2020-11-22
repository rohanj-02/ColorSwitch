package main.gui;

import javafx.scene.Group;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import main.Constants;

public class ColourSwitchBall extends GameElement implements Collidable {

	private double radius;

	private final double arcLength = 360.00 / Constants.COLOUR_PALETTE.length;
	private final Arc[] arcList;
	public Group root;

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

	public ColourSwitchBall(Point center, double radius) {
		this.radius = radius;
		this.setPosition(center);
		int length = Constants.COLOUR_PALETTE.length;
		this.arcList = new Arc[length];
		this.root = new Group();

		for (int i = 0; i < this.arcList.length; i++) {
			this.arcList[i] = new Arc(this.getPosX(), this.getPosY(), radius, radius, i * this.arcLength, this.arcLength);
			// Remove fill and set stroke
			this.arcList[i].setType(ArcType.ROUND);
			this.arcList[i].setFill(Constants.COLOUR_PALETTE[i]);
			this.arcList[i].setStrokeWidth(0);
			this.arcList[i].setStroke(Constants.COLOUR_PALETTE[i]);
			root.getChildren().add(arcList[i]);
		}
	}


	@Override
	public boolean isCollision(PlayerBall ball) {
		return false;
	}
}
