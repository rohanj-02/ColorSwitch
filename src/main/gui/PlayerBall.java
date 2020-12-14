package main.gui;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import main.Constants;

import java.util.Timer;
import java.util.TimerTask;

import static main.Constants.SCREEN_MIDPOINT_X;

public class PlayerBall extends GameElement {

	public static final long serialVersionUID = 11;
	private final static double jumpSize = 100;
	private final static double radius = 10;
	private String colour;
	private double angularVelocity;
	transient private Circle ballRoot;
	transient private TranslateTransition gravityTransition;
	transient private TranslateTransition currentJump;

	transient private Interpolator gravityInterpolator = new Interpolator() {
		@Override
		protected double curve(double t) {
			return -t * 4 * (1 - 2 * t);
		}
	};

	//	ut + 1/2 at^2

	public PlayerBall(Point position) {
		this.setPosition(position);
		this.ballRoot = new Circle(this.getPosX(), this.getPosY(), radius);
		this.currentJump = new TranslateTransition(Duration.millis(1000), this.ballRoot);
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

	public void setAngularVelocity(float angularVelocity) {
		this.angularVelocity = angularVelocity;
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
		// On for compass
//		jump.setByX(jumpSize);
		jump.setCycleCount(1);
		jump.play();
		this.currentJump = jump;
	}

	/**
	 * Constantly decreases the height of the ball by some n amount
	 */
	public void decreaseHeight(int n) {
		this.setPosY(this.getPosY() - n);
	}

	public void pause() {
		this.currentJump.stop();
		this.setPosition();
		this.gravityTransition.stop();
		this.ballRoot.setVisible(false);
	}

	public void play() {
		this.play(this.getPosY());
	}

	public void play(double collisionY) {
		this.ballRoot.setTranslateY(collisionY);
		this.ballRoot.setVisible(true);
		Timer t = new Timer();
		// TODO Add alert that ball will fall after 3 seconds, else you can start jumping before that also
		// TODO Maybe implement scroll so that game doesn't start with a jerk
		t.schedule(
				new TimerTask() {
					@Override
					public void run() {
						if (PlayerBall.this.gravityTransition.getStatus() != Animation.Status.RUNNING) {
							PlayerBall.this.gravityTransition.playFrom(Duration.millis(5000));
						}
						t.cancel();
					}
				},
				3000
		);
	}

	public double getYPosition() {
		return this.ballRoot.getTranslateY() + this.ballRoot.getLayoutY();
	}

	@Override
	public void setPosition() {
		// Will have to change for compass
		Point point = new Point(SCREEN_MIDPOINT_X, this.getYPosition());
		this.setPosition(point);
	}

	@Override
	public void setOrientation() {
		this.setOrientation(0);
	}

	@Override
	public void init() {
		System.out.println("Player Ball @ " + this.getPosition());
		this.gravityInterpolator = new Interpolator() {
			@Override
			protected double curve(double t) {
				return -t * 4 * (1 - 2 * t);
			}
		};
//		this.ballRoot = new Circle(this.getPosX(), PLAYER_START + this.getPosY(), radius);
		this.ballRoot = new Circle(this.getPosX(), this.getPosY(), radius);
		this.currentJump = new TranslateTransition(Duration.millis(1000), this.ballRoot);
		this.ballRoot.setFill(Constants.COLOUR_PALETTE[0]);
		this.gravityTransition = new TranslateTransition(Duration.millis(10000), this.ballRoot);
		this.gravityTransition.setByY(1000f);
		this.gravityTransition.setCycleCount(1);
		this.gravityTransition.setInterpolator(this.gravityInterpolator);
		this.gravityTransition.setOnFinished(actionEvent -> {
			System.out.println("Gravity finished");
		});
	}

	public void render(Group gameRoot) {
		gameRoot.getChildren().add(this.ballRoot);
	}
}