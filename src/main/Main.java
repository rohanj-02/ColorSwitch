package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.controllers.MainLayoutController;
import main.gui.PlayerBall;
import main.gui.Point;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Add ball object
        Group root = new Group();
		PlayerBall ball = new PlayerBall(new Point(100,200));
        Button button = new Button("Show Circle");
        button.setOnMouseClicked(e -> ball.jump());
        root.getChildren().add(ball.root);
		root.getChildren().add(button);
		primaryStage.setScene(new Scene(root, 500, 700));
		primaryStage.show();

		// MainMenu
//		MainLayoutController mainLayoutController = new MainLayoutController();
//		primaryStage.setScene(new Scene(mainLayoutController));
//		primaryStage.show();
	}
}
