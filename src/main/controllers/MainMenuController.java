package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import main.Constants;
//TODO Change exit buttons to MainMenu, Landing page and out of the game
public class MainMenuController extends LayoutController {
	@FXML
	private Button loadGameButton;
	@FXML
	private Button newGameButton;
	@FXML
	private Button exitGameButton;

	@FXML
	public void onNewGameClick(MouseEvent mouseEvent) {
//		this.increaseStage();
		this.parentController.setGameStage(Constants.GameStage.STARTGAME);
	}

	@FXML
	public void onLoadGameClick(MouseEvent mouseEvent) {
		this.increaseStage();
	}

	@FXML
	public void onExitGameClick(MouseEvent mouseEvent) {
		this.parentController.exitGame();
	}
}
