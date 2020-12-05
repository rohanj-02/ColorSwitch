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
	public void start(Stage primaryStage) throws Exception {
		MainLayoutController mainLayoutController = new MainLayoutController(primaryStage);
		primaryStage.setScene(new Scene(mainLayoutController));
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setTitle("Color Switch");
	}
}
