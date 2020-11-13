package sample;

public class Rectangle extends Obstacle {

	private double width;
	private double height;


	public double getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public boolean isCollision(Ball ball) {
		return false;
	}
}