package main.gui.obstacles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.shape.Line;
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
	private final Timeline[] rotAnimationTimeline;
	private final Rotate[] rotAnimation;
	public Group rectangleRoot;


	public RectangleObstacle(Point center, double width, double height) {
		this.width = width;
		this.height = height;
		this.setPosition(center);
		int length = Constants.COLOUR_PALETTE.length;
		this.rotAnimationTimeline = new Timeline[length];
		this.rotAnimation = new Rotate[length];
		this.rectangleRoot = new Group();
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
			lineList[i].setStrokeWidth(10.0);
			lineList[i].setStrokeLineCap(StrokeLineCap.ROUND);

			rotAnimation[i] = new Rotate(0, this.getPosX(), this.getPosY());
			this.rotAnimation[i].setAxis(Rotate.Z_AXIS);
			this.lineList[i].getTransforms().add(this.rotAnimation[i]);
			this.rotAnimationTimeline[i] = new Timeline();
			this.rotAnimationTimeline[i].getKeyFrames()
					.add(new KeyFrame(Duration.seconds(5), new KeyValue(this.rotAnimation[i].angleProperty(), 360)));
			this.rotAnimationTimeline[i].setCycleCount(Animation.INDEFINITE);
		}

	}

	public void setCoordinateOfLine(Line line, double startX, double startY, double endX, double endY) {
		line.setStartX(startX);
		line.setStartY(startY);
		line.setEndX(endX);
		line.setEndY(endY);
	}

	public void rotate() {
		// Rotate all arcs.
		for (int i = 0; i < this.lineList.length; i++) {
			if (rotAnimationTimeline[i].getStatus() == Animation.Status.PAUSED || rotAnimationTimeline[i].getStatus() == Animation.Status.STOPPED) {
				this.rotAnimationTimeline[i].play();
			} else {
				this.rotAnimationTimeline[i].pause();
			}
		}
	}

	public void renderRectangle(Group root) {
		if (this.rectangleRoot.getChildren().containsAll(Arrays.asList(this.lineList))) {
			this.rectangleRoot.getChildren().removeAll(this.lineList);
			root.getChildren().removeAll(this.rectangleRoot);
		} else {
			this.rectangleRoot.getChildren().addAll(this.lineList);
			root.getChildren().addAll(this.rectangleRoot);
		}
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
		return false;
	}
}