package main.gui.obstacles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import main.Constants;
import main.gui.PlayerBall;
import main.gui.Point;

import java.util.Arrays;

public class TriangleObstacle extends Obstacle {

	private double sideLength;
	private final Point[] vertices;
	transient private final Line[] edges;
	transient private final Timeline rotAnimationTimeline;
	transient private final Rotate rotAnimation;

	public TriangleObstacle(Point center, double sideLength, Boolean positiveDirection) {

		this.setPosition(center);
		this.sideLength = sideLength;
		this.vertices = new Point[3];
		this.edges = new Line[3];

		//Set edges and vertices
		this.vertices[0] = new Point(center.getX() - sideLength / 2, center.getY() - sideLength / (2 * Math.sqrt(3)));
		this.vertices[1] = new Point(center.getX() + sideLength / 2, center.getY() - sideLength / (2 * Math.sqrt(3)));
		this.vertices[2] = new Point(center.getX(), center.getY() + sideLength / Math.sqrt(3));
		this.edges[0] = new Line(this.vertices[0].getX(), this.vertices[0].getY(), this.vertices[1].getX(), this.vertices[1].getY());
		this.edges[1] = new Line(this.vertices[1].getX(), this.vertices[1].getY(), this.vertices[2].getX(), this.vertices[2].getY());
		this.edges[2] = new Line(this.vertices[2].getX(), this.vertices[2].getY(), this.vertices[0].getX(), this.vertices[0].getY());

		// Initialise other arrays
		int length = 3;
//		this.rotAnimationTimeline = new Timeline[length];
//		this.rotAnimation = new Rotate[length];
		this.obstacleRoot = new Group();

		for (int i = 0; i < edges.length; i++) {
			//Remove fill and set stroke
			this.edges[i].setFill(Color.rgb(0, 0, 0, 0));
			this.edges[i].setStrokeWidth(this.strokeWidth);
			this.edges[i].setStroke(Constants.COLOUR_PALETTE[i]);
			this.edges[i].setStrokeLineCap(StrokeLineCap.ROUND);
		}
			// Add rotation mechanics
			this.rotAnimation = new Rotate(0, this.getPosX(), this.getPosY());
			this.rotAnimation.setAxis(Rotate.Z_AXIS);
			this.obstacleRoot.getTransforms().add(this.rotAnimation);
			this.rotAnimationTimeline = new Timeline();
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
//		for (int i = 0; i < this.edges.length; i++) {
			if (rotAnimationTimeline.getStatus() == Animation.Status.PAUSED || rotAnimationTimeline.getStatus() == Animation.Status.STOPPED) {
				this.rotAnimationTimeline.play();
			} else {
				this.rotAnimationTimeline.pause();
			}
//		}
	}

	public void render(Group root) {
		if (this.obstacleRoot.getChildren().containsAll(Arrays.asList(this.edges))) {
			this.obstacleRoot.getChildren().removeAll(this.edges);
			root.getChildren().removeAll(this.obstacleRoot);
		} else {
			this.obstacleRoot.getChildren().addAll(this.edges);
			root.getChildren().addAll(this.obstacleRoot);
		}
	}

	public double getSideLength() {
		return sideLength;
	}

	public void setSideLength(double sideLength) {
		this.sideLength = sideLength;
	}

	@Override
	public boolean isCollision(PlayerBall ball) {
		boolean collisionDetected = false;

		for (int i = 0; i < edges.length; i++) {
			Shape intersect = Shape.intersect(edges[i], ball.getBallRoot());
			if (edges[i].getStroke() != ball.getBallRoot().getFill()) {
				if (intersect.getBoundsInLocal().getWidth() != -1) {
					collisionDetected = true;
				}
			}
		}
		if (collisionDetected) {
			System.out.println("Collision Detected");
		}
		return collisionDetected;
	}
}