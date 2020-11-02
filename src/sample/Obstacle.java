package sample;

public abstract class Obstacle {
//	protected int[] position;
	public abstract boolean isColliding(Ball ball); //returns true on collision with user ball
}
