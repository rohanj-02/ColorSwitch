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

public class Game implements Serializable {

	private transient Player player;
	// Player Game 2 way association maybe recursion in serialization
	// When deserializing PLayer object, in savedGames() in a Game object
	// set Game.player = this to give reference of the player to Game object
	private ArrayList<Obstacle> listOfObstacles;
	private Obstacle topObstacle;
	private ArrayList<Star> listOfStar;
	private ArrayList<ColourSwitchBall> listOfSwitch;
	private PlayerBall playerBall;
	private int currentScore;
	private Group gameRoot;
	private double maxY;
	private StartGameController gameController;

	public Game(StartGameController gameController) {
		this.listOfStar = new ArrayList<>();
		this.listOfSwitch = new ArrayList<>();
		this.gameController = gameController;
		this.listOfObstacles = new ArrayList<>();
		this.gameRoot = new Group();
		this.playerBall = new PlayerBall(new Point(250, 700), this.gameController);
		this.gameRoot.getChildren().add(playerBall.root);
		this.topObstacle = new LineObstacle(new Point(0, 100), 500, true);
		this.listOfObstacles.add(this.topObstacle);
		this.listOfObstacles.add(new LineObstacle(new Point(0, 130), 500, false));
		this.listOfObstacles.add(new PlusObstacle(new Point(200, 200), 50, true));
		this.listOfObstacles.add(new PlusObstacle(new Point(300, 200), 50, false));
		this.listOfObstacles.add(new RectangleObstacle(new Point(250, 400), 100, 100, false));
		this.listOfObstacles.add(new CircleObstacle(new Point(250, 400), 100, true));

		final Duration oneFrameAmt = Duration.millis(1000 / 60);
		final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
				new EventHandler() {

					@Override
					public void handle(Event event) {
						for(Obstacle obstacle: listOfObstacles){
							obstacle.isCollision(playerBall);
						}
					}
		this.listOfStar.add(new Star(new Point(230, 370), 5));

		this.listOfSwitch.add(new ColourSwitchBall(new Point(250, 270), 15));
		checkCollision();

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

	/**
	 * Check collision of ball with any game element
	 */
	public void checkCollision() {
		final Duration oneFrameAmt = Duration.millis(1000 / 60);
		final KeyFrame oneFrame = new KeyFrame(oneFrameAmt, new EventHandler() {

			@Override
			public void handle(Event event) {
				for (Obstacle obstacle : listOfObstacles) {
					if (obstacle.isCollision(playerBall)) {
						obstacle.play();

					}
				}
				for (Star star : listOfStar) {
					if (star.isCollision(playerBall)) {
						star.svgPath.setVisible(false);
						// star.increaseScore(player);
					}
				}

				for (ColourSwitchBall colourSwitchBall : listOfSwitch) {
					if (colourSwitchBall.isCollision(playerBall)) {
						colourSwitchBall.changeColour(playerBall);
					}
				}
			}

		});

		Timeline timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(oneFrame);
		timeline.play();
	}

	/**
	 * pause button click handles
	 */
	public void pauseGame() {

	}
	// pause button click handles

	/**
	 * serializes game for that player
	 */
	public void saveGame() {

	}
	// serializes game for that player

	public void resumeGame() {

	}

	public void startGame() {

	}

	/**
	 * Checks whether there is a need to scroll the screen down based on player
	 * position
	 * 
	 * @return true when scroll is required
	 */
	public boolean isScrollRequired() {
		return (this.getPlayerBall().root.getTranslateY() < -SCROLL_THRESHOLD);
	}

	public double getDistanceOfTop() {
		System.out.println(this.topObstacle.getObstacleRoot().getTranslateY());
		return this.topObstacle.getObstacleRoot().getTranslateY();
	}

	/**
	 * Scrolls everything on the screen(obstacles, stars, player and colour switch
	 * balls) The scroll length is determined by the y value of the player ball
	 */
	public void scrollScreen() {
		double lengthOfScroll = Math.abs(SCROLL_THRESHOLD + this.playerBall.root.getTranslateY());

		if (getDistanceOfTop() > NEW_OBSTACLE_SCROLL_THRESHOLD) {
			this.generateObstacles();
		}

		for (Obstacle obstacle : listOfObstacles) {
			TranslateTransition scrollDown = new TranslateTransition(Duration.millis(1000), obstacle.getObstacleRoot());
			scrollDown.setInterpolator(Interpolator.EASE_BOTH);
			scrollDown.setByY(lengthOfScroll);
			scrollDown.setCycleCount(1);
			scrollDown.play();
		}
		for (ColourSwitchBall colourSwitchBall : listOfSwitch) {
			TranslateTransition scrollDown = new TranslateTransition(Duration.millis(1000), colourSwitchBall.root);
			scrollDown.setInterpolator(Interpolator.EASE_BOTH);
			scrollDown.setByY(lengthOfScroll);
			scrollDown.setCycleCount(1);
			scrollDown.play();
		}
		for (Star star : listOfStar) {
			TranslateTransition scrollDown = new TranslateTransition(Duration.millis(1000), star.root);
			scrollDown.setInterpolator(Interpolator.EASE_BOTH);
			scrollDown.setByY(lengthOfScroll);
			scrollDown.setCycleCount(1);
			scrollDown.play();
		}
		TranslateTransition scrollDown = new TranslateTransition(Duration.millis(1000), this.playerBall.root);
		scrollDown.setInterpolator(Interpolator.EASE_BOTH);
		scrollDown.setByY(lengthOfScroll);
		scrollDown.setCycleCount(1);
		scrollDown.play();
		// playerBall
		System.out.println("Scroll");
	}

	/**
	 * Add obstacles to the game screen. Appends new obstacles to the
	 * listOfObstacles
	 */
	public void generateObstacles() {
		// Add fixed size and then size percentage
		int selection = (int) (Math.random() * 5 + 1);
		double y = this.getMaxY();
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
				newObstacle = new LineObstacle(new Point(0, OBSTACLE_GENERATE_START), SCREEN_SIZE_X, direction);
				break;
			default:
				break;
		}
		this.topObstacle = newObstacle;
		this.listOfObstacles.add(newObstacle);
		newObstacle.render(this.gameRoot);
		newObstacle.play();
	}
}
