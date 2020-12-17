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
	public DirectionSwitcher.Direction scrollDirection;

	public CompassGame(StartGameController gameController) {
		super(gameController);

		this.listOfSwitch.add(new DirectionSwitcher(new Point(SCREEN_MIDPOINT_X, 350), COLOUR_SWITCH_RADIUS, DirectionSwitcher.Direction.STRAIGHT_RIGHT));
//		this.listOfSwitch.add(new DirectionSwitcher(new Point(SCREEN_MIDPOINT_X, 400), COLOUR_SWITCH_RADIUS, DirectionSwitcher.Direction.LEFT));
		this.listOfSwitch.add(new DirectionSwitcher(new Point(SCREEN_MIDPOINT_X, 450), COLOUR_SWITCH_RADIUS, DirectionSwitcher.Direction.STRAIGHT_RIGHT));
		this.listOfSwitch.add(new DirectionSwitcher(new Point(SCREEN_MIDPOINT_X + 100, 350), COLOUR_SWITCH_RADIUS, DirectionSwitcher.Direction.STRAIGHT_LEFT));
		this.render();
		this.scrollDirection = DirectionSwitcher.Direction.STRAIGHT;
	}

	public int isScrollRequiredX(){
		int flagX = 0;
		if(this.getPlayerBall().getDirectionX() == -1 && this.getPlayerBall().getBallRoot().getTranslateX() + this.getPlayerBall().getPosX() - PLAYER_START_X < -SCROLL_THRESHOLD_X){
			flagX = 1; //Should work
			System.out.println("Translate right checked");
		}
		else if(this.getPlayerBall().getDirectionX() == 1 &&  this.getPlayerBall().getBallRoot().getTranslateX() + this.getPlayerBall().getPosX() - PLAYER_START_X > SCROLL_THRESHOLD_X){
			flagX = -1; //Should not
			System.out.println("Translate left checked");
		}
		return flagX;
	}

	public int isScrollRequiredY(){
		int flagY = 0;
		if(this.getPlayerBall().getBallRoot().getTranslateY() + this.getPlayerBall().getPosY() - PLAYER_START_Y < -COMPASS_SCROLL_THRESHOLD_Y){
			flagY = 1;
			System.out.println("Translate Y checked");
		}
		return flagY;
	}

	@Override
	public boolean isScrollRequired() {
		int flagX = isScrollRequiredX();
		int flagY = isScrollRequiredY();
		if(flagX == 1){
			if(flagY == 0){
				this.scrollDirection = DirectionSwitcher.Direction.RIGHT;
			}
			else if(flagY == 1){
				this.scrollDirection = DirectionSwitcher.Direction.STRAIGHT_RIGHT;
			}
		}
		else if(flagX == 0 && flagY == 1){
			this.scrollDirection = DirectionSwitcher.Direction.STRAIGHT;
		}
		else if(flagX == -1){
			if(flagY == 0){
				this.scrollDirection = DirectionSwitcher.Direction.LEFT;
			}
			else if(flagY == 1){
				this.scrollDirection = DirectionSwitcher.Direction.STRAIGHT_LEFT;
			}
		}
		System.out.println(this.scrollDirection);
		return flagX != 0 || flagY != 0;
	}

	public double computeLengthOfScrollX(){
		double lengthOfScroll;
		switch(this.scrollDirection){
			case STRAIGHT_LEFT:
			case LEFT:
				lengthOfScroll = Math.abs(SCROLL_THRESHOLD_X + this.playerBall.getBallRoot().getTranslateX() + this.getPlayerBall().getPosX() - PLAYER_START_X);
				break;
			case STRAIGHT_RIGHT:
			case RIGHT:
				lengthOfScroll = Math.abs( -SCROLL_THRESHOLD_X + this.playerBall.getBallRoot().getTranslateX() + this.getPlayerBall().getPosX() - PLAYER_START_X);
				break;
			default:
				lengthOfScroll = 0;
				break;
		}
		return lengthOfScroll;
	}

	public double computeLengthOfScrollY(){
		double lengthOfScroll;
		switch(this.scrollDirection){
			case STRAIGHT:
			case STRAIGHT_LEFT:
			case STRAIGHT_RIGHT:
				lengthOfScroll = Math.abs(COMPASS_SCROLL_THRESHOLD_Y + this.playerBall.getBallRoot().getTranslateY() + this.getPlayerBall().getPosY() - PLAYER_START_Y);
				break;
			default:
				lengthOfScroll = 0;
				break;
		}
		return lengthOfScroll;
	}

	@Override
	public void scrollScreen() {
		double lengthOfScrollX = computeLengthOfScrollX();
		double lengthOfScrollY = computeLengthOfScrollY();
		this.scrollAnimations = new ArrayList<>();

		System.out.println("Length of scrolls : X: " + lengthOfScrollX + ", Y: " + lengthOfScrollY);

		//Translate all obstacles
		for (Obstacle obstacle : listOfObstacles) {
			TranslateTransition scrollDown = new TranslateTransition(Duration.millis(1000), obstacle.getObstacleRoot());
			scrollDown.setInterpolator(Interpolator.EASE_BOTH);
			scrollDown.setByY(lengthOfScrollY);
			scrollDown.setByX(lengthOfScrollX);
			scrollDown.setCycleCount(1);
			scrollDown.play();
			scrollAnimations.add(scrollDown);
		}
		// Translate all colour switches
		for (CollectableBall collectableBall : listOfSwitch) {
			TranslateTransition scrollDown = new TranslateTransition(Duration.millis(1000), collectableBall.getRoot());
			scrollDown.setInterpolator(Interpolator.EASE_BOTH);
			scrollDown.setByY(lengthOfScrollY);
			scrollDown.setByX(lengthOfScrollX);
			scrollDown.setCycleCount(1);
			scrollDown.play();
			scrollAnimations.add(scrollDown);
		}
		// Translate all stars
		for (Star star : listOfStar) {
			TranslateTransition scrollDown = new TranslateTransition(Duration.millis(1000), star.starRoot);
			scrollDown.setInterpolator(Interpolator.EASE_BOTH);
			scrollDown.setByY(lengthOfScrollY);
			scrollDown.setByX(lengthOfScrollX);
			scrollDown.setCycleCount(1);
			scrollDown.play();
			scrollAnimations.add(scrollDown);
		}
		// Translate the playerBall
		TranslateTransition scrollDown = new TranslateTransition(Duration.millis(1000), this.playerBall.getBallRoot());
		scrollDown.setInterpolator(Interpolator.EASE_BOTH);
		scrollDown.setByY(lengthOfScrollY);
		scrollDown.setByX(lengthOfScrollX);
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
