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
		this.closePopup();
	}

	public void closePopup(){
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
		this.pausePopup.show(primaryStage, primaryStage.getX(), primaryStage.getY() + 100);
	}

	public void setParentController(StartGameController parentController) {
		this.parentController = parentController;
	}


	public void onPlayClicked(MouseEvent mouseEvent) {
		this.onClose(mouseEvent);
	}

	public void onRestartClicked(MouseEvent mouseEvent) {
		this.parentController.initialiseGame();
	}

	public void onSaveClicked(MouseEvent mouseEvent) {
		//serialization
		this.parentController.addToSavedGames();
	}

	public void onExitClicked(MouseEvent mouseEvent) {
	this.parentController.closeGame();
	}
}
