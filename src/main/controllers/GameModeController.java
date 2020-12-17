package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import main.Constants;

import java.net.URL;
import java.util.ResourceBundle;

public class GameModeController  extends LayoutController implements Initializable {
	@FXML
	private VBox buttonContainer;
	@FXML
	private Button classicButton;
	@FXML
	private Button compassButton;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

	}

	public void onClassicModeSelect(MouseEvent mouseEvent) {
		this.setGameMode(Constants.GameMode.CLASSIC);
		this.parentController.setGameStage(Constants.GameStage.START_GAME);
	}

	public void onCompassModeSelect(MouseEvent mouseEvent) {
		this.setGameMode(Constants.GameMode.COMPASS);
		this.parentController.setGameStage(Constants.GameStage.START_GAME);
	}

	public void setGameMode(Constants.GameMode gameMode){
		this.parentController.setGameMode(gameMode);
	}
}
