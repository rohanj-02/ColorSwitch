package main.gui;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import main.Constants;
import main.gui.obstacles.Obstacle;

import java.util.Arrays;
import java.util.ConcurrentModificationException;

public class collisionBall extends Obstacle {

	public Group ballRoot;
	public Circle[] circleList;
	private Path[] pathList;
	private PathTransition[] pathTransitions;
	public collisionBall(Point point, double radius){
		this.ballRoot = new Group();
		this.circleList = new Circle[1];
		this.pathList = new Path[1];
		this.pathTransitions = new PathTransition[1];
		for(int i = 0; i<circleList.length; i++){
			this.circleList[i] = new Circle();
			this.pathTransitions[i] = new PathTransition();
			this.pathList[i] = new Path();
			this.circleList[i].setRadius(radius);
			this.circleList[i].setStrokeWidth(5);
			this.circleList[i].setCenterX(point.getX());
			this.circleList[i].setCenterY(point.getY());
			this.circleList[i].setFill(Constants.COLOUR_PALETTE[(int) (Math.random() * (3))]);
			MoveTo moveTo = new MoveTo(point.getX(), point.getY());
			CubicCurveTo cubicCurveTo = new CubicCurveTo(point.getX(), point.getY(),210, 30, 175, 398 );
			this.pathList[i].getElements().add(moveTo);
			this.pathList[i].getElements().add(cubicCurveTo);
			this.pathTransitions[i].setDuration(Duration.millis(2000));
			this.pathTransitions[i].setNode(this.circleList[i]);
			this.pathTransitions[i].setPath(this.pathList[i]);
			this.pathTransitions[i].setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		}

	}



	@Override
	public void render(Group root) {
		if (this.ballRoot.getChildren().containsAll(Arrays.asList(this.circleList))) {
			this.ballRoot.getChildren().removeAll(this.circleList);
			root.getChildren().removeAll(this.ballRoot);
		} else {
			this.ballRoot.getChildren().addAll(this.circleList);
			root.getChildren().addAll(this.ballRoot);
		}
	}


	@Override
	public void play() {
		for (int i = 0; i < this.circleList.length; i++) {
			if (pathTransitions[i].getStatus() == Animation.Status.PAUSED || pathTransitions[i].getStatus() == Animation.Status.STOPPED) {
				this.pathTransitions[i].play();
			} else {
				this.pathTransitions[i].pause();
			}
		}
	}

	@Override
	public boolean isCollision(PlayerBall ball) {
		return false;
	}
}
