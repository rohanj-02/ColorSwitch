package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.gui.PlayerBall;
import main.gui.Point;
import main.gui.obstacles.*;

import java.io.IOException;

public class StartGameController extends LayoutController {

	private LineObstacle[] allObstacles;
	private Group[] rootList;
	private final Stage primaryStage;
	private double xOffset;
	private double yOffset;

	public StartGameController() throws IOException {
		Obstacle[] allObstacles = new Obstacle[6];
		Group root = new Group();
		PlayerBall ball = new PlayerBall(new Point(250, 600));
		Button button = new Button("Jump");
		button.setLayoutX(250);
		button.setLayoutY(650);
		button.setOnMouseClicked(e -> ball.jump());
		root.getChildren().add(ball.root);
		root.getChildren().add(button);
		allObstacles[0] = new LineObstacle(new Point(0, 100), 500, true);
		allObstacles[1] = new LineObstacle(new Point(0, 130), 500, false);
//		root.getChildren().add(obstacleRoot);
		CircleObstacle circleObstacle = new CircleObstacle(new Point(250,400), 100, true);
		allObstacles[2] = new PlusObstacle(new Point(200, 200), 50, true);
		allObstacles[3] = new PlusObstacle(new Point(300, 200), 50, false);

		allObstacles[4] = new RectangleObstacle(new Point(250, 400), 100, 100, false);
		allObstacles[5] = circleObstacle;
		for (Obstacle allObstacle : allObstacles) {
			allObstacle.render(root);
			allObstacle.play();
		}
		primaryStage = new Stage();
		primaryStage.setScene(new Scene(root, 500, 700));
		primaryStage.show();
	}


//    @FXML
//    private Button addObstacles;
//    @FXML
//    private Button animateObstacles;

//    @FXML
//    public void onAddObstacles(MouseEvent mouseEvent) {
//
//        allObstacles = new LineObstacle[1];
//        rootList = new Group[1];
//        rootList[0] = new Group();
//        allObstacles[0] = new LineObstacle(new Point(100, 100), 500,true);
//        allObstacles[0].render(rootList[0]);
////        anchorPane.getChildren().addAll(rootList);
////        anchorPane.getChildren().add(allObstacles[0].getLineRoot());
//    }
//    public void onAnimateObstacles(MouseEvent mouseEvent){
//        for(int i = 0; i<allObstacles.length;i++){
//            allObstacles[i].play();
//        }
//    }
}
