package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.controllers.MainLayoutController;
import main.controllers.StartGameController;
import main.gui.Point;
import main.gui.obstacles.LineObstacle;
import main.gui.obstacles.Obstacle;
import main.gui.obstacles.PlusObstacle;
import main.gui.obstacles.RectangleObstacle;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Add ball object
//        Group root = new Group();
//		PlayerBall ball = new PlayerBall(new Point(100,200));
//        Button button = new Button("Show Circle");
//        button.setLayoutX(100);
//        button.setLayoutY(100);
//        button.setOnMouseClicked(e -> ball.jump());
//        root.getChildren().add(ball.root);
//		root.getChildren().add(button);
//		primaryStage.setScene(new Scene(root, 500, 700));
//		primaryStage.show();

		// MainMenu
//		MainLayoutController mainLayoutController = new MainLayoutController();
//		primaryStage.setScene(new Scene(mainLayoutController));
//		primaryStage.show();

		// Test
		StartGameController startGameController = new StartGameController();

		primaryStage.setResizable(false);
		primaryStage.setTitle("Color Switch");

	}
}
