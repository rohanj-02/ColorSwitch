package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import main.gui.Point;
import main.gui.obstacles.CircleObstacle;

public class HeadingController {
	@FXML
	private Image logoImage;
	@FXML
	private AnchorPane headingContainer;

	public void addMovingO() {
		CircleObstacle circle1 = new CircleObstacle(new Point(325, 99), 37, true);
		CircleObstacle circle2 = new CircleObstacle(new Point(190, 99), 37, true);
		Group root1 = new Group();
		circle1.render(root1);
		circle2.render(root1);
		this.headingContainer.getChildren().add(root1);
		circle1.setStrokeWidth(0);
		circle2.setStrokeWidth(0);
		circle1.play();
		circle2.play();

	}
}
