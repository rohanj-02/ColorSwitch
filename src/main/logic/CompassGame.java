package main.logic;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import main.controllers.StartGameController;
import main.gui.*;
import main.gui.obstacles.*;

import java.util.ArrayList;

import static main.Constants.*;
import static main.Constants.SCREEN_SIZE_X;

public class CompassGame extends Game{
	public static final long serialVersionUID = 3;

	public CompassGame(StartGameController gameController) {
		super(gameController);

		this.listOfSwitch.add(new DirectionSwitcher(new Point(SCREEN_MIDPOINT_X, 350), COLOUR_SWITCH_RADIUS, DirectionSwitcher.Direction.RIGHT));
//		this.listOfSwitch.add(new DirectionSwitcher(new Point(SCREEN_MIDPOINT_X, 400), COLOUR_SWITCH_RADIUS, DirectionSwitcher.Direction.LEFT));
		this.listOfSwitch.add(new DirectionSwitcher(new Point(SCREEN_MIDPOINT_X, 450), COLOUR_SWITCH_RADIUS, DirectionSwitcher.Direction.LEFT));
		for (CollectableBall collectableBall : this.listOfSwitch) {
			collectableBall.render(this.gameRoot);
		}
	}

	@Override
	public boolean isScrollRequired() {
		return (this.getPlayerBall().getBallRoot().getTranslateY() + this.getPlayerBall().getPosY() - PLAYER_START < -SCROLL_THRESHOLD);
	}

	/**
	 * Computes the distance of the topObstacle from y = 0.
	 *
	 * @return double: the computed distance
	 */
	public double getDistanceOfTop() {
		return this.topObstacle.getObstacleRoot().getTranslateY() + this.topObstacle.getObstacleRoot().getLayoutY() + this.topObstacle.getPosY();
	}

	@Override
	public void scrollScreen() {
		double lengthOfScroll = Math.abs(SCROLL_THRESHOLD + this.playerBall.getBallRoot().getTranslateY() + this.getPlayerBall().getPosY() - PLAYER_START);
		this.scrollAnimations = new ArrayList<>();
		// Generate new game elements when they are above NEW_OBSTACLE_SCROLL_THRESHOLD
		double topDistance = getDistanceOfTop();
		if (topDistance > NEW_OBSTACLE_SCROLL_THRESHOLD) {
			this.generateGameElements();
		}

		//Translate all obstacles
		for (Obstacle obstacle : listOfObstacles) {
			TranslateTransition scrollDown = new TranslateTransition(Duration.millis(1000), obstacle.getObstacleRoot());
			scrollDown.setInterpolator(Interpolator.EASE_BOTH);
			scrollDown.setByY(lengthOfScroll);
			scrollDown.setCycleCount(1);
			scrollDown.play();
			scrollAnimations.add(scrollDown);
		}
		// Translate all colour switches
		for (CollectableBall collectableBall : listOfSwitch) {
			TranslateTransition scrollDown = new TranslateTransition(Duration.millis(1000), collectableBall.getRoot());
			scrollDown.setInterpolator(Interpolator.EASE_BOTH);
			scrollDown.setByY(lengthOfScroll);
			scrollDown.setCycleCount(1);
			scrollDown.play();
			scrollAnimations.add(scrollDown);
		}
		// Translate all stars
		for (Star star : listOfStar) {
			TranslateTransition scrollDown = new TranslateTransition(Duration.millis(1000), star.starRoot);
			scrollDown.setInterpolator(Interpolator.EASE_BOTH);
			scrollDown.setByY(lengthOfScroll);
			scrollDown.setCycleCount(1);
			scrollDown.play();
			scrollAnimations.add(scrollDown);
		}
		// Translate the playerBall
		TranslateTransition scrollDown = new TranslateTransition(Duration.millis(1000), this.playerBall.getBallRoot());
		scrollDown.setInterpolator(Interpolator.EASE_BOTH);
		scrollDown.setByY(lengthOfScroll);
		scrollDown.setCycleCount(1);
		scrollDown.play();
		scrollAnimations.add(scrollDown);
	}

	@Override
	public void generateGameElements() {
		this.generateObstacles();
		this.generateStars();
		this.generateColourSwitches();
	}

	/**
	 * Add stars to the game screen. Appends new stars to the listOfStars
	 */
	public void generateStars() {
		Point generationPoint = new Point(SCREEN_MIDPOINT_X, OBSTACLE_GENERATE_START - 3); // 3 added for latency issues
		Star newStar = new Star(generationPoint, STAR_POINTS);
		this.listOfStar.add(newStar);
		newStar.render(this.gameRoot);
	}

	/**
	 * Add colourSwitches to the game screen. Appends new colourSwitches to the listOfSwitches
	 */
	public void generateColourSwitches() {
		Point generationPoint = new Point(SCREEN_MIDPOINT_X, COLOR_SWITCH_START_Y);
		ColourSwitchBall newBall = new ColourSwitchBall(generationPoint, COLOUR_SWITCH_RADIUS);
		this.listOfSwitch.add(newBall);
		newBall.render(this.gameRoot);
	}

	/**
	 * Add obstacles to the game screen. Appends new obstacles to the listOfObstacles
	 */
	public void generateObstacles() {
		// Add fixed size and then size percentage
		int selection = (int) (Math.random() * 5 + 1);
		Point generationPoint = new Point(SCREEN_MIDPOINT_X, OBSTACLE_GENERATE_START);
		boolean direction = Math.random() < 0.5;
		Obstacle newObstacle = new CircleObstacle(generationPoint, CIRCLE_RADIUS, direction);
		switch (selection) {
			case 1:
				newObstacle = new CircleObstacle(generationPoint, CIRCLE_RADIUS, direction);
				break;
			case 2:
				newObstacle = new TriangleObstacle(generationPoint, TRIANGLE_SIDE_LENGTH, direction);
				break;
			case 3:
				newObstacle = new RectangleObstacle(generationPoint, RECTANGLE_WIDTH_LENGTH, RECTANGLE_HEIGHT_LENGTH,
						direction);
				break;
			case 4:
				generationPoint.setX(generationPoint.getX() + PLUS_OFFSET);
				newObstacle = new PlusObstacle(generationPoint, PLUS_SIDE_LENGTH, direction);
				break;
			case 5:
				generationPoint.setX(0);
				newObstacle = new LineObstacle(generationPoint, SCREEN_SIZE_X, direction);
				break;
			default:
				break;
		}
		newObstacle.getObstacleRoot().toBack();
		this.topObstacle = newObstacle;
		this.listOfObstacles.add(newObstacle);
		newObstacle.render(this.gameRoot);
		newObstacle.play();
	}
}
