package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class LoadGameController extends LayoutController {

	@FXML
	private Button loadGameButton;

	@FXML
	public void onLoadGameClick(MouseEvent mouseEvent) {
		this.parentController.playClickSound();
		this.increaseStage();
	}
}
