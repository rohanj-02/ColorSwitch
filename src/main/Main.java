package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.controllers.ConfirmExitController;
import main.controllers.MainLayoutController;

import java.io.IOException;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		MainLayoutController mainLayoutController = new MainLayoutController(primaryStage);
//		mainLayoutController.playGameSound();
		Scene mainScene = new Scene(mainLayoutController);
		primaryStage.setScene(mainScene);
		mainLayoutController.setMainScene(mainScene);

		// Confirm exit
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent windowEvent) {
//				System.out.println("Hello?");
				windowEvent.consume();
				confirmAndExit(primaryStage);
			}
		});
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setTitle("Color Switch");
	}

	public void confirmAndExit(Stage primaryStage) {
		ConfirmExitController exitController;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/fxml/ConfirmExit.fxml"));
			Parent popup = loader.load();
			exitController = loader.getController();
			exitController.setPrimaryStage(primaryStage);
			Popup exitPopup = exitController.getPopup();
			exitPopup.setHideOnEscape(false);
			exitPopup.getContent().add(popup);
			exitController.show();
		} catch (IOException e) {
			e.printStackTrace();
			primaryStage.close();
		}
	}
}
