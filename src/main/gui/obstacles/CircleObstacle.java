package main.gui.obstacles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import main.Constants;
import main.gui.PlayerBall;
import main.gui.Point;

import java.util.Arrays;

public class CircleObstacle extends Obstacle {

	public static final long serialVersionUID = 5;
	private final double arcLength = 360.00 / Constants.COLOUR_PALETTE.length;
	transient private Arc[] arcList;
	transient private Timeline rotAnimationTimeline;
	transient private Rotate rotAnimation;
	private double radius;

	//TODO Add functionality to change strokeWidth
	public CircleObstacle(Point center, double radius, Boolean positiveDirection) {
		this.radius = radius;
		this.setPosition(center);
		int length = Constants.COLOUR_PALETTE.length;
		this.arcList = new Arc[length];
		this.rotAnimationTimeline = new Timeline();
		this.obstacleRoot = new Group();
		this.positiveDirection = positiveDirection;

		for (int i = 0; i < this.arcList.length; i++) {
			this.arcList[i] = new Arc(this.getPosX(), this.getPosY(), radius, radius, i * this.arcLength, this.arcLength);

			// Remove fill and set stroke
			this.arcList[i].setFill(Color.rgb(0, 0, 0, 0));
			this.arcList[i].setStrokeWidth(this.strokeWidth);
			this.arcList[i].setStroke(Constants.COLOUR_PALETTE[i]);
		}

		this.rotAnimation = new Rotate(0, this.getPosX(), this.getPosY());
		this.rotAnimation.setAxis(Rotate.Z_AXIS);
		obstacleRoot.getTransforms().add(this.rotAnimation);
		if (positiveDirection) {
			this.rotAnimationTimeline.getKeyFrames()
					.add(new KeyFrame(Duration.seconds(5), new KeyValue(this.rotAnimation.angleProperty(), 360)));
		} else {
			this.rotAnimationTimeline.getKeyFrames()
					.add(new KeyFrame(Duration.seconds(5), new KeyValue(this.rotAnimation.angleProperty(), -360)));
		}

		this.rotAnimationTimeline.setCycleCount(Animation.INDEFINITE);
	}

	public void play() {
		// Rotate all arcs.
		if (rotAnimationTimeline.getStatus() == Animation.Status.PAUSED || rotAnimationTimeline.getStatus() == Animation.Status.STOPPED) {
			this.rotAnimationTimeline.play();
		} else {
			this.rotAnimationTimeline.pause();
		}
	}

	public void printRotation() {
//		for (int i = 0; i < this.rotAnimation.length; i++) {
		System.out.println(": " + this.rotAnimation.getAngle());
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void render(Group root) {
		if (this.obstacleRoot.getChildren().containsAll(Arrays.asList(this.arcList))) {
			this.obstacleRoot.getChildren().removeAll(this.arcList);
			root.getChildren().removeAll(this.obstacleRoot);
		} else {
			this.obstacleRoot.getChildren().addAll(this.arcList);
			root.getChildren().addAll(this.obstacleRoot);
		}
	}

	@Override
	public boolean isCollision(PlayerBall ball) {
		boolean collisionDetected = false;
		System.out.println("Angle: "+this.rotAnimation.getAngle());

		for (int i = 0; i < arcList.length; i++) {
			Shape intersect = Shape.intersect(arcList[i], ball.getBallRoot());
			if (arcList[i].getStroke() != ball.getBallRoot().getFill()) {
				if (intersect.getBoundsInLocal().getWidth() != -1) {
					collisionDetected = true;
				}
			}
		}
		if (collisionDetected) {
			System.out.println("Circle Obstacle Collision Detected");
		}
		return collisionDetected;
	}

	@Override
	public void init() {
		int length = Constants.COLOUR_PALETTE.length;
		this.arcList = new Arc[length];
		this.rotAnimationTimeline = new Timeline();
		this.obstacleRoot = new Group();
		System.out.println("Circle Obstacle @ " + this.getPosition());
		for (int i = 0; i < this.arcList.length; i++) {
			this.arcList[i] = new Arc(this.getPosX(), this.getPosY(), radius, radius, i * this.arcLength + this.getOrientation(), this.arcLength);

			// Remove fill and set stroke
			this.arcList[i].setFill(Color.rgb(0, 0, 0, 0));
			this.arcList[i].setStrokeWidth(this.strokeWidth);
			this.arcList[i].setStroke(Constants.COLOUR_PALETTE[i]);
		}

		this.rotAnimation = new Rotate(0, this.getPosX(), this.getPosY());
		this.rotAnimation.setAxis(Rotate.Z_AXIS);
		this.obstacleRoot.getTransforms().add(this.rotAnimation);
		if (this.positiveDirection) {
			this.rotAnimationTimeline.getKeyFrames()
					.add(new KeyFrame(Duration.seconds(5), new KeyValue(this.rotAnimation.angleProperty(), 360)));
		} else {
			this.rotAnimationTimeline.getKeyFrames()
					.add(new KeyFrame(Duration.seconds(5), new KeyValue(this.rotAnimation.angleProperty(), -360)));
		}
		this.rotAnimationTimeline.setCycleCount(Animation.INDEFINITE);
	}
}