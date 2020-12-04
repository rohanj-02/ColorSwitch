package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import main.exceptions.GameDoesNotExistException;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoadGameController extends LayoutController implements Initializable {

	@FXML
	private ScrollPane scrollPane;
	@FXML
	private VBox buttonContainer;
	@FXML
	private Button loadGameButton;
	private int numButtons;
	private ArrayList<Button> loadGameButtons;

	@FXML
	public void onLoadGameClick(MouseEvent mouseEvent) {
		this.increaseStage();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.numButtons = 0;
		this.loadGameButtons = new ArrayList<>();
		this.buttonContainer.getChildren().remove(loadGameButton);
	}

	private Button createButton(String text, int index){
		Button button = new Button(text + " " + index);
		button.setPrefHeight(40);
		button.setMinHeight(40);
		button.setPrefWidth(200);
		button.setMinWidth(200);
		button.setOnMouseClicked(mouseEvent -> this.onButtonClick(index));
		button.setTextAlignment(TextAlignment.CENTER);
		return button;
	}

	public void onButtonClick(int index){
		try{
			this.parentController.loadGame(index);
		}catch(GameDoesNotExistException e){
			System.out.println("Could not load game! Creating a new game... ");
		}
	}

	public void setNumberOfGames(int n){
		this.numButtons = n;
		for(int i = 0; i < n; i++){
			Button newButton = createButton("Load Game", i+1);
			this.buttonContainer.getChildren().add(newButton);
		}
	}
}
