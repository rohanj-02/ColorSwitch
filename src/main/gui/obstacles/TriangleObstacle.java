package main.gui.obstacles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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
	private final double strokeWidth = 10;
	private final javafx.scene.shape.Line[] edges;
	private final Timeline[] rotAnimationTimeline;
	private final Rotate[] rotAnimation;
	public Group triangleRoot;

	public TriangleObstacle(Point center, double sideLength) {

		this.setPosition(center);
		this.sideLength = sideLength;
		this.vertices = new Point[3];
		this.edges = new javafx.scene.shape.Line[3];

		//Set edges and vertices
		this.vertices[0] = new Point(center.getX() - sideLength / 2, center.getY() - sideLength / (2 * Math.sqrt(3)));
		this.vertices[1] = new Point(center.getX() + sideLength / 2, center.getY() - sideLength / (2 * Math.sqrt(3)));
		this.vertices[2] = new Point(center.getX(), center.getY() + sideLength / Math.sqrt(3));
		this.edges[0] = new Line(this.vertices[0].getX(), this.vertices[0].getY(), this.vertices[1].getX(), this.vertices[1].getY());
		this.edges[1] = new Line(this.vertices[1].getX(), this.vertices[1].getY(), this.vertices[2].getX(), this.vertices[2].getY());
		this.edges[2] = new Line(this.vertices[2].getX(), this.vertices[2].getY(), this.vertices[0].getX(), this.vertices[0].getY());

		// Initialise other arrays
		int length = 3;
		this.rotAnimationTimeline = new Timeline[length];
		this.rotAnimation = new Rotate[length];
		this.triangleRoot = new Group();

		for (int i = 0; i < edges.length; i++) {

			//Remove fill and set stroke
			this.edges[i].setFill(Color.rgb(0, 0, 0, 0));
			this.edges[i].setStrokeWidth(this.strokeWidth);
			this.edges[i].setStroke(Constants.COLOUR_PALETTE[i]);
			this.edges[i].setStrokeLineCap(StrokeLineCap.ROUND);

			// Add rotation mechanics
			this.rotAnimation[i] = new Rotate(0, this.getPosX(), this.getPosY());
			this.rotAnimation[i].setAxis(Rotate.Z_AXIS);
			this.edges[i].getTransforms().add(this.rotAnimation[i]);
			this.rotAnimationTimeline[i] = new Timeline();
			this.rotAnimationTimeline[i].getKeyFrames()
					.add(new KeyFrame(Duration.seconds(5), new KeyValue(this.rotAnimation[i].angleProperty(), 360)));
			this.rotAnimationTimeline[i].setCycleCount(Animation.INDEFINITE);

		}
	}


	public void rotate() {
		// Rotate all arcs.
		for (int i = 0; i < this.edges.length; i++) {
			if (rotAnimationTimeline[i].getStatus() == Animation.Status.PAUSED || rotAnimationTimeline[i].getStatus() == Animation.Status.STOPPED) {
				this.rotAnimationTimeline[i].play();
			} else {
				this.rotAnimationTimeline[i].pause();
			}
		}
	}

	public void renderTriangle(Group root) {
		if (this.triangleRoot.getChildren().containsAll(Arrays.asList(this.edges))) {
			this.triangleRoot.getChildren().removeAll(this.edges);
			root.getChildren().removeAll(this.triangleRoot);
		} else {
			this.triangleRoot.getChildren().addAll(this.edges);
			root.getChildren().addAll(this.triangleRoot);
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
		return false;
	}
}