package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.controllers.MainLayoutController;
import main.controllers.StartGameController;
import main.gui.Point;
import main.gui.collisionBall;
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
		MainLayoutController mainLayoutController = new MainLayoutController(primaryStage);
		primaryStage.setScene(new Scene(mainLayoutController));
		primaryStage.show();

		// Test
//		StartGameController startGameController = new StartGameController(primaryStage);
//		Parent root = FXMLLoader.load(getClass().getResource("../resources/fxml/game.fxml"));
//
//		primaryStage.setScene(new Scene(root));
//		primaryStage.show();

		Group root = new Group();
		collisionBall collisionBall = new collisionBall(new Point(250, 350), 10);
		Button button = new Button("show");

		collisionBall.render(root);
		collisionBall.play();
		root.getChildren().add(button);
//		root.getChildren().add(collisionBall.ballRoot);
		primaryStage.setScene(new Scene(root, 500, 700));
		primaryStage.show();

//		primaryStage.setResizable(false);
//		primaryStage.setTitle("Color Switch");

	}
}
