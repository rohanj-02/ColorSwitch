package main.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Constants.*;
import main.exceptions.GameDoesNotExistException;
import main.exceptions.SameNameException;
import main.exceptions.UserDoesNotExistException;
import main.logic.Game;
import main.logic.GameFactory;
import main.menu.MainMenu;

import java.io.*;
import java.nio.file.Paths;

// TODO Change serialisation s.t. the name of load game is Compass game 1 and Classic game 1 instead of Load Game 1
// TODO Pause after load game initialises ball at top

import static main.Constants.*;

public class MainLayoutController extends AnchorPane {
	@FXML
	private AnchorPane heading;
	@FXML
	private HeadingController headingController;
	/**
	 * The container for bottomAnchorPane object in this class
	 */
	@FXML
	private AnchorPane bottomAnchorPaneContainer;
	/**
	 * The pane that is changed to simulate different stages on the menu
	 */
	@FXML
	private AnchorPane bottomAnchorPane;
	/**
	 * It specifies the controller for the bottomAnchorPane object
	 */
	private LayoutController bottomPaneController;
	/**
	 * Current gameStage
	 */
	private GameStage gameStage;
	/**
	 * isLogin specifies the choice the user made on the landing screen. true for login and false for signup
	 */

	@FXML
	private Button backButton;

	private boolean isLogin;
	private boolean isTransitioning; // added to remove the 5 tap skip!
	private Stage primaryStage;
	private MainMenu mainMenu;
	private StartGameController gameController;
	private Scene mainScene;
	private GameMode gameMode;

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	// TODO change MainMenu design to Singleton
	public MainLayoutController(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.mainMenu = MainMenu.getInstance();
//		try {
//			this.deserialize();
//			System.out.println("Deserialized Database!");
//		} catch (IOException | ClassNotFoundException | NullPointerException e) {
//			System.out.println("Exception Encountered: ");
//			e.printStackTrace();
//			System.out.println("Could not load " + DATABASE_FILENAME);
//			this.mainMenu = MainMenu.getInstance();
//		}
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../resources/fxml/MainLayout.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		this.headingController.addMovingO();
		this.setGameStage(GameStage.LANDING);
		this.setLogin(false);
	}

	public void deserialize() throws IOException, ClassNotFoundException, NullPointerException {
		MainMenu.deserialize();
	}

	public void serialize() throws IOException {
		MainMenu.serialize();
	}

	public void onBackClick(MouseEvent mouseEvent) {
		playClickSound();
		this.decreaseGameStage();
	}

