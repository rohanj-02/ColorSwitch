package main.gui;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import main.Constants;
import main.controllers.StartGameController;

import java.io.IOException;

public class PlayerBall extends GameElement {
	private String colour;
	private final static double jumpSize = 100;
	private double angularVelocity;
	private final static double radius = 15;
	public Circle root;
	//	public double maxDisplacement = this.getPosY();
	private final Interpolator gravityInterpolator = new Interpolator() {
		@Override
		protected double curve(double t) {
			return -t * 4 * (1 - 2 * t);
		}
	};

	//	ut + 1/2 at^2
	private final TranslateTransition gravityTransition;
	//	private final ParallelTransition transitions;
	private TranslateTransition currentJump;

	public PlayerBall(Point position, StartGameController gameController) {
		this.setPosition(position);
		this.root = new Circle(this.getPosX(), this.getPosY(), radius);
		this.root.setFill(Constants.COLOUR_PALETTE[0]);
		this.gravityTransition = new TranslateTransition(Duration.millis(10000), this.root);
		this.gravityTransition.setByY(1000f);
		this.gravityTransition.setCycleCount(1);
		this.gravityTransition.setInterpolator(this.gravityInterpolator);
		this.gravityTransition.setOnFinished(actionEvent -> {
			System.out.println("Gravity finished");
//			gameController.simulateEnd();
		});
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

	public void setAngularVelocity(float angularVelocity) {
		this.angularVelocity = angularVelocity;
	}

	public double getRadius() {
		return radius;
	}

	/**
	 * Makes the ball jump according to the jumpSize of the ball
	 */
	public void jump() {
		if (this.gravityTransition.getStatus() != Animation.Status.RUNNING) {
			this.gravityTransition.playFrom(Duration.millis(5000));
		}
		TranslateTransition jump = new TranslateTransition(Duration.millis(1000), this.root);
		jump.setInterpolator(this.gravityInterpolator);
		jump.setByY(jumpSize);
		jump.setCycleCount(1);
		jump.play();
		currentJump = jump;
	}

	/**
	 * Constantly decreases the height of the ball by some n amount
	 */
	public void decreaseHeight(int n) {
		this.setPosY(this.getPosY() - n);
	}
}