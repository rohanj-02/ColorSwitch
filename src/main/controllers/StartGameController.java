package main.controllers;

import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Constants;
import main.gui.PlayerBall;
import main.logic.Game;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class StartGameController implements Initializable {

	private final Popup pausePopup;
	private final Popup endGamePopup;
	private final PauseController pausePopupController;
	private final EndGameController endGamePopupController;
	public HBox timerPlaceHolder;
	public Button timerButton;

	@FXML
	private Text timer;

	@FXML
	private AnchorPane rootPane;
	@FXML
	private Button pauseButton;
	@FXML
	private ImageView starImage;
	@FXML
	private Text scoreText;
	@FXML
	private Button endButton;
	private Game game;
	private Stage primaryStage;
	private MediaPlayer gameSound;
	private MediaPlayer clickSound;
	private MainLayoutController mainLayoutController;
	private int time = 3;

	public StartGameController() throws IOException {

		game = new Game(this);

		//Pause popup code
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/fxml/PausePopUp.fxml"));
		Parent popup = loader.load();
		this.pausePopupController = loader.getController();
		this.pausePopupController.setParentController(this);
		this.pausePopup = this.pausePopupController.getPausePopup();
		this.pausePopup.setHideOnEscape(false);
		this.pausePopup.getContent().add(popup);

		// End game popup code
		FXMLLoader endGameLoader = new FXMLLoader(getClass().getResource("../../resources/fxml/EndGame.fxml"));
		Parent endPopup = endGameLoader.load();
		this.endGamePopupController = endGameLoader.getController();
		this.endGamePopupController.setParentController(this);
		this.endGamePopup = this.endGamePopupController.getEndGamePopup();
		this.endGamePopup.setHideOnEscape(false);
		this.endGamePopup.getContent().add(endPopup);
		this.endGamePopup.setX(540);
		this.endGamePopup.setY(220);

	}

	public void setLoadedGame(Game game) throws IOException {
		this.game.destroyGame();
		this.game = game;
		this.game.setGameController(this);
		this.game.init();
		this.render();
//		this.getMainLayoutController().loadNewGame("GameScreen.fxml");

	}

	class PauseTimer extends TimerTask {
		public void run() {
			if(time == 3){
				timer.setText("3s");
			}
			time--;
			timer.setText(Integer.toString(time) + "s");

			if(time == 0){
				timer.setText(" ");
			}
		}
	}

	// And From your main() method or any other method

	public void pauseTimer(){
		timerPlaceHolder.toFront();
		timer.toFront();
		timer.setVisible(true);
		System.out.println(timer.getText());
		timer.setText("3s");
		time = 3;


			Timer t = new Timer();
			t.schedule(new PauseTimer(), 1000);
			t.schedule(new PauseTimer(), 2000);
			t.schedule(new PauseTimer(), 3000);

	}

	/**
	 * Used for restarting the game
	 */
	public void initialiseGame() throws IOException {
		this.hideAllPopups();
		this.playClickSound();
		this.destroyGame();
		this.mainLayoutController.deleteGame();
		this.mainLayoutController.loadNewGame("GameScreen.fxml");
		this.game = new Game(this);
		this.game.setCurrentScore(0);
		this.render();
	}

	private void hideAllPopups() {
		this.pausePopupController.closePopup();
		this.endGamePopupController.closePopup();
	}

	public void destroyGame() {
		this.game.destroyGame();
		this.rootPane.getChildren().remove(this.game.getGameRoot());
	}

	public Text getScoreText() {
		return scoreText;
	}

	public Popup getEndGamePopup() {
		return endGamePopup;
	}

	public void setScoreText(Text scoreText) {
		this.scoreText = scoreText;
	}

	public void onEndClick(MouseEvent mouseEvent) {
		this.endGamePopupController.show(this.primaryStage);
	}

	public void onPauseClick(MouseEvent mouseEvent) {
		this.pauseGame();
	}

	public void pauseGame() {
		this.pausePopupController.show(this.primaryStage);
		this.game.pauseGame();
	}

	public void endGame() {
		this.endGamePopupController.show(this.primaryStage);
		this.endGamePopupController.getScoreText().setText(Integer.toString(this.game.getCurrentScore()));
		this.game.endGame();
	}

	public Game getGame() {
		return game;
	}

	public Text getTimer() {
		return timer;
	}

	public void setGame(Game game) {
		this.game.destroyGame();
		this.game = game;
		this.render();
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public PauseController getPausePopupController() {
		return pausePopupController;
	}

	public EndGameController getEndGamePopupController() {
		return endGamePopupController;
	}


	public MainLayoutController getMainLayoutController() {
		return mainLayoutController;
	}

	public void setMainLayoutController(MainLayoutController mainLayoutController) {
		this.mainLayoutController = mainLayoutController;
	}

	public void onJumpClick(MouseEvent mouseEvent) {
		this.jump();
	}

	public void render() {
		this.rootPane.getChildren().add(this.game.getGameRoot());
	}

	public void refreshStage() {
		this.primaryStage.show();

	}

	public void simulateEnd() {
		this.endGamePopupController.show(this.primaryStage);
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		this.rootPane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.SPACE) {
				this.jump();
			} else if (event.getCode() == KeyCode.P) {
				this.pauseGame();
			}
		});
	}

	public void jump() {
		this.timer.setVisible(false);
		if (this.game.isScrollRequired()) {
			this.game.scrollScreen();
		}
		this.game.getPlayerBall().jump();
	}

	public void closeGame() {
		this.hideAllPopups();
		this.destroyGame();
		this.mainLayoutController.setGameStage(Constants.GameStage.MAINMENU);
		this.mainLayoutController.deleteGame();
		this.mainLayoutController.setSceneToMain();
		this.primaryStage.show();
	}

	public void addToSavedGames() {
		this.mainLayoutController.getMainMenu().addToSavedGames(this.game);
		try {
			this.game.serialize();
			this.mainLayoutController.serialize();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not save the game!");
		}
	}

	public void playGameSound() {
		String path = "src/resources/sounds/GameSound-1.mp3";
		Media media = new Media(new File(path).toURI().toString());
		MediaPlayer gameSound = new MediaPlayer(media);
		gameSound.play();
	}

	public void playClickSound() {
		String path = "src/resources/sounds/click.mp3";
		Media media = new Media(new File(path).toURI().toString());
		MediaPlayer clickSound = new MediaPlayer(media);
		clickSound.play();
	}
}

