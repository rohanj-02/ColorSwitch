package main.logic;

import javafx.animation.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.util.Duration;
import main.controllers.StartGameController;
import main.gui.ColourSwitchBall;
import main.gui.PlayerBall;
import main.gui.Point;
import main.gui.Star;
import main.gui.obstacles.*;
import java.io.Serializable;
import java.util.ArrayList;
import static main.Constants.*;

// TODO Give game object an id and ask are you sure you want to overwrite the game? Or something to avoid duplicate saves.
// TODO Pause game on x click if player in STARTGAME Stage

public abstract class Game implements Serializable {

	public static final long serialVersionUID = 3;
	private transient Player player;
	private boolean immunity;
	// Player Game 2 way association maybe recursion in serialization
	// When deserializing PLayer object, in savedGames() in a Game object
	// set Game.player = this to give reference of the player to Game object
	private ArrayList<Obstacle> listOfObstacles;
	private Obstacle topObstacle;
	private ArrayList<Star> listOfStar;
	private ArrayList<ColourSwitchBall> listOfSwitch;
	private PlayerBall playerBall;
	private int currentScore;
	transient private Group gameRoot;
	transient private ArrayList<TranslateTransition> scrollAnimations;
	private double maxY;
	transient private StartGameController gameController;
	private double collisionY = 0.0;
	private double lengthOfScroll;
	private transient Timeline collisionTimeline;

	public Game(StartGameController gameController) {
		this.scrollAnimations = new ArrayList<>();
		this.lengthOfScroll = 0;
		this.currentScore = 0;
		this.listOfStar = new ArrayList<>();
		this.listOfSwitch = new ArrayList<>();
		this.gameController = gameController;
		this.listOfObstacles = new ArrayList<>();
		this.gameRoot = new Group();
		this.playerBall = new PlayerBall(new Point(SCREEN_MIDPOINT_X, PLAYER_START));
		this.gameRoot.getChildren().add(playerBall.getBallRoot());
//		this.listOfObstacles.add(new CircleObstacle(new Point(SCREEN_MIDPOINT_X, 400), CIRCLE_RADIUS, true));
		this.topObstacle = new CircleObstacle(new Point(SCREEN_MIDPOINT_X, 100), CIRCLE_RADIUS, true);
		this.topObstacle.getObstacleRoot().toBack();
		this.listOfObstacles.add(this.topObstacle);
//		this.listOfObstacles.add(new CircleObstacle(new Point(SCREEN_MIDPOINT_X, -200), CIRCLE_RADIUS, false));
		this.listOfStar.add(new Star(new Point(SCREEN_MIDPOINT_X, 400), STAR_POINTS));
		this.listOfStar.add(new Star(new Point(SCREEN_MIDPOINT_X, 100), STAR_POINTS));
//		this.listOfStar.add(new Star(new Point(SCREEN_MIDPOINT_X, -200), STAR_POINTS));
		this.listOfSwitch.add(new ColourSwitchBall(new Point(SCREEN_MIDPOINT_X, -50), COLOUR_SWITCH_RADIUS));
		this.immunity = false;
		checkCollision();
//		printTranslationY();
//
		for (Obstacle obstacle : this.listOfObstacles) {
			obstacle.render(this.gameRoot);
			obstacle.play();
		}
		for (Star star : this.listOfStar) {
			star.render(this.gameRoot);
		}

		for (ColourSwitchBall colourSwitchBall : listOfSwitch) {
			colourSwitchBall.render(this.gameRoot);
		}

	}

	public StartGameController getGameController() {
		return gameController;
	}

	public void setGameController(StartGameController gameController) {
		this.gameController = gameController;
	}

	public Group getGameRoot() {
		return gameRoot;
	}

	public void setGameRoot(Group gameRoot) {
		this.gameRoot = gameRoot;
	}

	public ArrayList<ColourSwitchBall> getListOfSwitch() {
		return listOfSwitch;
	}

	public void setListOfSwitch(ArrayList<ColourSwitchBall> listOfSwitch) {
		this.listOfSwitch = listOfSwitch;
	}

	public ArrayList<Star> getListOfStar() {
		return listOfStar;
	}

