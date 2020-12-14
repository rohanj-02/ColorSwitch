package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import main.controllers.MainLayoutController;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		MainLayoutController mainLayoutController = new MainLayoutController(primaryStage);
		Scene mainScene = new Scene(mainLayoutController);
		primaryStage.setScene(mainScene);
		mainLayoutController.setMainScene(mainScene);
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setTitle("Color Switch");
	}
}
