package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.Constants.GameStage;

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
	private GameStage gameStage;
	private boolean isLogin;

	public MainLayoutController() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../resources/fxml/MainLayout.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		this.setGameStage(GameStage.LANDING);
		this.setLogin(false);
		landingController.setParentController(this);
		loginController.setParentController(this);
		mainMenuController.setParentController(this);
		loadGameController.setParentController(this);
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean login) {
		isLogin = login;
	}

	public GameStage getGameStage() {
		return gameStage;
	}

	public void setGameStage(GameStage gameStage) {
		this.gameStage = gameStage;
		switch (gameStage) {
			case LANDING:
				landing.setVisible(true);
				login.setVisible(false);
				mainMenu.setVisible(false);
				loadGame.setVisible(false);
				break;
			case LOGIN:
				landing.setVisible(false);
				login.setVisible(true);
				mainMenu.setVisible(false);
				loadGame.setVisible(false);
				this.loginController.setButtonText(this.isLogin() ? "Login" : "Signup");
				break;
			case MAINMENU:
				landing.setVisible(false);
				login.setVisible(false);
				mainMenu.setVisible(true);
				loadGame.setVisible(false);
				break;
			case SELECTSAVED:
				landing.setVisible(false);
				login.setVisible(false);
				mainMenu.setVisible(false);
				loadGame.setVisible(true);
				break;
		}
	}

	public void increaseGameStage() {
		boolean flag = false;
		for (GameStage iter : GameStage.values()) {
			if (flag) {
				this.setGameStage(iter);
				break;
			}
			if (iter == this.getGameStage()) {
				flag = true;
			}
		}
	}

}
