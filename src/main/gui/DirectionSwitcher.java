package main.gui;

import javafx.scene.Group;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import main.Constants;

import java.io.Serializable;

public class DirectionSwitcher extends CollectableBall{

	public enum Direction implements Serializable {
		STRAIGHT, STRAIGHT_LEFT, STRAIGHT_RIGHT, LEFT, RIGHT;
	}

	public static final long serialVersionUID = 10;
	private boolean directionChanged;
	private Direction direction;
	transient private SVGPath directionSVG;

	public DirectionSwitcher(Point center, double radius, Direction direction) {
		super(center, radius);
		this.directionChanged = false;
		this.directionSVG = new SVGPath();
		String path = "M12 2L4.5 20.29l.71.71L12 18l6.79 3 .71-.71z";
		this.directionSVG.setContent(path);
		this.directionSVG.setScaleX(1.5);
		this.directionSVG.setScaleY(1.5);
		this.directionSVG.setLayoutX(center.getX() - this.directionSVG.getLayoutBounds().getWidth() / 2);
		this.directionSVG.setLayoutY(center.getY() - this.directionSVG.getLayoutBounds().getHeight() / 2);
		this.directionSVG.setFill(Constants.DIRECTION_COLOUR);
		this.setDirection(direction);
	}


	public boolean isDirectionChanged() {
		return directionChanged;
	}

	public void setDirectionChanged(boolean directionChanged) {
		this.directionChanged = directionChanged;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
		double angle = 0;
		if(direction == Direction.STRAIGHT_LEFT){
			angle = 315;
		}
		else if(direction == Direction.STRAIGHT_RIGHT){
			angle = 45;
		}
		else if(direction == Direction.RIGHT){
			angle = 90;
		}
		else if(direction == Direction.LEFT){
			angle = 270;
		}
		this.directionSVG.setRotate(angle);
	}

	@Override
	public void render(Group root) {
		if(!this.root.getChildren().contains(this.directionSVG)){
			this.root.getChildren().add(this.directionSVG);
		}
		root.getChildren().addAll(this.root);
	}

	@Override
	public void performSpecialFunction(PlayerBall ball) {
		this.changeDirection(ball);
	}

	private void changeDirection(PlayerBall ball) {
		// LEFT RIGHT TO STRAIGHT DOES NOT WORK.
		if(!this.directionChanged){
			if(this.direction == Direction.STRAIGHT_LEFT){
				ball.setDirectionX(-1);
				ball.setDirectionY(1);
			}
			else if(this.direction == Direction.STRAIGHT_RIGHT){
				ball.setDirectionX(1);
				ball.setDirectionY(1);
			}
			else if(this.direction == Direction.RIGHT){
				ball.setDirectionX(1);
				ball.setDirectionY(0);
			}
			else if(this.direction == Direction.LEFT){
				ball.setDirectionX(-1);
				ball.setDirectionY(0);
			}
			else if(this.direction == Direction.STRAIGHT){
				ball.setDirectionX(0);
				ball.setDirectionY(1);
			}
			else{
				ball.setDirectionX(0);
			}
		}
	}

	@Override
	public void scaleCollectable(double scaleX, double scaleY){
		this.directionSVG.setScaleX(scaleX);
		this.directionSVG.setScaleY(scaleY);
	}

	@Override
	public boolean isCollision(PlayerBall ball) {
		boolean collisionDetected = false;
		Shape intersect = Shape.intersect(this.directionSVG, ball.getBallRoot());
		if (intersect.getBoundsInLocal().getWidth() != -1) {
			collisionDetected = true;
		}
		if (collisionDetected) {
			System.out.println("Direction Collision Detected");
		}
		return collisionDetected;
	}

	@Override
	public void init() {
		super.init();
		this.directionSVG = new SVGPath();
		String path = "M12 2L4.5 20.29l.71.71L12 18l6.79 3 .71-.71z";
		this.directionSVG.setContent(path);
		this.directionSVG.setScaleX(1.5);
		this.directionSVG.setScaleY(1.5);
		this.directionSVG.setLayoutX(this.getPosX() - this.directionSVG.getLayoutBounds().getWidth() / 2);
		this.directionSVG.setLayoutY(this.getPosY() - this.directionSVG.getLayoutBounds().getHeight() / 2);
		this.directionSVG.setFill(Constants.DIRECTION_COLOUR);
		this.setDirection(this.direction);
	}
}
