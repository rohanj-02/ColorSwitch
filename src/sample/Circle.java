package sample;

public class Circle extends Obstacle {

	private float radius;
	private float stroke;
	private float angularVelocity;

	public float getRadius() {

		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public float getStroke() {
		return stroke;
	}

	public void setStroke(float stroke) {
		this.stroke = stroke;
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