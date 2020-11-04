package sample;

public class Bar extends Obstacle {

	private float width;
	private float linearVelocity;

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getLinearVelocity() {
		return linearVelocity;
	}

	public void setLinearVelocity(float linearVelocity) {
		this.linearVelocity = linearVelocity;
	}

	@Override
	public boolean isCollision(Ball ball) {
		return false;
	}
}