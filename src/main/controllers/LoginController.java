package main.controllers;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends LayoutController implements Initializable {
	@FXML
	private TextField usernameText;
	@FXML
	private Button actionButton;
	@FXML
	private Text errorText;

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
		if(this.textProperty().toString().equals("Login")){
			String name = this.usernameText.getText();
			if(name.equals("")){
				this.errorText.setText("Username cannot be empty!");
				return;
			}
			this.parentController.createPlayer(name);
		}
		else{
			String name = this.usernameText.getText();
			if(name.equals("")){
				this.errorText.setText("Username cannot be empty!");
				return;
			}
			// More error handling
			this.parentController.setCurrentPlayer(name);
		}
		this.increaseStage();
//		this.setButtonText(this.getUsername());
	}

	public void setButtonText(String text) {
		this.actionButton.setText(text);
	}


	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.errorText.setText("");
	}
}
