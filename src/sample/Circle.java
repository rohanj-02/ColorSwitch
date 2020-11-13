package sample;

public class Circle extends Obstacle {

	private double radius;

	public double getRadius() {

		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public boolean isCollision(Ball ball) {
		return false;
	}
}