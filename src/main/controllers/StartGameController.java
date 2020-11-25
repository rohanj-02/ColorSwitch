package main.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.gui.ColourSwitchBall;
import main.gui.PlayerBall;
import main.gui.Point;
import main.gui.obstacles.*;

import java.io.IOException;

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
	private Button endButton;

	private final Obstacle[] allObstacles;
	private Group[] rootList;
	private Stage primaryStage;
	private final Group root;
	private final Popup pausePopup;
	private final Popup endGamePopup;
	private final PauseController pausePopupController;
	private final EndGameController endGameController;
	private PlayerBall playerBall;

	public StartGameController() throws IOException {
		allObstacles = new Obstacle[6];
		root = new Group();

		//Pause popup code
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/fxml/PausePopUp.fxml"));
		Parent popup = loader.load();
		this.pausePopupController = loader.getController();
		this.pausePopupController.setParentController(this);
		this.pausePopup = this.pausePopupController.getPausePopup();
		this.pausePopup.getContent().add(popup);

		// End game popup code
		FXMLLoader endGameLoader = new FXMLLoader(getClass().getResource("../../resources/fxml/EndGame.fxml"));
		Parent endPopup = endGameLoader.load();
		this.endGameController = endGameLoader.getController();
		this.endGameController.setParentController(this);
		this.endGamePopup = this.endGameController.getEndGamePopup();
		this.endGamePopup.getContent().add(endPopup);
		this.endGamePopup.setX(540);
		this.endGamePopup.setY(220);

		// Game elements
		this.initialiseGame();
	}

	public void initialiseGame(){

		for(Node node : root.getChildren()){
			if(node != pauseButton){
				if(node != rootPane){
//					System.out.println(node);
					root.getChildren().remove(node);
				}
			}
		}
		playerBall = new PlayerBall(new Point(250, 600), this);
		ColourSwitchBall colourSwitchBall = new ColourSwitchBall(new Point(250, 270), 15);
		root.getChildren().add(colourSwitchBall.root);
		root.getChildren().add(playerBall.root);
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

	public void onEndClick(MouseEvent mouseEvent) {
		this.endGameController.show(this.primaryStage);
	}

	public void onPauseClick(MouseEvent mouseEvent) {
		this.pausePopupController.show(this.primaryStage);
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void onJumpClick(MouseEvent mouseEvent) {
		this.playerBall.jump();
	}

	public void render() {
		this.rootPane.getChildren().add(this.root);
	}

	public void refreshStage() {
		this.primaryStage.show();
	}

	public void simulateEnd() {
		this.endGameController.show(this.primaryStage);
	}
}

