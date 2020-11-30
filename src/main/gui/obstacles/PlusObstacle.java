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

public class PlusObstacle extends Obstacle {

	private double armLength;
	private final Line[] armList;
	private final Timeline rotAnimationTimeline;
	private final Rotate rotAnimation;

	public PlusObstacle(Point center, double armLength, Boolean positiveDirection) {
		this.armLength = armLength;
		this.setPosition(center);
		int length = Constants.COLOUR_PALETTE.length;
//		this.rotAnimationTimeline = new Timeline[length];
//		this.rotAnimation = new Rotate[length];
		this.obstacleRoot = new Group();
		this.armList = new javafx.scene.shape.Line[length];
		javafx.scene.shape.Line armBottom = new javafx.scene.shape.Line();
		javafx.scene.shape.Line armRight = new javafx.scene.shape.Line();
		javafx.scene.shape.Line armTop = new javafx.scene.shape.Line();
		javafx.scene.shape.Line armLeft = new Line();
		setCoordinateOfArm(armBottom, this.getPosX(), this.getPosY(), this.getPosX(), this.getPosY() + armLength);
		setCoordinateOfArm(armRight, this.getPosX(), this.getPosY(), this.getPosX() + armLength, this.getPosY());
		setCoordinateOfArm(armTop, this.getPosX(), this.getPosY(), this.getPosX(), this.getPosY() - armLength);
		setCoordinateOfArm(armLeft, this.getPosX(), this.getPosY(), this.getPosX() - armLength, this.getPosY());

		this.armList[0] = armBottom;
		this.armList[1] = armRight;
		this.armList[2] = armTop;
		this.armList[3] = armLeft;

		for (int i = 0; i < armList.length; i++) {
			armList[i].setStroke(Constants.COLOUR_PALETTE[i]);
			armList[i].setStrokeWidth(this.strokeWidth);
			armList[i].setStrokeLineCap(StrokeLineCap.ROUND);
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

	public void setCoordinateOfArm(Line line, double startX, double startY, double endX, double endY) {
		line.setStartX(startX);
		line.setStartY(startY);
		line.setEndX(endX);
		line.setEndY(endY);
	}

	public void play() {
		// Rotate all arcs.
//		for (int i = 0; i < this.armList.length; i++) {
			if (rotAnimationTimeline.getStatus() == Animation.Status.PAUSED || rotAnimationTimeline.getStatus() == Animation.Status.STOPPED) {
				this.rotAnimationTimeline.play();
			} else {
				this.rotAnimationTimeline.pause();
			}
//		}
	}

	public void render(Group root) {
		if (this.obstacleRoot.getChildren().containsAll(Arrays.asList(this.armList))) {
			this.obstacleRoot.getChildren().removeAll(this.armList);
			root.getChildren().removeAll(this.obstacleRoot);
		} else {
			this.obstacleRoot.getChildren().addAll(this.armList);
			root.getChildren().addAll(this.obstacleRoot);
		}
	}

	public double getArmLength() {
		return armLength;
	}

	public void setArmLength(double armLength) {
		this.armLength = armLength;
	}

	@Override
	public boolean isCollision(PlayerBall ball) {
		boolean collisionDetected = false;

		for (int i = 0; i < armList.length; i++) {
			Shape intersect = Shape.intersect(armList[i], ball.getBallRoot());
			if (armList[i].getStroke() != ball.getBallRoot().getFill()) {
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