package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.gui.PlayerBall;
import main.gui.Point;
import main.gui.obstacles.*;

public class StartGameController{

	@FXML
	private AnchorPane rootPane;
	@FXML
	private Button pauseButton;
	@FXML
	private ImageView starImage;
	@FXML
	private Text scoreText;
	@FXML
	private Button addObstaclesButton;
	@FXML
	private Button animateObstaclesButton;
	private LineObstacle[] allObstacles;
	private Group[] rootList;
	private Stage primaryStage;
	private Group root;


	public void addStylesheet(String path){
		rootPane.getStylesheets().add(path);
	}

	public StartGameController() {
		Obstacle[] allObstacles = new Obstacle[6];
		root = new Group();
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
	}

	public void onPauseClick(MouseEvent mouseEvent) {
	}

	public void onAddObstacles(MouseEvent mouseEvent) {
		rootPane.getChildren().add(root);
	}

	public void onAnimateObstacles(MouseEvent mouseEvent) {
	}

}
