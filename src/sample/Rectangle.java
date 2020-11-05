package sample;

public class Rectangle extends Obstacle {

	private float width;
	private float height;
	private float angularVelocity; //decide pivot later
	private float stroke;

	public float getStroke() {
		return stroke;
	}

	public void setStroke(float stroke) {
		this.stroke = stroke;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getAngularVelocity() {
		return angularVelocity;
	}

	public void setAngularVelocity(float angularVelocity) {
		this.angularVelocity = angularVelocity;
	}

	@Override
	public boolean isCollision(Ball ball) {
		return false;
	}
}