package main.gui.obstacles;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.gui.Collidable;
import main.gui.GameElement;
import main.gui.Point;

public abstract class Obstacle extends GameElement implements Collidable {

	public static final long serialVersionUID = 4;
	protected double strokeWidth = 20;
	protected boolean positiveDirection;
	transient protected Group obstacleRoot;

	public boolean isPositiveDirection() {
		return positiveDirection;
	}

	public void setPositiveDirection(boolean positiveDirection) {
		this.positiveDirection = positiveDirection;
	}

	public double getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(double strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	public abstract void render(Group root);

	public abstract void play();

	public Group getObstacleRoot() {
		return obstacleRoot;
	}

	public void setObstacleRoot(Group obstacleRoot) {
		this.obstacleRoot = obstacleRoot;
	}

	// public abstract static void deserialize() throws IOException, ClassNotFoundException;
	@Override
	public void setPosition() {
		Point point = new Point(this.getPosX() + this.obstacleRoot.getLayoutX() + this.obstacleRoot.getTranslateX(), this.obstacleRoot.getTranslateY() + this.obstacleRoot.getLayoutY() + this.getPosY());
		this.setPosition(point);
	}

	//	protected float[] position;

	// public abstract static void serialize() throws IOException;

	@Override
	public void setOrientation() {
		this.setOrientation(this.obstacleRoot.getRotate());
	}

	static class SolidCircle extends GameElement {
		private final Circle element;
		public Group solidCircleRoot;
		private float radius;
		private Color colour;

		// Solid Circle changed but needs to be changed a lot depending on PathObstacle that we create
		public SolidCircle(Point centre, Color color) {
			this.setPosition(centre);
			this.colour = color;
			this.radius = 5;
			this.element = new Circle(centre.getX(), centre.getY(), this.radius);
			this.element.setFill(color);
			this.solidCircleRoot = new Group();
			this.solidCircleRoot.getChildren().add(element);
		}


		public void renderCircle(Group root) {
			if (this.solidCircleRoot.getChildren().contains(this.element)) {
				this.solidCircleRoot.getChildren().remove(this.element);
				root.getChildren().removeAll(this.solidCircleRoot);
			} else {
				this.solidCircleRoot.getChildren().add(this.element);
				root.getChildren().addAll(this.solidCircleRoot);
			}
		}


		public float getRadius() {
			return radius;
		}

		public void setRadius(float radius) {
			this.radius = radius;
		}

		public Color getColour() {
			return colour;
		}

		public void setColour(Color colour) {
			this.colour = colour;
		}

		@Override
		public void setPosition() {
			Point point = new Point(this.getPosX(), this.solidCircleRoot.getTranslateY() + this.solidCircleRoot.getLayoutY());
			this.setPosition(point);
		}

		@Override
		public void setOrientation() {
			this.setOrientation(0);
		}

		@Override
		public void init() {
			System.out.println("Solid Circle init");
		}
	}

}