package sample;

public class Triangle extends Obstacle {

	private float sideLength;
	private float angularVelocity;
	private float stroke;

	public float getStroke() {
		return stroke;
	}

	public void setStroke(float stroke) {
		this.stroke = stroke;
	}

	public float getSideLength() {
		return sideLength;
	}

	public void setSideLength(float sideLength) {
		this.sideLength = sideLength;
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