	public void setListOfStar(ArrayList<Star> listOfStar) {
		this.listOfStar = listOfStar;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Obstacle> getListOfObstacles() {
		return listOfObstacles;
	}

	public void setListOfObstacles(ArrayList<Obstacle> listOfObstacles) {
		this.listOfObstacles = listOfObstacles;
	}

	public PlayerBall getPlayerBall() {
		return playerBall;
	}

	public void setPlayerBall(PlayerBall playerBall) {
		this.playerBall = playerBall;
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}

	public void destroyGame() {
		this.collisionTimeline.stop();
		this.gameRoot.getChildren().removeAll(this.gameRoot.getChildren());
	}

	/**
	 * Check collision of ball with any game element
	 */
	public void checkCollision() {
		final Duration oneFrameAmt = Duration.millis((float) 1000 / 60);
		final KeyFrame oneFrame = new KeyFrame(oneFrameAmt, new EventHandler() {

			@Override
			public void handle(Event event) {
				if(gameController.getMainLayoutController().getGameStage().equals(GameStage.STARTGAME) && !gameController.getPausePopupController().isOpened()){
					System.out.println(playerBall.getYPosition());
					gameController.getScoreText().setText(Integer.toString(currentScore));
					if (playerBall.getYPosition() > (SCREEN_SIZE_Y - playerBall.getPosY() - PLAYER_RADIUS)) {
						gameController.endGame();
					}

					for (Obstacle obstacle : listOfObstacles) {
						if (obstacle.isCollision(playerBall)) {
							if(!immunity){
								gameController.endGame();
								gameController.getScoreText().setText(Integer.toString(currentScore));
							}
							immunity = true;
//						System.out.println(collisionY);
						}
					}

					Star collectedStar = null;
					for (Star star : listOfStar) {
						if (star.isCollision(playerBall)) {
							if (!star.isCollected()) {
//							System.out.println(star.svgPath.getLayoutY());
								collectedStar = star;
								star.increaseScore(player.getCurrentGame());
								gameController.getScoreText().setText(Integer.toString(currentScore));
								star.svgPath.setVisible(false);
							}
						}
					}
					if(collectedStar != null){
						listOfStar.remove(collectedStar);
					}

					ColourSwitchBall collectedColourSwitchBall = null;
					for (ColourSwitchBall colourSwitchBall : listOfSwitch) {
						if (colourSwitchBall.isCollision(playerBall)) {
							collectedColourSwitchBall = colourSwitchBall;
							colourSwitchBall.changeColour(playerBall);
							collisionY = colourSwitchBall.getRoot().getLayoutY();
							colourSwitchBall.root.setVisible(false);
						}
					}
					if(collectedColourSwitchBall != null){
						listOfSwitch.remove(collectedColourSwitchBall);
					}
				}
			}
		});
		this.collisionTimeline = new Timeline();
		this.collisionTimeline.setCycleCount(Animation.INDEFINITE);
		this.collisionTimeline.getKeyFrames().add(oneFrame);
		this.collisionTimeline.play();
	}

	/**
	 * pause button click handles
	 */
	public void pauseGame() {
		this.playerBall.pause();
		for (TranslateTransition transition : this.scrollAnimations) {
			transition.stop();
		}
	}

	public void endGame() {
		this.playerBall.pause();
		for (TranslateTransition transition : this.scrollAnimations) {
			transition.stop();
		}
	}

	public void playGame() {
		this.playerBall.play();
	}

	public void playGameAfterStar() {
//		System.out.println(collisionY);
		this.playerBall.playGameAfterStar(collisionY, this);
	}

	public boolean isImmunity() {
		return immunity;
	}

	public void setImmunity(boolean immunity) {
		this.immunity = immunity;
	}

	/**
	 * Checks whether there is a need to scroll the screen down based on player
	 * position
	 *
	 * @return boolean: true when scroll is required
	 */
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

	/**
	 * Scrolls everything on the screen(obstacles, stars, player and colour switch
	 * balls). The scroll length is determined by the y value of the player ball.
	 */
	public void scrollScreen() {
		double lengthOfScroll = Math.abs(SCROLL_THRESHOLD + this.playerBall.getBallRoot().getTranslateY() + this.getPlayerBall().getPosY() - PLAYER_START);
		this.lengthOfScroll += lengthOfScroll;
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
		for (ColourSwitchBall colourSwitchBall : listOfSwitch) {
			TranslateTransition scrollDown = new TranslateTransition(Duration.millis(1000), colourSwitchBall.root);
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

	/**
	 * Generates obstacles, stars and colourSwitches and adds them to the game screen.
	 */
	public void generateGameElements() {
		this.generateObstacles();
		this.generateStars();
		this.generateSwitches();
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
	public void generateSwitches() {
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

	/**
	 * Initialise game after serialization
	 */
	public void init() {
		// TODO After save game needs to be exited
		// TODO Line obstacle may start in the center after init
		this.gameRoot = new Group();
		for (Obstacle obstacle : this.listOfObstacles) {
			obstacle.init();
			obstacle.render(this.gameRoot);
			obstacle.play();
		}
		for (ColourSwitchBall colourSwitchBall : listOfSwitch) {
			colourSwitchBall.init();
			colourSwitchBall.render(this.gameRoot);
		}
		for (Star star : listOfStar) {
			star.init();
			star.render(this.gameRoot);
		}
		this.playerBall.init();
		this.playerBall.render(this.gameRoot);
		this.scrollAnimations = new ArrayList<>();
		System.out.println(this.topObstacle.getPosition());
		checkCollision();
	}

	public void serialize() {
		for (Obstacle obstacle : this.listOfObstacles) {
			obstacle.setPosition();
		}

		for (ColourSwitchBall colourSwitchBall : this.listOfSwitch) {
			colourSwitchBall.setPosition();
		}

		for (Star star : this.listOfStar) {
			star.setPosition();
		}
		this.playerBall.setPosition();
	}
}