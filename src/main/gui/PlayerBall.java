package main.gui;

import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import main.Constants;

import java.io.IOException;

public class PlayerBall extends GameElement {
	private String colour;
	private double jumpSize;
	private double angularVelocity;
	private double radius;
	public Circle root;
	public double maxDisplacement = this.getPosY();
	private Interpolator gravityInterpolator = new Interpolator() {
		@Override
		protected double curve(double t) {
			return -t*2 + t*t*2;
		}
	};

//	ut + 1/2 at^2
	private TranslateTransition gravityTransition;
	private ParallelTransition transitions;
	private TranslateTransition currentJump;

	public PlayerBall(Point position) {
		this.setPosition(position);
		this.radius = 10;
		this.root = new Circle(this.getPosX(), this.getPosY(), 5);
		this.root.setFill(Constants.COLOUR_PALETTE[0]);
		this.gravityTransition = new TranslateTransition(Duration.millis(4000), this.root);
		this.gravityTransition.setInterpolator(this.gravityInterpolator);
		this.gravityTransition.setByY(700f);
		this.gravityTransition.setCycleCount(1);
		this.transitions = new ParallelTransition(this.root, this.gravityTransition);
		this.transitions.play();
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
		Duration time = this.transitions.getCurrentTime();
		Duration jumpTime = Duration.millis(1000);
		this.transitions.pause();
		this.transitions.getChildren().remove(currentJump);
		TranslateTransition jump = new TranslateTransition(jumpTime, this.root);
		jump.setInterpolator(this.gravityInterpolator);
		jump.setByY(100);
		jump.setCycleCount(1);
		jump.play();
		currentJump = jump;
		jump.setOnFinished(actionEvent -> this.transitions.playFrom(time.add(jumpTime)));
		this.transitions.getChildren().add(jump);
	}

	/**
	 * Constantly decreases the height of the ball by some n amount
	 */
	public void decreaseHeight(int n) {
		this.setPosY(this.getPosY() - n);
	}
}