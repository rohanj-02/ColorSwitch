package main.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Constants.GameStage;
import main.exceptions.UserDoesNotExist;
import main.logic.Game;
import main.menu.MainMenu;
import java.io.*;

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
	private boolean isLogin;
	private boolean isTransitioning; // added to remove the 5 tap skip!
	private Stage primaryStage;
	private MainMenu mainMenu;
	private StartGameController gameController;

	public MainLayoutController(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.mainMenu = new MainMenu();
		try{
			this.deserialize();
			System.out.println("Deserialized Database!");
		}catch(IOException | ClassNotFoundException | NullPointerException e){
			System.out.println("Exception Encountered: ");
			e.printStackTrace();
			System.out.println("Could not load " + FILENAME);
			this.mainMenu = new MainMenu();
		}
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

	public void deserialize() throws IOException, ClassNotFoundException, NullPointerException{
		ObjectInputStream in;
		in = new ObjectInputStream(new FileInputStream(FILENAME));
		this.mainMenu = (MainMenu)in.readObject();
		in.close();

	}

	public void serialize() throws IOException {
		ObjectOutputStream out;
		out = new ObjectOutputStream(new FileOutputStream(FILENAME));
		out.writeObject(mainMenu);
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

	public void createPlayer(String name){
		this.mainMenu.createNewPlayer(name);
	}

	public void setCurrentPlayer(String name) throws UserDoesNotExist {
		this.mainMenu.setCurrentPlayer(name);
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
				case MAINMENU:
					this.loadStage("MainMenu.fxml");
					break;
				case SELECTSAVED:
					this.loadStage("LoadGame.fxml");
					break;
				case STARTGAME:
					this.loadNewGame("GameScreen.fxml");
					break;
				default:
					return;
			}
			if (gameStage != GameStage.STARTGAME) {
				this.bottomPaneController.setParentController(this);
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
		if(this.isTransitioning){
			return;
		}else{
			this.isTransitioning = true;
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
		if(this.isTransitioning){
			return;
		}else{
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

	public void loadNewGame(String name) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../resources/fxml/" + name));
		AnchorPane newRoot = fxmlLoader.load();
		this.gameController = fxmlLoader.getController();
		this.gameController.setPrimaryStage(this.primaryStage);
		this.gameController.render();
		Scene scene = this.getScene();
		this.primaryStage = (Stage) scene.getWindow();
		this.primaryStage.setScene(new Scene(newRoot, SCREEN_SIZE_X, SCREEN_SIZE_Y));
		this.primaryStage.show();
		this.gameController.setMainLayoutController(this);
		Game game = new Game(this.gameController);
		this.gameController.setGame(game);
		this.mainMenu.addGame(game);
	}

	public void loadSavedGame(Game game) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../resources/fxml/GameScreen.fxml"));
		AnchorPane newRoot = fxmlLoader.load();
		this.gameController = fxmlLoader.getController();
		this.gameController.setPrimaryStage(this.primaryStage);
		this.gameController.render();
		Scene scene = this.getScene();
		this.primaryStage = (Stage) scene.getWindow();
		this.primaryStage.setScene(new Scene(newRoot, SCREEN_SIZE_X, SCREEN_SIZE_Y));
		this.primaryStage.show();
//		Game game = new Game(this.gameController);
		this.gameController.setGame(game);
		this.mainMenu.addGame(game);
	}

	public void exitGame() {
		this.primaryStage.close();
		// Serializer code
	}


}
