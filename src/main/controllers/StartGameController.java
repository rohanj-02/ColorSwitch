package main.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.gui.PlayerBall;
import main.gui.Point;
import main.gui.obstacles.*;

public class StartGameController {

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

	public void addStylesheet(String path) {
		rootPane.getStylesheets().add(path);
	}

	public StartGameController() throws IOException {
		Obstacle[] allObstacles = new Obstacle[6];
		root = new Group();
		popUpStage = new Stage();
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
		CircleObstacle circleObstacle = new CircleObstacle(new Point(250, 400), 100, true);
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
