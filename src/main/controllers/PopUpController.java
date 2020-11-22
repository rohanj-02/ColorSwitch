package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class PopUpController extends AnchorPane {

	@FXML
	public Button closeButton;

	private Popup pausePopup;

	@FXML
	public void onClose(MouseEvent mouseEvent) {
		System.out.println("Close popup");
		this.pausePopup.hide();
	}

	public PopUpController() {
		this.pausePopup = new Popup();
	}

	public Popup getPausePopup() {
		return pausePopup;
	}

	public void setPausePopup(Popup pausePopup) {
		this.pausePopup = pausePopup;
	}

	public void show(Stage primaryStage) {
		this.pausePopup.show(primaryStage);
	}
}
