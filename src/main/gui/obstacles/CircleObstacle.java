package main.gui.obstacles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import main.Constants;
import main.gui.PlayerBall;
import main.gui.Point;

import java.util.Arrays;


public class CircleObstacle extends Obstacle {

	private final double arcLength = 360.00 / Constants.COLOUR_PALETTE.length;
	private final Arc[] arcList;
	private final Timeline[] rotAnimationTimeline;
	private final Rotate[] rotAnimation;
	public Group circleRoot;
	private double radius;

	public CircleObstacle(Point center, double radius, Boolean positiveDirection) {
		this.radius = radius;
		this.setPosition(center);
		int length = Constants.COLOUR_PALETTE.length;
		this.arcList = new Arc[length];
		this.rotAnimationTimeline = new Timeline[length];
		this.rotAnimation = new Rotate[length];
		this.circleRoot = new Group();

		for (int i = 0; i < this.arcList.length; i++) {
			this.arcList[i] = new Arc(this.getPosX(), this.getPosY(), radius, radius, i * this.arcLength, this.arcLength);

			// Remove fill and set stroke
			this.arcList[i].setFill(Color.rgb(0, 0, 0, 0));
			this.arcList[i].setStrokeWidth(this.strokeWidth);
			this.arcList[i].setStroke(Constants.COLOUR_PALETTE[i]);

			// Add rotation mechanics
			this.rotAnimation[i] = new Rotate(0, this.getPosX(), this.getPosY());
			this.rotAnimation[i].setAxis(Rotate.Z_AXIS);
			this.arcList[i].getTransforms().add(this.rotAnimation[i]);
			this.rotAnimationTimeline[i] = new Timeline();
			if (positiveDirection) {
				this.rotAnimationTimeline[i].getKeyFrames()
						.add(new KeyFrame(Duration.seconds(5), new KeyValue(this.rotAnimation[i].angleProperty(), 360)));
			} else {
				this.rotAnimationTimeline[i].getKeyFrames()
						.add(new KeyFrame(Duration.seconds(5), new KeyValue(this.rotAnimation[i].angleProperty(), -360)));
			}

			this.rotAnimationTimeline[i].setCycleCount(Animation.INDEFINITE);
		}

	}

	public void play() {
		// Rotate all arcs.
		for (int i = 0; i < this.arcList.length; i++) {
			if (rotAnimationTimeline[i].getStatus() == Animation.Status.PAUSED || rotAnimationTimeline[i].getStatus() == Animation.Status.STOPPED) {
				this.rotAnimationTimeline[i].play();
			} else {
				this.rotAnimationTimeline[i].pause();
			}
		}
	}

	public void printRotation() {
		for (int i = 0; i < this.rotAnimation.length; i++) {
			System.out.println(i + ": " + this.rotAnimation[i].getAngle());
		}
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void render(Group root) {
		if (this.circleRoot.getChildren().containsAll(Arrays.asList(this.arcList))) {
			this.circleRoot.getChildren().removeAll(this.arcList);
			root.getChildren().removeAll(this.circleRoot);
		} else {
			this.circleRoot.getChildren().addAll(this.arcList);
			root.getChildren().addAll(this.circleRoot);
		}
	}

	@Override
	public boolean isCollision(PlayerBall ball) {
		return false;
	}
}