package main.gui;

import javafx.scene.Group;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import main.Constants;

import java.util.Arrays;

public class ColourSwitchBall extends CollectableBall {

	public static final long serialVersionUID = 10;
	private static int counter = 1;
	private final double arcLength = 360.00 / Constants.COLOUR_PALETTE.length;
	private boolean colourChanged = false;
	transient private Arc[] arcList;

	public ColourSwitchBall(Point center, double radius) {
		super(center, radius);

		int length = Constants.COLOUR_PALETTE.length;
		this.arcList = new Arc[length];

		for (int i = 0; i < this.arcList.length; i++) {
			this.arcList[i] = new Arc(this.getPosX(), this.getPosY(), radius, radius, i * this.arcLength, this.arcLength);
			// Remove fill and set stroke
			this.arcList[i].setType(ArcType.ROUND);
			this.arcList[i].setFill(Constants.COLOUR_PALETTE[i]);
			this.arcList[i].setStrokeWidth(0);
			this.arcList[i].setStroke(Constants.COLOUR_PALETTE[i]);
		}
	}

	@Override
	public void performSpecialFunction(PlayerBall ball) {
		this.changeColour(ball);
	}

	/**
	 * Changes the colour of the ball passed to it.
	 */
	public void changeColour(PlayerBall ball) {
		if (!colourChanged) {
			ball.getBallSVG().setFill(Constants.COLOUR_PALETTE[counter]);
			colourChanged = true;
		}
		counter++;
		if (counter == 4) {
			counter = 0;
		}
	}

	@Override
	public void render(Group root) {
		if (this.root.getChildren().containsAll(Arrays.asList(this.arcList))) {
//			this.root.getChildren().removeAll(this.arcList);
//			root.getChildren().removeAll(this.root);
			return;
		} else {
			this.root.getChildren().addAll(this.arcList);
			root.getChildren().addAll(this.root);
		}
	}

	@Override
	public void scaleCollectable(double scaleX, double scaleY){
//		for(Arc arc : this.arcList){
//			arc.setScaleX(scaleX);
//			arc.setScaleY(scaleY);
//		}
		this.root.setScaleX(scaleX*0.8);
		this.root.setScaleY(scaleY*0.8);
	}

	@Override
	public boolean isCollision(PlayerBall ball) {
		boolean collisionDetected = false;

		Shape intersect = Shape.intersect(arcList[0], ball.getBallSVG());
		if (intersect.getBoundsInLocal().getWidth() != -1) {
			collisionDetected = true;
		}
		if (collisionDetected) {
//			System.out.println("Collision Detected");
		}
		return collisionDetected;
	}

	@Override
	public void init() {
		super.init();
		System.out.println("Switch Ball @ " + this.getPosition());
		int length = Constants.COLOUR_PALETTE.length;
		this.arcList = new Arc[length];

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
