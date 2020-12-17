package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class ConfirmExitController {

	private final Popup popup;
	@FXML
	public Text scoreText;
	@FXML
	private Text errorText;
	@FXML
	private Button cancelExitButton;
	@FXML
	private Button exitButton;
	private boolean opened = false;
	private Stage primaryStage;

	public ConfirmExitController() {
		this.popup = new Popup();
	}

	public void cancelExit(MouseEvent mouseEvent) {
		this.closePopup();
		this.primaryStage.show();
	}

	public Popup getPopup() {
		return popup;
	}

	public void closePopup() {
		this.popup.hide();
		opened = false;
	}

	public void show() {
		this.popup.show(this.primaryStage, this.primaryStage.getX() + 30, this.primaryStage.getY() + 150);
		opened = true;
	}

	public void exit(MouseEvent mouseEvent) {
		this.primaryStage.close();
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
}
