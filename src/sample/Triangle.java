package sample;

public class Triangle extends Obstacle {

	private double sideLength;


	public double getSideLength() {
		return sideLength;
	}

	public void setSideLength(double sideLength) {
		this.sideLength = sideLength;
	}

	@Override
	public boolean isCollision(Ball ball) {
		return false;
	}
}