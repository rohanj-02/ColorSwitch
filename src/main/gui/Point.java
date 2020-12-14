package main.gui;

import java.io.Serializable;

public class Point implements Serializable {
	public static final long serialVersionUID = 12;
	private double x;
	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return this.x;
	}

	public void setX(double _x) {
		this.x = _x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double _y) {
		this.y = _y;
	}

	@Override
	public String toString() {
		return "Point{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}