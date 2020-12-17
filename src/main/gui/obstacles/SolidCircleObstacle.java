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

public class SolidCircleObstacle extends Obstacle {

	public static final long serialVersionUID = 5;
	private final double arcLength = 360.00 / Constants.COLOUR_PALETTE.length;
	transient private SolidCircle[] solidCircleList;
	transient private Timeline rotAnimationTimeline;
	transient private Rotate rotAnimation;
	private double radius;
	private int counter = 0;

	//TODO Add functionality to change strokeWidth
	public SolidCircleObstacle(Point center, double radius, Boolean positiveDirection) {
		this.radius = radius;
		this.setPosition(center);
		int length = Constants.COLOUR_PALETTE.length;
		this.solidCircleList = new SolidCircle[12];
		this.rotAnimationTimeline = new Timeline();
		this.obstacleRoot = new Group();
		this.positiveDirection = positiveDirection;


		for (int i = 0; i < 3; i++) {
			this.solidCircleList[counter] = new SolidCircle(new Point(this.getPosX() + i*Constants.CIRCLE_RADIUS/3,this.getPosY()+i*Constants.CIRCLE_RADIUS/3), Constants.COLOUR_PALETTE[0]);
			this.obstacleRoot.getChildren().add(this.solidCircleList[counter].getElement());
			counter++;

		}
		for (int i = 0; i < 3; i++) {
			this.solidCircleList[counter] = new SolidCircle(new Point(this.getPosX() - i*Constants.CIRCLE_RADIUS/3,this.getPosY()+i*Constants.CIRCLE_RADIUS/3), Constants.COLOUR_PALETTE[1]);
			this.obstacleRoot.getChildren().add(this.solidCircleList[counter].getElement());
			counter++;
		}
		for (int i = 0; i < 3; i++) {
			this.solidCircleList[counter] = new SolidCircle(new Point(this.getPosX() - i*Constants.CIRCLE_RADIUS/3,this.getPosY()- i*Constants.CIRCLE_RADIUS/3), Constants.COLOUR_PALETTE[2]);
			this.obstacleRoot.getChildren().add(this.solidCircleList[counter].getElement());
			counter++;
		}
		for (int i = 0; i < 3; i++) {
			this.solidCircleList[counter] = new SolidCircle(new Point(this.getPosX() + i*Constants.CIRCLE_RADIUS/3,this.getPosY()-i*Constants.CIRCLE_RADIUS/3), Constants.COLOUR_PALETTE[3]);
			this.obstacleRoot.getChildren().add(this.solidCircleList[counter].getElement());
			counter++;
		}

		this.rotAnimation = new Rotate(360, this.getPosX(), this.getPosY());
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
		for(int i = 0; i<solidCircleList.length;i++){
			this.solidCircleList[i].renderCircle(root);
		}
	}

	@Override
	public boolean isCollision(PlayerBall ball) {
		boolean collisionDetected = false;
//		System.out.println("Angle: "+this.rotAnimation.getAngle());

//		for (int i = 0; i < solidCircleList.length; i++) {
//			Shape intersect = Shape.intersect(arcList[i], ball.getBallSVG());
//			if (solidCircleList[i].getStroke() != ball.getBallSVG().getFill()) {
//				if (intersect.getBoundsInLocal().getWidth() != -1) {
//					collisionDetected = true;
//				}
//			}
//		}
//		if (collisionDetected) {
////			System.out.println("Circle Obstacle Collision Detected");
//		}
		return collisionDetected;
	}

	@Override
	public void init() {
		int length = Constants.COLOUR_PALETTE.length;
		this.solidCircleList = new SolidCircle[length];
		this.rotAnimationTimeline = new Timeline();
		this.obstacleRoot = new Group();
//		System.out.println("Circle Obstacle @ " + this.getPosition());
		for (int i = 0; i < 3; i++) {
			this.solidCircleList[counter] = new SolidCircle(new Point(this.getPosX() + i*Constants.CIRCLE_RADIUS/3,this.getPosY()+i*Constants.CIRCLE_RADIUS/3), Constants.COLOUR_PALETTE[0]);
			this.obstacleRoot.getChildren().add(this.solidCircleList[counter].getSolidCircleRoot());
			counter++;
		}
		for (int i = 0; i > -3; i--) {
			this.solidCircleList[counter] = new SolidCircle(new Point(this.getPosX() + i*Constants.CIRCLE_RADIUS/3,this.getPosY()+i*Constants.CIRCLE_RADIUS/3), Constants.COLOUR_PALETTE[1]);
			counter++;
		}
		for (int i = 0; i < 3; i++) {
			this.solidCircleList[counter] = new SolidCircle(new Point(this.getPosX() - i*Constants.CIRCLE_RADIUS/3,this.getPosY()-i*Constants.CIRCLE_RADIUS/3), Constants.COLOUR_PALETTE[2]);
			counter++;
		}
		for (int i = 0; i > -3; i--) {
			this.solidCircleList[counter] = new SolidCircle(new Point(this.getPosX() - i*Constants.CIRCLE_RADIUS/3,this.getPosY()-i*Constants.CIRCLE_RADIUS/3), Constants.COLOUR_PALETTE[3]);
			counter++;
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