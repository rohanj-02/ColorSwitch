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

	public void onNewGameClick(MouseEvent mouseEvent) {
		this.increaseStage();
	}

	public void onLoadGameClick(MouseEvent mouseEvent) {
		this.increaseStage();
	}

	public void onExitGameClick(MouseEvent mouseEvent) {
		this.increaseStage();
	}
}
