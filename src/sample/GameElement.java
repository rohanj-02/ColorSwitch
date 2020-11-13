package sample;

import java.io.Serializable;

public abstract class GameElement implements Serializable {
	private Point position;

	public double getPosX() {
		return this.position.getX();
	}

	public void setPosX(double  _x) {
		this.position.setX(_x);
	}

	public double getPosY() {
		return this.position.getY();
	}

	public void setPosY(double _y) {
		this.position.setY(_y);
	}
}