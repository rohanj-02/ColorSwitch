package main.gui.obstacles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import main.Constants;
import main.gui.PlayerBall;
import main.gui.Point;

import java.util.Arrays;

public class RectangleObstacle extends Obstacle {

	private double width;
	private double height;
	private final Line[] lineList;
	private final Timeline rotAnimationTimeline;
	private final Rotate rotAnimation;

	public RectangleObstacle(Point center, double width, double height, Boolean positiveDirection) {
		this.width = width;
		this.height = height;
		this.setPosition(center);
		int length = Constants.COLOUR_PALETTE.length;
//		this.rotAnimationTimeline = new Timeline[length];
//		this.rotAnimation = new Rotate[length];
		this.obstacleRoot = new Group();
		this.lineList = new Line[length];
		Line lineBottom = new Line();
		Line lineRight = new Line();
		Line lineTop = new Line();
		Line lineLeft = new Line();
		setCoordinateOfLine(lineBottom, this.getPosX() - width / 2.0, this.getPosY() + height / 2.0, this.getPosX() + width / 2.0, this.getPosY() + height / 2.0);
		setCoordinateOfLine(lineRight, this.getPosX() + width / 2.0, this.getPosY() - height / 2.0, this.getPosX() + width / 2.0, this.getPosY() + height / 2.0);
		setCoordinateOfLine(lineTop, this.getPosX() - width / 2.0, this.getPosY() - height / 2.0, this.getPosX() + width / 2.0, this.getPosY() - height / 2.0);
		setCoordinateOfLine(lineLeft, this.getPosX() - width / 2.0, this.getPosY() - height / 2.0, this.getPosX() - width / 2.0, this.getPosY() + height / 2.0);

		this.lineList[0] = lineBottom;
		this.lineList[1] = lineRight;
		this.lineList[2] = lineTop;
		this.lineList[3] = lineLeft;

		for (int i = 0; i < lineList.length; i++) {
			lineList[i].setStroke(Constants.COLOUR_PALETTE[i]);
			lineList[i].setStrokeWidth(this.strokeWidth);
			lineList[i].setStrokeLineCap(StrokeLineCap.ROUND);
		}
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

	public void setCoordinateOfLine(Line line, double startX, double startY, double endX, double endY) {
		line.setStartX(startX);
		line.setStartY(startY);
		line.setEndX(endX);
		line.setEndY(endY);
	}

	public void play() {
		// Rotate all arcs.
//		for (int i = 0; i < this.lineList.length; i++) {
			if (rotAnimationTimeline.getStatus() == Animation.Status.PAUSED || rotAnimationTimeline.getStatus() == Animation.Status.STOPPED) {
				this.rotAnimationTimeline.play();
			} else {
				this.rotAnimationTimeline.pause();
			}
//		}
	}

	public void render(Group root) {
		if (this.obstacleRoot.getChildren().containsAll(Arrays.asList(this.lineList))) {
			this.obstacleRoot.getChildren().removeAll(this.lineList);
			root.getChildren().removeAll(this.obstacleRoot);
		} else {
			this.obstacleRoot.getChildren().addAll(this.lineList);
			root.getChildren().addAll(this.obstacleRoot);
		}
	}

	public void moveDown() {


	}

	public double getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public boolean isCollision(PlayerBall ball) {
		boolean collisionDetected = false;

		for (int i = 0; i < lineList.length; i++) {
			Shape intersect = Shape.intersect(lineList[i], ball.getBallRoot());
			if (lineList[i].getStroke() != ball.getBallRoot().getFill()) {
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