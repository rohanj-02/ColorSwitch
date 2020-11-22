package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopUpController extends AnchorPane {

	private Stage primaryStage;

	@FXML
	public Button close;

	@FXML
	public void onClose(MouseEvent mouseEvent) {

	}
}
