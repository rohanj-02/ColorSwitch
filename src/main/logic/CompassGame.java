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

//		this.listOfSwitch.add(new DirectionSwitcher(new Point(SCREEN_MIDPOINT_X, 350), COLOUR_SWITCH_RADIUS, DirectionSwitcher.Direction.STRAIGHT_RIGHT));
////		this.listOfSwitch.add(new DirectionSwitcher(new Point(SCREEN_MIDPOINT_X, 400), COLOUR_SWITCH_RADIUS, DirectionSwitcher.Direction.LEFT));
//		this.listOfSwitch.add(new DirectionSwitcher(new Point(SCREEN_MIDPOINT_X, 450), COLOUR_SWITCH_RADIUS, DirectionSwitcher.Direction.STRAIGHT_RIGHT));
//		this.listOfSwitch.add(new DirectionSwitcher(new Point(SCREEN_MIDPOINT_X + 100, 350), COLOUR_SWITCH_RADIUS, DirectionSwitcher.Direction.STRAIGHT_LEFT));
		this.populateLevel();
		this.scaleAllElements();
		this.render();
		this.scrollDirection = DirectionSwitcher.Direction.STRAIGHT;
	}

	private void scaleAllElements() {
		this.playerBall.scalePlayerBall(0.7,0.7);
//		for (Obstacle obstacle : listOfObstacles) {
//		}

		for (CollectableBall collectableBall : listOfSwitch) {
			collectableBall.scaleCollectable(2,2);
		}
		// Translate all stars
		for (Star star : listOfStar) {
			star.scaleStar(2,2);
		}

	}

	private void populateLevel() {
		// Straight then right
		double baseX = SCREEN_MIDPOINT_X;
		double baseY = 400;
		this.listOfObstacles.add(new CircleObstacle(new Point(baseX, baseY), CIRCLE_RADIUS, true));
		this.listOfSwitch.add(new DirectionSwitcher(new Point(baseX, baseY - 150), COLOUR_SWITCH_RADIUS, DirectionSwitcher.Direction.STRAIGHT_RIGHT));
		this.listOfStar.add(new Star(new Point(baseX + 50, baseY - 200 ), STAR_POINTS));
		this.listOfSwitch.add(new ColourSwitchBall(new Point(baseX + 100, baseY - 250), COLOUR_SWITCH_RADIUS));

		// Right to left
		baseX = SCREEN_MIDPOINT_X + 300;
		baseY = 0;
		this.listOfObstacles.add(new CircleObstacle(new Point(baseX, baseY), CIRCLE_RADIUS, true));
		this.listOfSwitch.add(new DirectionSwitcher(new Point(baseX + 125, baseY - 125), COLOUR_SWITCH_RADIUS, DirectionSwitcher.Direction.STRAIGHT_LEFT));
		this.listOfStar.add(new Star(new Point(baseX + 100, baseY - 150 ), STAR_POINTS));
		this.listOfSwitch.add(new ColourSwitchBall(new Point(baseX + 50, baseY - 200), COLOUR_SWITCH_RADIUS));

		// Left
		baseX = baseX + 50 - TRIANGLE_SIDE_LENGTH;
		baseY = baseY - 200 - TRIANGLE_SIDE_LENGTH;
		this.listOfObstacles.add(new TriangleObstacle(new Point(baseX, baseY), TRIANGLE_SIDE_LENGTH, true));
		this.listOfSwitch.add(new DirectionSwitcher(new Point(baseX - 125, baseY - 125), COLOUR_SWITCH_RADIUS, DirectionSwitcher.Direction.STRAIGHT_LEFT));
		this.listOfStar.add(new Star(new Point(baseX - 200, baseY - 200 ), STAR_POINTS));
		this.listOfSwitch.add(new ColourSwitchBall(new Point(baseX - 250, baseY - 250), COLOUR_SWITCH_RADIUS));


		// Left
		baseX = baseX - 250 - RECTANGLE_HEIGHT_LENGTH;
		baseY = baseY - 250 - RECTANGLE_HEIGHT_LENGTH;
		this.listOfObstacles.add(new RectangleObstacle(new Point(baseX, baseY), RECTANGLE_WIDTH_LENGTH, RECTANGLE_HEIGHT_LENGTH, true));
		this.listOfSwitch.add(new DirectionSwitcher(new Point(baseX - 125, baseY - 125), COLOUR_SWITCH_RADIUS, DirectionSwitcher.Direction.STRAIGHT_LEFT));
		this.listOfStar.add(new Star(new Point(baseX - 200, baseY - 200 ), STAR_POINTS));
		this.listOfStar.add(new Star(new Point(baseX - 250, baseY - 250), STAR_POINTS*5));

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
		if(this.playerBall.getDirectionY() == 1){
			if(this.playerBall.getDirectionX() == 0){
				this.scrollDirection = DirectionSwitcher.Direction.STRAIGHT;
				return isScrollRequiredY() == 1;
			}
			else if(this.playerBall.getDirectionX() == -1){
				int flagX = isScrollRequiredX();
				int flagY = isScrollRequiredY();
				if(flagX == 1){
					this.scrollDirection = DirectionSwitcher.Direction.STRAIGHT_LEFT;
					return flagY == 1;
				}
			}
			else if(this.playerBall.getDirectionX() == 1){
				int flagX = isScrollRequiredX();
				int flagY = isScrollRequiredY();
				if(flagX == -1){
					this.scrollDirection = DirectionSwitcher.Direction.STRAIGHT_RIGHT;
					return flagY == 1;
				}
			}
		}
		return false;

//		int flagX = isScrollRequiredX();
//		int flagY = isScrollRequiredY();
//		if(flagX == 1){
//			if(flagY == 0){
//				this.scrollDirection = DirectionSwitcher.Direction.RIGHT;
//			}
//			else if(flagY == 1){
//				this.scrollDirection = DirectionSwitcher.Direction.STRAIGHT_RIGHT;
//			}
//		}
//		else if(flagX == 0 && flagY == 1){
//			this.scrollDirection = DirectionSwitcher.Direction.STRAIGHT;
//		}
//		else if(flagX == -1){
//			if(flagY == 0){
//				this.scrollDirection = DirectionSwitcher.Direction.LEFT;
//			}
//			else if(flagY == 1){
//				this.scrollDirection = DirectionSwitcher.Direction.STRAIGHT_LEFT;
//			}
//		}
//		System.out.println(this.scrollDirection);
//		return flagX != 0 || flagY != 0;
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
				lengthOfScroll = -Math.abs( -SCROLL_THRESHOLD_X + this.playerBall.getBallRoot().getTranslateX() + this.getPlayerBall().getPosX() - PLAYER_START_X);
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
				lengthOfScroll = Math.abs(SCROLL_THRESHOLD_Y + this.playerBall.getBallRoot().getTranslateY() + this.getPlayerBall().getPosY() - PLAYER_START_Y);
				break;
			case STRAIGHT_RIGHT:
			case STRAIGHT_LEFT:
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
		double lengthOfScroll = Math.min(Math.abs(lengthOfScrollX), Math.abs(lengthOfScrollY));
		lengthOfScrollX = (lengthOfScrollX/ Math.abs(lengthOfScrollX)) * lengthOfScroll;
		lengthOfScrollY = (lengthOfScrollY/ Math.abs(lengthOfScrollY)) * lengthOfScroll;

		this.scrollAnimations = new ArrayList<>();

		System.out.println("Length of scrolls : X: " + lengthOfScrollX + ", Y: " + lengthOfScrollY);

		//Translate all obstacles
		for (Obstacle obstacle : listOfObstacles) {
			TranslateTransition scrollDownX = new TranslateTransition(Duration.millis(1000), obstacle.getObstacleRoot());
			scrollDownX.setInterpolator(Interpolator.EASE_BOTH);
			scrollDownX.setByX(lengthOfScrollX);
			scrollDownX.setCycleCount(1);
			scrollDownX.play();
			scrollAnimations.add(scrollDownX);
			TranslateTransition scrollDownY = new TranslateTransition(Duration.millis(1000), obstacle.getObstacleRoot());
			scrollDownY.setInterpolator(Interpolator.EASE_BOTH);
			scrollDownY.setByY(lengthOfScrollY);
			scrollDownY.setCycleCount(1);
			scrollDownY.play();
			scrollAnimations.add(scrollDownY);

		}
		// Translate all colour switches
		for (CollectableBall collectableBall : listOfSwitch) {
			TranslateTransition scrollDownX = new TranslateTransition(Duration.millis(1000), collectableBall.getRoot());
			scrollDownX.setInterpolator(Interpolator.EASE_BOTH);
			scrollDownX.setByX(lengthOfScrollX);
			scrollDownX.setCycleCount(1);
			scrollDownX.play();
			scrollAnimations.add(scrollDownX);
			TranslateTransition scrollDownY = new TranslateTransition(Duration.millis(1000), collectableBall.getRoot());
			scrollDownY.setInterpolator(Interpolator.EASE_BOTH);
			scrollDownY.setByY(lengthOfScrollY);
			scrollDownY.setCycleCount(1);
			scrollDownY.play();
			scrollAnimations.add(scrollDownY);
		}
		// Translate all stars
		for (Star star : listOfStar) {
			TranslateTransition scrollDownX = new TranslateTransition(Duration.millis(1000), star.starRoot);
			scrollDownX.setInterpolator(Interpolator.EASE_BOTH);
			scrollDownX.setByX(lengthOfScrollX);
			scrollDownX.setCycleCount(1);
			scrollDownX.play();
			scrollAnimations.add(scrollDownX);
			TranslateTransition scrollDownY = new TranslateTransition(Duration.millis(1000), star.starRoot);
			scrollDownY.setInterpolator(Interpolator.EASE_BOTH);
			scrollDownY.setByY(lengthOfScrollY);
			scrollDownY.setCycleCount(1);
			scrollDownY.play();
			scrollAnimations.add(scrollDownY);
		}
		// Translate the playerBall

		TranslateTransition scrollDownX = new TranslateTransition(Duration.millis(1000), this.playerBall.getBallRoot());
		scrollDownX.setInterpolator(Interpolator.EASE_BOTH);
		scrollDownX.setByX(lengthOfScrollX);
		scrollDownX.setCycleCount(1);
		scrollDownX.play();
		scrollAnimations.add(scrollDownX);
		TranslateTransition scrollDownY = new TranslateTransition(Duration.millis(1000), this.playerBall.getBallRoot());
		scrollDownY.setInterpolator(Interpolator.EASE_BOTH);
		scrollDownY.setByY(lengthOfScrollY);
		scrollDownY.setCycleCount(1);
		scrollDownY.play();
		scrollAnimations.add(scrollDownY);
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
