package main.gui;

import javafx.scene.Group;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import main.Constants;

import java.util.Arrays;

import static main.Constants.SCREEN_MIDPOINT_X;

public class ColourSwitchBall extends GameElement implements Collidable {

	private double radius;
	public static final long serialVersionUID = 10;
	private final double arcLength = 360.00 / Constants.COLOUR_PALETTE.length;
	transient private final Arc[] arcList;
	transient public Group root;
	private static int counter = 1;
	public boolean colourChanged = false;

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
		if (!colourChanged) {
			ball.getBallRoot().setFill(Constants.COLOUR_PALETTE[counter]);
			colourChanged = true;
		}
		counter++;
		if (counter == 4) {
			counter = 0;
		}
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
		}
	}

	public void render(Group root) {
		if (this.root.getChildren().containsAll(Arrays.asList(this.arcList))) {
			this.root.getChildren().removeAll(this.arcList);
			root.getChildren().removeAll(this.root);
		} else {
			this.root.getChildren().addAll(this.arcList);
			root.getChildren().addAll(this.root);
		}
	}


	@Override
	public boolean isCollision(PlayerBall ball) {
		boolean collisionDetected = false;

		Shape intersect = Shape.intersect(arcList[0], ball.getBallRoot());
		if (intersect.getBoundsInLocal().getWidth() != -1) {
			collisionDetected = true;
		}
		if (collisionDetected) {
			System.out.println("Collision Detected");
		}
		return collisionDetected;
	}

	@Override
	public void setPosition() {
		Point point = new Point(SCREEN_MIDPOINT_X, this.root.getTranslateY() + this.root.getLayoutY());
		this.setPosition(point);
	}

	@Override
	public void setOrientation() {
		this.setOrientation(0);
	}

	@Override
	public void init() {
//TODO
	}
}
