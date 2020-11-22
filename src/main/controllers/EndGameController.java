package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class EndGameController extends AnchorPane {

	@FXML
	public Button closeButton;

	private Popup endGamePopup;

	public Button getCloseButton() {
		return closeButton;
	}

	public void setCloseButton(Button closeButton) {
		this.closeButton = closeButton;
	}

	public Popup getEndGamePopup() {
		return endGamePopup;
	}

	public void setEndGamePopup(Popup endGamePopup) {
		this.endGamePopup = endGamePopup;
	}

	@FXML
	public void onClose(MouseEvent mouseEvent) {
		System.out.println("Close popup");
		this.endGamePopup.hide();
	}

	public EndGameController() {
		this.endGamePopup = new Popup();
	}

	public void show(Stage primaryStage) {
		this.endGamePopup.show(primaryStage);
	}
}
