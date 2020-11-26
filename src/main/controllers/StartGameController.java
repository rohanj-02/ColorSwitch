package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.gui.ColourSwitchBall;
import main.gui.PlayerBall;
import main.gui.Point;
import main.gui.obstacles.*;
import main.logic.Game;

import java.io.IOException;

public class StartGameController {

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
	private final Popup pausePopup;
	private final Popup endGamePopup;
	private final PauseController pausePopupController;
	private final EndGameController endGameController;

	public StartGameController() throws IOException {

		game = new Game(this);

		//Pause popup code
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/fxml/PausePopUp.fxml"));
		Parent popup = loader.load();
		this.pausePopupController = loader.getController();
		this.pausePopupController.setParentController(this);
		this.pausePopup = this.pausePopupController.getPausePopup();
		this.pausePopup.getContent().add(popup);

		// End game popup code
		FXMLLoader endGameLoader = new FXMLLoader(getClass().getResource("../../resources/fxml/EndGame.fxml"));
		Parent endPopup = endGameLoader.load();
		this.endGameController = endGameLoader.getController();
		this.endGameController.setParentController(this);
		this.endGamePopup = this.endGameController.getEndGamePopup();
		this.endGamePopup.getContent().add(endPopup);
		this.endGamePopup.setX(540);
		this.endGamePopup.setY(220);

		// Game elements
		this.initialiseGame();
	}

	public void initialiseGame(){

	}

	public void onEndClick(MouseEvent mouseEvent) {
		this.endGameController.show(this.primaryStage);
	}

	public void onPauseClick(MouseEvent mouseEvent) {
		this.pausePopupController.show(this.primaryStage);
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void onJumpClick(MouseEvent mouseEvent) {
		if(this.game.isScrollRequired()){
			this.game.scrollScreen();
		}
		this.game.getPlayerBall().jump();
	}

	public void render() {
		this.rootPane.getChildren().add(this.game.getGameRoot());
	}

	public void refreshStage() {
		this.primaryStage.show();
	}

	public void simulateEnd() {
		this.endGameController.show(this.primaryStage);
	}
}

