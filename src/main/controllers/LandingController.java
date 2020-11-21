package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class LandingController extends LayoutController {
	@FXML
	private Button loginButton;
	@FXML
	private Button signUpButton;

	public void onLoginClick(MouseEvent mouseEvent) {
		this.getParentController().setLogin(true);
		this.increaseStage();
	}

	public void onSignUpClick(MouseEvent mouseEvent) {
		this.getParentController().setLogin(false);
		this.increaseStage();
	}

	//	public LandingController() {
//		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../resources/fxml/Landing.fxml"));
////		fxmlLoader.setRoot(this);
////		fxmlLoader.setController(this);
//
//		try {
//			fxmlLoader.load();
//		} catch (IOException exception) {
//			throw new RuntimeException(exception);
//		}
//	}


}
