package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class MainMenuController extends LayoutController {
	@FXML
	private Button loadGameButton;
	@FXML
	private Button newGameButton;
	@FXML
	private Button exitGameButton;

	@FXML
	public void onNewGameClick(MouseEvent mouseEvent) {
		this.increaseStage();
	}

	@FXML
	public void onLoadGameClick(MouseEvent mouseEvent) {
		this.increaseStage();
	}

	@FXML
	public void onExitGameClick(MouseEvent mouseEvent) {
		this.increaseStage();
	}
}
