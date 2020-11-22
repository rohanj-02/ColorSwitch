package main.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.gui.PlayerBall;
import main.gui.Point;
import main.gui.obstacles.*;

import java.io.IOException;

public class StartGameController extends LayoutController {

	private LineObstacle[] allObstacles;
	private Group[] rootList;
	private final Stage primaryStage;
	private final Stage popUpStage;
	private double xOffset;
	private double yOffset;

	public StartGameController() throws IOException {
		primaryStage = new Stage();
		popUpStage = new Stage();
		Group root = new Group();
		Button show = new Button("Show");
		Popup popup = new Popup();
		show.setLayoutX(200);
		show.setOnAction((EventHandler<ActionEvent>) event -> popup.show(primaryStage));

		Button hide = new Button("Hide");
		hide.setOnAction((EventHandler<ActionEvent>) event -> popup.hide());


		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/fxml/PopUp.fxml"));
		PopUpController popUpController = new PopUpController();
		popup.getContent().add(loader.load());

		popup.setX(540);
		popup.setY(220);


		root.getChildren().add(show);
		root.getChildren().add(hide);

		Obstacle[] allObstacles = new Obstacle[6];

		PlayerBall ball = new PlayerBall(new Point(250, 600));
		Button button = new Button("Jump");
		button.setLayoutX(250);
		button.setLayoutY(650);
		button.setOnMouseClicked(e -> ball.jump());
		root.getChildren().add(ball.root);
		root.getChildren().add(button);
		allObstacles[0] = new LineObstacle(new Point(0, 100), 500, true);
		allObstacles[1] = new LineObstacle(new Point(0, 130), 500, false);
		CircleObstacle circleObstacle = new CircleObstacle(new Point(250,400), 100, true);
		allObstacles[2] = new PlusObstacle(new Point(200, 200), 50, true);
		allObstacles[3] = new PlusObstacle(new Point(300, 200), 50, false);

		allObstacles[4] = new RectangleObstacle(new Point(250, 400), 100, 100, false);
		allObstacles[5] = circleObstacle;
		for (Obstacle allObstacle : allObstacles) {
			allObstacle.render(root);
			allObstacle.play();
		}
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
