package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class LandingController extends LayoutController {
	@FXML
	private Button loginButton;
	@FXML
	private Button signUpButton;

	@FXML
	public void onLoginClick(MouseEvent mouseEvent) {
		this.getParentController().setLogin(true);
		this.increaseStage();
	}

	@FXML
	public void onSignUpClick(MouseEvent mouseEvent) {
		this.getParentController().setLogin(false);
		this.increaseStage();
	}

}
