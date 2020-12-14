package main.controllers;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class ConfirmExitController {

	public Text scoreText;
	public Text errorText;
	public Button cancelExitButton;
	public Button exitButton;
	private Popup popup;
	private boolean opened = false;
	private Stage primaryStage;

	public void cancelExit(MouseEvent mouseEvent) {
		this.closePopup();
		this.primaryStage.show();
	}

	public Popup getPopup() {
		return popup;
	}

	public void closePopup(){
		this.popup.hide();
		opened = false;
	}

	public ConfirmExitController(){
		this.popup = new Popup();
	}

	public void show() {
		this.popup.show(this.primaryStage, this.primaryStage.getX(), this.primaryStage.getY() + 100);
		opened = true;
	}

	public void exit(MouseEvent mouseEvent) {
		this.primaryStage.close();
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
}
