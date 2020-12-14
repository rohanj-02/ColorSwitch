package main.controllers;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.exceptions.SameNameException;
import main.exceptions.UserDoesNotExistException;

import java.net.URL;
import java.util.ResourceBundle;

//TODO User with same name exists comes when no db
public class LoginController extends LayoutController implements Initializable {

	@FXML
	private AnchorPane rootPane;
	@FXML
	private TextField usernameText;
	@FXML
	private Button actionButton;
	@FXML
	private Text errorText;

	public String getUsername() {
		return this.usernameTextProperty().get();
	}

	public void setUsername(String value) {
		this.usernameTextProperty().set(value);
	}

	public StringProperty usernameTextProperty() {
		return this.usernameText.textProperty();
	}

	@FXML
	public void onActionClick(MouseEvent mouseEvent) {
		this.tryLogin();
	}

	public void setButtonText(String text) {
		this.actionButton.setText(text);
	}

	public void tryLogin() {
		this.parentController.playClickSound();
		if (this.actionButton.getText().equals("Login")) {
			String name = this.usernameText.getText();
			if (name.equals("")) {
				this.errorText.setText("Username cannot be empty!");
				return;
			}
			// More error handling
			try {
				this.parentController.setCurrentPlayer(name);
				this.increaseStage();
			} catch (UserDoesNotExistException e) {
				this.errorText.setText("This user does not exist! Please login from an existing user.");
			}
		} else {
			String name = this.usernameText.getText();
			if (name.equals("")) {
				this.errorText.setText("Username cannot be empty!");
				return;
			}
			try {
				this.parentController.createPlayer(name);
				this.increaseStage();
			} catch (SameNameException e) {
				this.errorText.setText("A user with this name already exists! ");
			}
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.errorText.setText("");
		this.rootPane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				this.tryLogin();
			}
			if (event.getCode() == KeyCode.ESCAPE) {
				this.decreaseStage();
			}
		});
	}
}
