package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.exceptions.NotEnoughStarsException;

import java.io.IOException;

import static main.Constants.REVIVE_AMOUNT;

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
	private boolean opened = false;
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
		if (currentScore >= REVIVE_AMOUNT) {
			this.parentController.getGame().setCurrentScore(currentScore - REVIVE_AMOUNT);
			this.parentController.getGame().playGameAfterStar();
			this.parentController.refreshStage();
			System.out.println("enough stars");
			this.closePopup();
		} else {
			this.getErrorText().setText("Not enough Stars");
			System.out.println("not enough stars");
			try {
				throw new NotEnoughStarsException("Not enough stars");
			} catch (NotEnoughStarsException e) {
				e.printStackTrace();
			}
		}
		this.parentController.getScoreText().setText(Integer.toString(currentScore));
		this.opened = false;
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
		this.opened = true;
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

	public boolean isOpened() {
		return this.opened;
	}
}
