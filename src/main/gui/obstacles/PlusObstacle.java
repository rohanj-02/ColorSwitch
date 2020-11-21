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

public class PlusObstacle extends Obstacle {

	private double armLength;
	private final Line[] armList;
	private final Timeline[] rotAnimationTimeline;
	private final Rotate[] rotAnimation;
	public Group plusRoot;


	public PlusObstacle(Point center, double armLength, Boolean positiveDirection) {
		this.armLength = armLength;
		this.setPosition(center);
		int length = Constants.COLOUR_PALETTE.length;
		this.rotAnimationTimeline = new Timeline[length];
		this.rotAnimation = new Rotate[length];
		this.plusRoot = new Group();
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

			rotAnimation[i] = new Rotate(0, this.getPosX(), this.getPosY());
			this.rotAnimation[i].setAxis(Rotate.Z_AXIS);
			this.armList[i].getTransforms().add(this.rotAnimation[i]);
			this.rotAnimationTimeline[i] = new Timeline();
			if(positiveDirection){
				this.rotAnimationTimeline[i].getKeyFrames()
						.add(new KeyFrame(Duration.seconds(5), new KeyValue(this.rotAnimation[i].angleProperty(), 360)));
			}else{
				this.rotAnimationTimeline[i].getKeyFrames()
						.add(new KeyFrame(Duration.seconds(5), new KeyValue(this.rotAnimation[i].angleProperty(), -360)));
			}
			this.rotAnimationTimeline[i].setCycleCount(Animation.INDEFINITE);
		}

	}

	public void setCoordinateOfArm(Line line, double startX, double startY, double endX, double endY) {
		line.setStartX(startX);
		line.setStartY(startY);
		line.setEndX(endX);
		line.setEndY(endY);
	}

	public void play() {
		// Rotate all arcs.
		for (int i = 0; i < this.armList.length; i++) {
			if (rotAnimationTimeline[i].getStatus() == Animation.Status.PAUSED || rotAnimationTimeline[i].getStatus() == Animation.Status.STOPPED) {
				this.rotAnimationTimeline[i].play();
			} else {
				this.rotAnimationTimeline[i].pause();
			}
		}
	}

	public void render(Group root) {
		if (this.plusRoot.getChildren().containsAll(Arrays.asList(this.armList))) {
			this.plusRoot.getChildren().removeAll(this.armList);
			root.getChildren().removeAll(this.plusRoot);
		} else {
			this.plusRoot.getChildren().addAll(this.armList);
			root.getChildren().addAll(this.plusRoot);
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
		return false;
	}
}