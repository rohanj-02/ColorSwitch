package main.gui;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import main.Constants;
import main.logic.Game;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

// TODO Space + P = Ball disappears ?!
// TODO If no translate transition working then kill ball, otherwise ball may hover in between
// TODO Add NoSavedGamesException

import static main.Constants.*;

public class PlayerBall extends GameElement {

	public static final long serialVersionUID = 11;
	private final static double jumpSize = 100;
	private final static double radius = PLAYER_RADIUS;
	private String colour;
	private double angularVelocity;
//	transient private Circle ballRoot;
	transient private Group root;
	transient private TranslateTransition gravityTransition;
	transient private TranslateTransition currentJump;
//	transient private Timeline rotAnimationTimeline;
	transient private RotateTransition rotAnimation;
	private Point pausePosition;
	transient private SVGPath ballSVG;
	private Boolean posDirection = true;

	transient private Interpolator gravityInterpolator = new Interpolator() {
		@Override
		protected double curve(double t) {
			return -t * 4 * (1 - 2 * t);
		}
	};

	//	ut + 1/2 at^2

	public PlayerBall(Point position) {
		this.setPosition(position);
		this.pausePosition = new Point(position);
		int path_count = getRandomNumberUsingNextInt(0, 5);
//		path_count = 4;
		String path = Constants.pathList[path_count];
		ballSVG = new SVGPath();
		ballSVG.setContent(path);
		ballSVG.setLayoutX(this.getPosX());
		ballSVG.setLayoutY(this.getPosY());
//		this.rotAnimationTimeline = new Timeline();
//		this.ballRoot = new Circle(this.getPosX(), this.getPosY(), radius);
		this.rotAnimation = new RotateTransition();
		this.rotAnimation.setAxis(Rotate.Z_AXIS);
		this.rotAnimation.setNode(this.getBallSVG());
		this.rotAnimation.setByAngle(360);
		this.rotAnimation.setCycleCount(Animation.INDEFINITE);
		this.rotAnimation.setDuration(Duration.seconds(3));
		this.rotAnimation.setAutoReverse(true);
		this.currentJump = new TranslateTransition(Duration.millis(1000), this.ballSVG);
		this.ballSVG.setFill(Constants.COLOUR_PALETTE[0]);
		this.gravityTransition = new TranslateTransition(Duration.millis(10000), this.ballSVG);
		this.gravityTransition.setByY(1000f);
		this.gravityTransition.setCycleCount(1);
		this.gravityTransition.setInterpolator(this.gravityInterpolator);


		this.gravityTransition.setOnFinished(actionEvent -> {
			System.out.println("Gravity finished");
//			gameController.simulateEnd();
		});
	}


	public int getRandomNumberUsingNextInt(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min) + min;
	}

	public void rotateBall(Boolean direction){
		this.rotAnimation.play();
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

	public SVGPath getBallSVG() {
		return ballSVG;
	}

	public void setBallSVG(SVGPath ballSVG) {
		this.ballSVG = ballSVG;
	}

	public double getRadius() {
		return radius;
	}

	/**
	 * Makes the ball jump according to the jumpSize of the ball
	 */
	public void jump() {
		rotateBall(this.posDirection);
		if (!this.posDirection) this.posDirection = true;
		else this.posDirection = false;
		if (this.gravityTransition.getStatus() != Animation.Status.RUNNING) {
			this.gravityTransition.playFrom(Duration.millis(5000));
		}
		TranslateTransition jump = new TranslateTransition(Duration.millis(1000), this.ballSVG);
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
//		this.setPosition(new Point(SCREEN_MIDPOINT_X, this.ballRoot.getTranslateY() + this.ballRoot.getLayoutY() + this.getPosY()));
		this.setPausePosition();
		this.currentJump.stop();
		this.gravityTransition.stop();
		this.ballSVG.setVisible(false);
	}

	public double getYPosition() {
		return this.ballSVG.getTranslateY() + this.ballSVG.getLayoutY();
	}

	public void play() {
		this.play(this.pausePosition.getY());
	}

	public void setPausePosition(){
		System.out.println(" Ball Layout Y" + this.ballSVG.getLayoutY());
		System.out.println(" Ball Translate Y" + this.ballSVG.getTranslateY());
		System.out.println(" Ball Pos Y" + this.getPosY());
		this.pausePosition = new Point(SCREEN_MIDPOINT_X, this.ballSVG.getTranslateY() + this.ballSVG.getLayoutY() + this.getPosY());
		System.out.println("Pause position" + this.pausePosition);
	}

	public void playGameAfterStar(double collisionY, Game game) {
		System.out.println(this.ballSVG.getLayoutY() - collisionY);
		this.ballSVG.setTranslateY(this.ballSVG.getLayoutY() - collisionY);
		this.ballSVG.setVisible(true);
		Timer t = new Timer();
		// TODO Add alert that ball will fall after 3 seconds, else you can start jumping before that also
		// TODO Maybe implement scroll so that game doesn't start with a jerk
		t.schedule(
				new TimerTask() {
					@Override
					public void run() {
						game.setImmunity(false);
						if (PlayerBall.this.gravityTransition.getStatus() != Animation.Status.RUNNING) {
							PlayerBall.this.gravityTransition.playFrom(Duration.millis(5000));
						}
						t.cancel();
					}
				},
				3000
		);
	}public void play(double collisionY) {
		this.ballSVG.setTranslateY(collisionY - PLAYER_START);
//		this.ballRoot.setLayoutY(collisionY);
		this.ballSVG.setVisible(true);
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

	@Override
	public void setPosition() {
		// ! TO BE CALLED ONLY AFTER PAUSE MENU HAS BEEN CALLED!
		// Will have to change for compass
		System.out.println(" Ball Layout Y" + this.ballSVG.getLayoutY());
		System.out.println(" Ball Translate Y" + this.ballSVG.getTranslateY());
		System.out.println(" Ball Pos Y" + this.getPosY());
		System.out.println("Ball Pause Pos Y " + this.pausePosition.getY());
		Point point = new Point(SCREEN_MIDPOINT_X, this.pausePosition.getY());
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
		int path_count = getRandomNumberUsingNextInt(0, 5);
//		path_count = 4;
		String path = Constants.pathList[path_count];
		ballSVG = new SVGPath();
		ballSVG.setContent(path);
		ballSVG.setLayoutX(this.getPosX());
		ballSVG.setLayoutY(this.getPosY());
//		this.ballRoot = new Circle(this.getPosX(), this.getPosY(), radius);
		this.currentJump = new TranslateTransition(Duration.millis(1000), this.ballSVG);
		this.ballSVG.setFill(Constants.COLOUR_PALETTE[0]);
		this.gravityTransition = new TranslateTransition(Duration.millis(10000), this.ballSVG);
		this.gravityTransition.setByY(1000f);
		this.gravityTransition.setCycleCount(1);
		this.gravityTransition.setInterpolator(this.gravityInterpolator);
		this.gravityTransition.setOnFinished(actionEvent -> {
			System.out.println("Gravity finished");
		});
	}

	public void render(Group gameRoot) {
		gameRoot.getChildren().add(this.ballSVG);
	}
}