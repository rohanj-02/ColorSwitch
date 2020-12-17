package main.gui;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import main.Constants;

import java.awt.*;
import java.util.Arrays;

import static main.Constants.COLOUR_PALETTE;
import static main.Constants.SCREEN_MIDPOINT_X;

public class ColourSwitchBall extends GameElement implements Collidable {

	public static final long serialVersionUID = 10;
	private static int counter = 1;
	private final double arcLength = 360.00 / Constants.COLOUR_PALETTE.length;
	transient public Group root;
	public boolean colourChanged = false;
	private double radius;
	private Color targetColor;
	transient private Arc[] arcList;

	public ColourSwitchBall(Point center, double radius) {
		this.radius = radius;
		this.setPosition(center);
		this.targetColor = Constants.COLOUR_PALETTE[counter];
		counter++;
		if (counter == 4) {
			counter = 0;
		}
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

	public Color getTargetColor() {
		return targetColor;
	}

	public void setTargetColor(Color targetColor) {
		this.targetColor = targetColor;
	}

	/**
	 * Changes the colour of the ball passed to it.
	 */
	public void changeColour(PlayerBall ball) {
		if (!colourChanged) {
			ball.getBallRoot().setFill(this.targetColor);
			colourChanged = true;
		}
//		counter++;
//		if (counter == 4) {
//			counter = 0;
//		}
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
//			System.out.println("Collision Detected");
		}
		return collisionDetected;
	}

	@Override
	public void setPosition() {
		Point point = new Point(SCREEN_MIDPOINT_X, this.root.getTranslateY() + this.root.getLayoutY() + this.getPosY());
		this.setPosition(point);
	}

	@Override
	public void setOrientation() {
		this.setOrientation(0);
	}

	public Group getRoot() {
		return root;
	}

	public void setRoot(Group root) {
		this.root = root;
	}

	@Override
	public void init() {
		System.out.println("Switch Ball @ " + this.getPosition());
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
}
