package sample;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.io.IOException;
import java.io.Serializable;

public class Ball extends GameElement {
	private String colour;
	private double jumpSize;
	private double angularVelocity;
	// private int[] position;
	private double radius;
	public Circle root;
	private TranslateTransition gravity;


	public Ball(Point position){
		this.setPosition(position);
		this.radius = 10;
		this.root = new Circle(this.getPosX(), this.getPosY(), 5);
		this.root.setFill(Constants.COLOUR_PALETTE[0]);
		this.gravity = new TranslateTransition(Duration.millis(1500000), this.root);
		this.gravity.interpolatorProperty().set(Interpolator.EASE_IN);
		this.gravity.setByY(100000000);
		this.gravity.setCycleCount(Animation.INDEFINITE);
		this.gravity.play();
	}

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

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * Makes the ball jump according to the jumpSize of the ball
	 */
	public void jump() {
		TranslateTransition jump = new TranslateTransition(Duration.millis(200), this.root);
		jump.interpolatorProperty().set(Interpolator.EASE_IN);
		jump.setByY(-30000);
//		jump.play();
		Node dummyNode = new Rectangle();
		this.gravity.setNode(dummyNode);
		this.gravity = new TranslateTransition(Duration.millis(1500000), this.root);
		this.gravity.interpolatorProperty().set(Interpolator.EASE_IN);
		this.gravity.setByY(100000000);
		this.gravity.setCycleCount(Animation.INDEFINITE);
		ParallelTransition seqT = new ParallelTransition(this.root, jump, this.gravity);

//		this.setPosY(this.getPosY() + jumpSize);
		seqT.play();
	}

	/**
	 * Constantly decreases the height of the ball by some n amount
	 */
	public void decreaseHeight(int n) {
		this.setPosY(this.getPosY() - n);
	}

}
