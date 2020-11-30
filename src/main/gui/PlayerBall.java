package main.gui;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import main.Constants;
import main.controllers.StartGameController;
import java.io.IOException;
import static main.Constants.SCREEN_MIDPOINT_X;

public class PlayerBall extends GameElement {
	private String colour;
	private final static double jumpSize = 100;
	private double angularVelocity;
	private final static double radius = 15;
	transient private Circle ballRoot;
	transient private final TranslateTransition gravityTransition;

	//TODO Add to deserialization
	transient private final Interpolator gravityInterpolator = new Interpolator() {
		@Override
		protected double curve(double t) {
			return -t * 4 * (1 - 2 * t);
		}
	};

	//	ut + 1/2 at^2

	public PlayerBall(Point position, StartGameController gameController) {
		this.setPosition(position);
		this.ballRoot = new Circle(this.getPosX(), this.getPosY(), radius);
		this.ballRoot.setFill(Constants.COLOUR_PALETTE[0]);
		this.gravityTransition = new TranslateTransition(Duration.millis(10000), this.ballRoot);
		this.gravityTransition.setByY(1000f);
		this.gravityTransition.setCycleCount(1);
		this.gravityTransition.setInterpolator(this.gravityInterpolator);
		this.gravityTransition.setOnFinished(actionEvent -> {
			System.out.println("Gravity finished");
//			gameController.simulateEnd();
		});
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

	public Circle getBallRoot() {
		return ballRoot;
	}

	public void setBallRoot(Circle ballRoot) {
		this.ballRoot = ballRoot;
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
		TranslateTransition jump = new TranslateTransition(Duration.millis(1000), this.ballRoot);
		jump.setInterpolator(this.gravityInterpolator);
		jump.setByY(jumpSize);
		jump.setCycleCount(1);
		jump.play();
	}

	/**
	 * Constantly decreases the height of the ball by some n amount
	 */
	public void decreaseHeight(int n) {
		this.setPosY(this.getPosY() - n);
	}

	@Override
	public void setPosition() {
		Point point = new Point(SCREEN_MIDPOINT_X, this.ballRoot.getTranslateY() + this.ballRoot.getLayoutY());
		this.setPosition(point);
	}

	@Override
	public void setOrientation() {
		this.setOrientation(0);
	}
}