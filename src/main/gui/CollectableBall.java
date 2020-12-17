package main.gui;

import javafx.scene.Group;

public abstract class CollectableBall extends GameElement implements Collidable {
	public static final long serialVersionUID = 10;
	transient protected Group root;
	protected double radius;

	public CollectableBall(Point center, double radius) {
		this.radius = radius;
		this.setPosition(center);
		this.root = new Group();
	}

	/**
	 * Returns whether the object has been collected or not
	 *
	 * @return boolean
	 */
	public boolean isCollected() {
		return false;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public abstract void render(Group root);

	@Override
	public void setPosition() {
		Point point = new Point(this.root.getLayoutX() + this.getPosX() + this.root.getTranslateX(), this.root.getTranslateY() + this.root.getLayoutY() + this.getPosY());
		this.setPosition(point);
	}

	@Override
	public void setOrientation() {
		this.setOrientation(this.orientation);
	}

	public Group getRoot() {
		return root;
	}

	public void setRoot(Group root) {
		this.root = root;
	}

	@Override
	public void init(){
		this.root = new Group();

	}

	public abstract void performSpecialFunction(PlayerBall ball);

}
