package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

public class PauseController extends AnchorPane {

	@FXML
	public Button closeButton;
	private Popup pausePopup;
	private boolean opened = false;
	private StartGameController parentController;

	public PauseController() {
		this.pausePopup = new Popup();
	}

	@FXML
	public void onClose(MouseEvent mouseEvent) {
		this.closePopup();
	}

	public void closePopup() {
		this.parentController.getGame().playGame();
		this.pausePopup.hide();
		opened = false;
		this.parentController.refreshStage();
	}

	public Popup getPausePopup() {
		return pausePopup;
	}

	public void setPausePopup(Popup pausePopup) {
		this.pausePopup = pausePopup;
	}

	public void show(Stage primaryStage) {
		this.pausePopup.show(primaryStage, primaryStage.getX(), primaryStage.getY() + 100);
		opened = true;
	}

	public void setParentController(StartGameController parentController) {
		this.parentController = parentController;
	}


	public void onPlayClicked(MouseEvent mouseEvent) {
		this.onClose(mouseEvent);
		this.parentController.playClickSound();
	}

	public void onRestartClicked(MouseEvent mouseEvent) throws IOException {
		this.parentController.initialiseGame();

	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public void onSaveClicked(MouseEvent mouseEvent) {
		//serialization
		this.parentController.playClickSound();
		this.parentController.addToSavedGames();
	}

	public void onExitClicked(MouseEvent mouseEvent) {
		this.parentController.playClickSound();
		this.parentController.closeGame();
	}
}
