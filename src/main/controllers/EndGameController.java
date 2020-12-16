package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

public class EndGameController extends AnchorPane {

	public Text scoreText;
	public Text errorText;
	@FXML
	private Button closeButton;
	@FXML
	private Button exitToMainMenuButton;
	@FXML
	private Button restartGameButton;
	@FXML
	private Button reviveButton;
	private StartGameController parentController;
	private Popup endGamePopup;

	public EndGameController() {
		this.endGamePopup = new Popup();
	}

	public Button getCloseButton() {
		return closeButton;
	}

	public void setCloseButton(Button closeButton) {
		this.closeButton = closeButton;
	}

	public Popup getEndGamePopup() {
		return endGamePopup;
	}

	public void setEndGamePopup(Popup endGamePopup) {
		this.endGamePopup = endGamePopup;
	}

	@FXML
	public void onClose(MouseEvent mouseEvent) {
		int currentScore = this.parentController.getGame().getCurrentScore();
		System.out.println("on Close function called");
		if (currentScore >= -1100) {
			this.parentController.getGame().setCurrentScore(currentScore - 20);
			this.parentController.getGame().playGameAfterStar();
			this.parentController.refreshStage();
			System.out.println("enough stars");
			this.closePopup();
		} else {
			this.getErrorText().setText("Not enough Stars");
			System.out.println("not enough stars");
		}

	}

	@FXML
	public void closePopUp(MouseEvent mouseEvent){
		this.closePopup();
	}

	public void closePopup() {
		this.endGamePopup.hide();


	}

	public Text getScoreText() {
		return scoreText;
	}

	public void setScoreText(Text scoreText) {
		this.scoreText = scoreText;
	}

	public Text getErrorText() {
		return errorText;
	}

	public void setErrorText(Text errorText) {
		this.errorText = errorText;
	}

	public void show(Stage primaryStage) {
		this.endGamePopup.show(primaryStage, primaryStage.getX() + 30, primaryStage.getY() + 150);
	}

	public void setParentController(StartGameController parentController) {
		this.parentController = parentController;
	}

	public void revivePlayer(MouseEvent mouseEvent) {
		this.onClose(mouseEvent);
	}

	public void restartGame(MouseEvent mouseEvent) {
		try {
			this.parentController.initialiseGame();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void exitToMainMenu(MouseEvent mouseEvent) {
		this.parentController.playClickSound();
		this.parentController.closeGame();
	}

}
