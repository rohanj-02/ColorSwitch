package main.controllers;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController extends LayoutController {
	@FXML
	private TextField usernameText;
	@FXML
	private Button actionButton;

	public String getUsername() {
		return this.textProperty().get();
	}

	public void setUsername(String value) {
		this.textProperty().set(value);
	}

	public StringProperty textProperty() {
		return this.usernameText.textProperty();
	}

	@FXML
	public void onActionClick(MouseEvent mouseEvent) {
		this.increaseStage();
//		this.setButtonText(this.getUsername());
	}

	public void setButtonText(String text) {
		this.actionButton.setText(text);
	}
}
