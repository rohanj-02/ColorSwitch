package sample;


public class Line extends Obstacle {

	private double width;
	private double startX;
	private double startY;
	private double endX;
	private double endY;

	public Line(double startX, double startY, double endX, double endY) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}



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