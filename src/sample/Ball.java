package sample;

import java.io.IOException;
import java.io.Serializable;

public class Ball extends GameElement {
	private String colour;
	private double jumpSize;
	private double angularVelocity;
	// private int[] position;
	private float radius;

	public static void serialize() throws IOException {
	}

	public static void deserialize() throws IOException, ClassNotFoundException {
	}

	public double getAngularVelocity() {
		return angularVelocity;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public double getJumpSize() {
		return jumpSize;
	}

	public void setJumpSize(float jumpSize) {
		this.jumpSize = jumpSize;
	}

	public void setAngularVelocity(float angularVelocity) {
		this.angularVelocity = angularVelocity;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	/**
	 * Makes the ball jump according to the jumpSize of the ball
	 */
	public void jump() {
		this.setPosY(this.getPosY() + jumpSize);
	}

	/**
	 * Constantly decreases the height of the ball by some n amount
	 */
	public void decreaseHeight(int n) {
		this.setPosY(this.getPosY() - n);
	}

}
