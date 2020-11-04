package sample;

public abstract class GameElement {
	private Point position;

	public float getPosX() {
		return this.position.getX();
	}

	public void setPosX(float _x) {
		this.position.setX(_x);
	}

	public float getPosY() {
		return this.position.getY();
	}

	public void setPosY(float _y) {
		this.position.setY(_y);
	}
}