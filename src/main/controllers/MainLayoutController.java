package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainLayoutController extends AnchorPane {
	@FXML
	private AnchorPane heading;
	@FXML
	private HeadingController headingController;
	@FXML
	private AnchorPane landing;
	@FXML
	private LandingController landingController;
	@FXML
	private AnchorPane login;
	@FXML
	private LoginController loginController;
	@FXML
	private AnchorPane mainMenu;
	@FXML
	private MainMenuController mainMenuController;
	@FXML
	private AnchorPane loadGame;
	@FXML
	private LoadGameController loadGameController;
	private int stage;

	public MainLayoutController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../resources/fxml/MainLayout.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		this.stage = 0;
		loadGame.setVisible(false);
		login.setVisible(false);
		mainMenu.setVisible(false);
		landingController.setParentController(this);
		loginController.setParentController(this);
		mainMenuController.setParentController(this);
		loadGameController.setParentController(this);
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
		switch (stage) {
			case 0:
				loadGame.setVisible(false);
				login.setVisible(false);
				landing.setVisible(true);
				mainMenu.setVisible(false);
				break;
			case 1:
				loadGame.setVisible(false);
				login.setVisible(true);
				mainMenu.setVisible(false);
				landing.setVisible(false);
				break;
			case 2:
				loadGame.setVisible(false);
				login.setVisible(false);
				mainMenu.setVisible(true);
				landing.setVisible(false);
				break;
			case 3:
				loadGame.setVisible(true);
				login.setVisible(false);
				mainMenu.setVisible(false);
				landing.setVisible(false);
				break;
		}
	}

	public void increaseStage() {
		this.setStage(this.getStage() + 1);
	}

}