	// ACCESSORS

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean login) {
		isLogin = login;
	}

	public MainMenu getMainMenu() {
		return this.mainMenu;
	}

	public StartGameController getGameController() {
		return gameController;
	}

	public void setGameController(StartGameController gameController) {
		this.gameController = gameController;
	}

	public AnchorPane getBottomAnchorPaneContainer() {
		return bottomAnchorPaneContainer;
	}

	public void setBottomAnchorPaneContainer(AnchorPane bottomAnchorPaneContainer) {
		this.bottomAnchorPaneContainer = bottomAnchorPaneContainer;
	}

	public AnchorPane getBottomAnchorPane() {
		return bottomAnchorPane;
	}

	public void setBottomAnchorPane(AnchorPane bottomAnchorPane) {
		this.bottomAnchorPane = bottomAnchorPane;
	}

	public LayoutController getBottomPaneController() {
		return bottomPaneController;
	}

	public void setBottomPaneController(LayoutController bottomPaneController) {
		this.bottomPaneController = bottomPaneController;
	}

	// PLAYER RELATED METHODS

	public void createPlayer(String name) throws SameNameException {
		this.mainMenu.createNewPlayer(name);
		try{
			this.serialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCurrentPlayer(String name) throws UserDoesNotExistException {
		this.mainMenu.setCurrentPlayer(name);
//		this.gameController.getGame().setPlayer(this.mainMenu.getCurrentPlayer());
	}

	// GAME STAGE RELATED METHODS

	public GameStage getGameStage() {
		return gameStage;
	}


	public void setGameStage(GameStage gameStage) {
		this.gameStage = gameStage;
		try {
			switch (gameStage) {
				case LANDING:
					this.loadStage("Landing.fxml");
					break;
				case LOGIN:
					this.loadStage("Login.fxml");
					((LoginController) this.bottomPaneController).setButtonText(this.isLogin() ? "Login" : "Signup");
					break;
				case MAIN_MENU:
					this.loadStage("MainMenu.fxml");
					break;
				case SELECT_SAVED:
					this.loadStage("LoadGame.fxml");
					((LoadGameController) this.bottomPaneController).setNumberOfGames(this.mainMenu.getNumberOfGames());
					break;
				case GAME_MODE_SELECTION:
					this.loadStage("GameMode.fxml");
					break;
				case START_GAME:
					this.loadNewGame("GameScreen.fxml", this.gameMode);
					break;
				default:
					return;
			}
			if (gameStage != GameStage.START_GAME) {
				this.bottomPaneController.setParentController(this);
				this.backButton.setVisible(this.gameStage != GameStage.LANDING);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Proceed to the next game stage. The next game stage is taken by the {@link GameStage} enum
	 *
	 * @see main.Constants.GameStage
	 */
	public void increaseGameStage() {
		boolean flag = false;
		if (this.isTransitioning) {
			return;
		} else {
			this.isTransitioning = true;
		}
		if(this.gameStage == GameStage.SELECT_SAVED || this.gameStage == GameStage.GAME_MODE_SELECTION){
			this.setGameStage(GameStage.START_GAME);
			return;
		}
		for (GameStage iter : GameStage.values()) {
			if (flag) {
				this.setGameStage(iter);
				break;
			}
			if (iter == this.getGameStage()) {
				flag = true;
			}
		}
	}

	/**
	 * Proceed to the previous game stage. The previous game stage is taken by the {@link GameStage} enum
	 *
	 * @see main.Constants.GameStage
	 */
	public void decreaseGameStage() {

		GameStage prev = GameStage.LANDING;
		if(this.gameStage == GameStage.SELECT_SAVED || this.gameStage == GameStage.GAME_MODE_SELECTION){
			this.setGameStage(GameStage.MAIN_MENU);
			return;
		}
		if (this.isTransitioning) {
			return;
		} else {
			this.isTransitioning = true;
		}
		for (GameStage iter : GameStage.values()) {
			if (iter == this.getGameStage()) {
				this.setGameStage(prev);
			}
			prev = iter;
		}
	}

	/**
	 * This method loads the given FXML file in the bottom layout of the MainLayout
	 * and adds a fade-in fade-out transition to the slides
	 *
	 * @param name The name of the FXML file which contains the stage
	 * @throws IOException On not finding the fxml file in resources/fxml/ directory
	 */
	public void loadStage(String name) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../resources/fxml/" + name));
		AnchorPane newRoot = fxmlLoader.load();
		this.setBottomPaneController(fxmlLoader.getController());
		newRoot.setVisible(false);

		this.bottomAnchorPaneContainer.getChildren().add(newRoot);

		// Create fade animation of new gameStage and remove the old gameStage!
		Timeline timeline = new Timeline();

		//TODO Without definition of bottomAnchorPane when loading landing stage but still works without giving NullPointerException

		KeyValue transparentPrevious = new KeyValue(this.bottomAnchorPane.opacityProperty(), 0.0);
		KeyValue opaquePrevious = new KeyValue(this.bottomAnchorPane.opacityProperty(), 1.0);
		KeyValue transparentNext = new KeyValue(newRoot.opacityProperty(), 0.0);
		KeyValue opaqueNext = new KeyValue(newRoot.opacityProperty(), 1.0);

		KeyFrame startFadeOut = new KeyFrame(Duration.ZERO, opaquePrevious);
		KeyFrame endFadeOut = new KeyFrame(Duration.millis(250), transparentPrevious);
		KeyFrame startFadeIn = new KeyFrame(Duration.millis(500), e -> {
			newRoot.setVisible(true);
		}, transparentNext);
		KeyFrame endFadeIn = new KeyFrame(Duration.millis(750), opaqueNext);

		timeline.getKeyFrames().addAll(startFadeOut, endFadeOut);
		timeline.getKeyFrames().addAll(startFadeIn, endFadeIn);
		timeline.setOnFinished(e -> {
			this.bottomAnchorPaneContainer.getChildren().remove(this.bottomAnchorPane);
			this.setBottomAnchorPane(newRoot);
			this.isTransitioning = false;
		});
		timeline.play();
	}

	// GAME RELATED METHODS

	public void loadNewGame(String name, GameMode gameMode) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../resources/fxml/" + name));
		AnchorPane newRoot = fxmlLoader.load();
		this.gameController = fxmlLoader.getController();

		this.gameController.setPrimaryStage(this.primaryStage);
//		this.gameController.render();
//		this.gameController.getGame().setPlayer(this.mainMenu.getCurrentPlayer());
//		Scene scene = this.getScene();
//		this.primaryStage = (Stage) scene.getWindow();
		this.primaryStage.setScene(new Scene(newRoot, SCREEN_SIZE_X, SCREEN_SIZE_Y));
		this.primaryStage.show();
		this.gameController.setMainLayoutController(this);
		GameFactory gameFactory = new GameFactory();
		Game game = gameFactory.createGame(gameMode, this.gameController);
//		\new Game(this.gameController);
		this.gameController.setGameMode(this.gameMode);
		this.gameController.setGame(game);
		this.gameController.render();
		this.primaryStage.show();
		this.mainMenu.addGame(game);
		this.mainMenu.addPlayerToGame(game);
	}

	public void loadSavedGame(Game game) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../resources/fxml/GameScreen.fxml"));
		AnchorPane newRoot = fxmlLoader.load();
		this.gameController = fxmlLoader.getController();
		this.gameController.setPrimaryStage(this.primaryStage);
//		this.gameController.render();
//		this.gameController.getGame().setPlayer(this.mainMenu.getCurrentPlayer());
//		Scene scene = this.getScene();
//		this.primaryStage = (Stage) scene.getWindow();
		this.primaryStage.setScene(new Scene(newRoot, SCREEN_SIZE_X, SCREEN_SIZE_Y));
		this.primaryStage.show();
		this.gameController.setMainLayoutController(this);
		this.gameController.setGameMode(this.gameMode); // TODO This game mode will be null at that time, set it according to type of game object!
		this.gameController.setLoadedGame(game);
		this.mainMenu.addGame(game);
		this.mainMenu.addPlayerToGame(game);
	}

	public void exitGame() {
		ConfirmExitController exitController;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/fxml/ConfirmExit.fxml"));
			Parent popup = loader.load();
			exitController = loader.getController();
			exitController.setPrimaryStage(this.primaryStage);
			Popup exitPopup = exitController.getPopup();
			exitPopup.setHideOnEscape(false);
			exitPopup.getContent().add(popup);
			exitController.show();
		} catch (IOException e) {
			e.printStackTrace();
			this.primaryStage.close();
		}
	}

	public void forceExitGame() {
		this.primaryStage.close();
	}

	public void loadGame(int index) throws GameDoesNotExistException {
		Game loadedGame = this.mainMenu.getGame(index);
		this.gameStage = GameStage.START_GAME;
		try {
			this.loadSavedGame(loadedGame);
		} catch (IOException e) {
			System.out.println("Could not load FXML file. Exiting game!");
			this.forceExitGame();
		}
	}

	public void playGameSound() {
//		String path = "src/resources/sounds/GameSound-1.mp3";
//		Media media = new Media(new File(path).toURI().toString());
//		MediaPlayer gameSound = new MediaPlayer(media);
//		gameSound.play();
////		AudioClip gameSound;
////		gameSound = new AudioClip(path);
////		gameSound.play();
////		gameSound.setCycleCount(1000);
		playSound sound = new playSound();
		Thread t = new Thread(sound);
		t.start();

	}

	public void playClickSound() {
		String path = "src/resources/sounds/click-2.mp3";
		Media media = new Media(Paths.get(path).toUri().toString());
		MediaPlayer clickSound = new MediaPlayer(media);
		clickSound.play();
	}

	public void setMainScene(Scene mainScene) {
		this.mainScene = mainScene;
	}

	public void setSceneToMain() {
		this.primaryStage.setScene(mainScene);
	}

	public void deleteGame() {
		this.gameController = null;
	}

	class playSound implements Runnable{

		@Override
		public void run() {
			String bip = "src/resources/sounds/GameSound-1.mp3";
			Media hit = new Media(Paths.get(bip).toUri().toString());
			AudioClip mediaPlayer = new AudioClip(hit.getSource());
			mediaPlayer.play();
			mediaPlayer.setVolume(0.2);

		}
	}
}
