package main.logic;

import javafx.animation.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.util.Duration;
import main.controllers.StartGameController;
import main.gui.*;
import main.gui.obstacles.*;
import java.io.Serializable;
import java.util.ArrayList;
import static main.Constants.*;

// TODO Give game object an id and ask are you sure you want to overwrite the game? Or something to avoid duplicate saves.
// TODO Pause game on x click if player in STARTGAME Stage

public abstract class Game implements Serializable {

	public static final long serialVersionUID = 3;
	protected transient Player player;
	protected boolean immunity;
	// Player Game 2 way association maybe recursion in serialization
	// When deserializing PLayer object, in savedGames() in a Game object
	// set Game.player = this to give reference of the player to Game object
	protected ArrayList<Obstacle> listOfObstacles;
	protected Obstacle topObstacle;
	protected ArrayList<Star> listOfStar;
	protected ArrayList<CollectableBall> listOfSwitch;
	protected PlayerBall playerBall;
	protected int currentScore;
	protected transient Group gameRoot;
	protected transient ArrayList<TranslateTransition> scrollAnimations;
	protected double maxY;
	transient protected StartGameController gameController;
	protected double collisionY = 0.0;
	protected transient Timeline collisionTimeline;

	public Game(StartGameController gameController) {
		this.scrollAnimations = new ArrayList<>();
		this.currentScore = 0;
		this.listOfStar = new ArrayList<>();
		this.listOfSwitch = new ArrayList<>();
		this.gameController = gameController;
		this.listOfObstacles = new ArrayList<>();
		this.gameRoot = new Group();
		this.playerBall = new PlayerBall(new Point(SCREEN_MIDPOINT_X, PLAYER_START_Y));
		this.gameRoot.getChildren().add(playerBall.getBallRoot());
//		this.listOfObstacles.add(new CircleObstacle(new Point(SCREEN_MIDPOINT_X, 400), CIRCLE_RADIUS, true));
		this.topObstacle = new CircleObstacle(new Point(SCREEN_MIDPOINT_X, -100), CIRCLE_RADIUS, true);
		this.topObstacle.getObstacleRoot().toBack();
		this.listOfObstacles.add(this.topObstacle);
//		this.listOfObstacles.add(new CircleObstacle(new Point(SCREEN_MIDPOINT_X, -200), CIRCLE_RADIUS, false));
//		this.listOfStar.add(new Star(new Point(SCREEN_MIDPOINT_X, 400), STAR_POINTS));
//		this.listOfStar.add(new Star(new Point(SCREEN_MIDPOINT_X, 100), STAR_POINTS));
//		this.listOfStar.add(new Star(new Point(SCREEN_MIDPOINT_X, -200), STAR_POINTS));
		this.listOfSwitch.add(new ColourSwitchBall(new Point(SCREEN_MIDPOINT_X, -50), COLOUR_SWITCH_RADIUS));
		this.immunity = false;
		checkCollision();
//		printTranslationY();
//
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

	public ArrayList<CollectableBall> getListOfSwitch() {
		return listOfSwitch;
	}

	public void setListOfSwitch(ArrayList<CollectableBall> listOfSwitch) {
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
				if(gameController.getMainLayoutController().getGameStage().equals(GameStage.START_GAME) && !gameController.getPausePopupController().isOpened()){
//					System.out.println(playerBall.getYPosition());
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

					CollectableBall collectedBall = null;
					for (CollectableBall collectableBall : listOfSwitch) {
						if (collectableBall.isCollision(playerBall)) {
							collectedBall = collectableBall;
							collectableBall.performSpecialFunction(playerBall);
							collisionY = collectableBall.getRoot().getLayoutY();
							collectableBall.getRoot().setVisible(false);
						}
					}
					if(collectedBall != null){
						listOfSwitch.remove(collectedBall);
					}
				}
			}
		});
		this.collisionTimeline = new Timeline();
		this.collisionTimeline.setCycleCount(Animation.INDEFINITE);
		this.collisionTimeline.getKeyFrames().add(oneFrame);
		this.collisionTimeline.play();
	}

	public void render(){
		for (Obstacle obstacle : this.listOfObstacles) {
			obstacle.render(this.gameRoot);
			obstacle.play();
		}
		for (Star star : this.listOfStar) {
			star.render(this.gameRoot);
		}

		for (CollectableBall collectableBall : listOfSwitch) {
			collectableBall.render(this.gameRoot);
		}
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
	public abstract boolean isScrollRequired();


	/**
	 * Scrolls everything on the screen(obstacles, stars, player and colour switch
	 * balls). The scroll length is determined by the y value of the player ball.
	 */
	public abstract void scrollScreen();

	/**
	 * Generates obstacles, stars and colourSwitches and adds them to the game screen.
	 */
	public abstract void generateGameElements();

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
		for (CollectableBall collectableBall : listOfSwitch) {
			collectableBall.init();
			collectableBall.render(this.gameRoot);
		}
		for (Star star : listOfStar) {
			star.init();
			star.render(this.gameRoot);
		}
		this.playerBall.init();
		this.playerBall.render(this.gameRoot);
		this.scrollAnimations = new ArrayList<>();
//		System.out.println(this.topObstacle.getPosition());
		checkCollision();
	}

	public void serialize() {
		for (Obstacle obstacle : this.listOfObstacles) {
			obstacle.setPosition();
		}

		for (CollectableBall collectableBall : this.listOfSwitch) {
			collectableBall.setPosition();
		}

		for (Star star : this.listOfStar) {
			star.setPosition();
		}
		this.playerBall.setPosition();
	}
}