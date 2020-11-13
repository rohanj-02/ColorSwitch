package sample;

public class Line extends Obstacle {

	private double width;

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	@Override
	public boolean isCollision(Ball ball) {
		return false;
	}
}