package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class PauseController extends AnchorPane {

	@FXML
	public Button closeButton;
	private Popup pausePopup;
	private StartGameController parentController;

	@FXML
	public void onClose(MouseEvent mouseEvent) {
		this.pausePopup.hide();
		this.parentController.refreshStage();
	}

	public PauseController() {
		this.pausePopup = new Popup();
	}

	public Popup getPausePopup() {
		return pausePopup;
	}

	public void setPausePopup(Popup pausePopup) {
		this.pausePopup = pausePopup;
	}

	public void show(Stage primaryStage) {
		this.pausePopup.show(primaryStage, primaryStage.getX(),primaryStage.getY() + 100);
	}

	public void setParentController(StartGameController parentController){
		this.parentController = parentController;
	}
}
