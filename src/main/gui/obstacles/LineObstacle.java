package main.gui.obstacles;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import main.Constants;
import main.gui.PlayerBall;
import main.gui.Point;

import java.util.Arrays;

public class LineObstacle extends Obstacle {

	private final TranslateTransition[] translateTransitions;
	private final Line[] lineList;

	public LineObstacle(Point start, double sceneLength, Boolean positiveDirection) {
		double length = sceneLength / Constants.COLOUR_PALETTE.length;
		this.setPosition(start);
		if (!positiveDirection) {
			this.setPosX(sceneLength - this.getPosX());
		}
		this.lineList = new Line[2 * Constants.COLOUR_PALETTE.length];
		this.obstacleRoot = new Group();
		int noOfSegments = Constants.COLOUR_PALETTE.length;
		this.translateTransitions = new TranslateTransition[2 * Constants.COLOUR_PALETTE.length];
		for (int i = noOfSegments - 1; i >= 0; i--) {
			this.lineList[i] = new Line();
			setCoordinateOfLine(this.lineList[i], this.getPosX() + (i - noOfSegments + 1) * length, this.getPosY(), this.getPosX() + (i - noOfSegments) * length, this.getPosY());
			this.lineList[i].setStroke(Constants.COLOUR_PALETTE[i]);
			this.lineList[i].setStrokeWidth(this.strokeWidth);
		}
		for (int i = noOfSegments; i < 2 * noOfSegments; i++) {
			this.lineList[i] = new Line();
			setCoordinateOfLine(this.lineList[i], this.getPosX() + (i - noOfSegments) * length, this.getPosY(), this.getPosX() + (i - noOfSegments + 1) * length, this.getPosY());
			this.lineList[i].setStroke(Constants.COLOUR_PALETTE[i - noOfSegments]);
			this.lineList[i].setStrokeWidth(this.strokeWidth);
		}

		for (int i = 0; i < 2 * noOfSegments; i++) {
			this.translateTransitions[i] = new TranslateTransition();
			if (positiveDirection) {
				this.translateTransitions[i].setByX(sceneLength);
			} else {
				this.translateTransitions[i].setByX(-sceneLength);
			}
			this.translateTransitions[i].setDuration(Duration.millis(10000));
			this.translateTransitions[i].setInterpolator(Interpolator.LINEAR);
			this.translateTransitions[i].setCycleCount(500);
			this.translateTransitions[i].setNode(this.lineList[i]);
		}

	}

	public void setCoordinateOfLine(Line line, double startX, double startY, double endX, double endY) {
		line.setStartX(startX);
		line.setStartY(startY);
		line.setEndX(endX);
		line.setEndY(endY);
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

	public void play() {
		for (int i = 0; i < this.lineList.length; i++) {
			if (translateTransitions[i].getStatus() == Animation.Status.PAUSED || translateTransitions[i].getStatus() == Animation.Status.STOPPED) {
				this.translateTransitions[i].play();
			} else {
				this.translateTransitions[i].pause();
			}
		}
	}


	public double getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(double strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	@Override
	public boolean isCollision(PlayerBall ball) {
		boolean collisionDetected = false;

		for (int i = 0; i < lineList.length; i++) {
			Shape intersect = Shape.intersect(lineList[i], ball.root);
			if (lineList[i].getStroke() != ball.root.getFill()) {
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