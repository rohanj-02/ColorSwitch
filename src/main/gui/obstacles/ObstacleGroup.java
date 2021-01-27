package main.gui.obstacles;

import javafx.scene.Group;
import main.gui.PlayerBall;
import main.gui.Point;

import java.util.ArrayList;

import static main.Constants.*;

public class ObstacleGroup extends Obstacle{

	private ArrayList<Obstacle> listOfObstacles;

	public ObstacleGroup(Point generatePoint, Boolean direction, int selection) {
		this.listOfObstacles = new ArrayList<>();
		this.setPosition(generatePoint);
		switch(selection){
			case 6:
				listOfObstacles.add(new CircleObstacle(generatePoint, CIRCLE_RADIUS, true));
				listOfObstacles.add(new CircleObstacle(generatePoint, CIRCLE_RADIUS + 25, false));
				break;
			case 7:
				listOfObstacles.add(new TriangleObstacle(generatePoint ,TRIANGLE_SIDE_LENGTH, true));
				listOfObstacles.add(new CircleObstacle(generatePoint, CIRCLE_RADIUS + 30 , true));
				break;
			case 8:
				listOfObstacles.add( new RectangleObstacle(generatePoint, RECTANGLE_WIDTH_LENGTH - 20, RECTANGLE_HEIGHT_LENGTH - 20, true));
				listOfObstacles.add( new RectangleObstacle(generatePoint, RECTANGLE_WIDTH_LENGTH + 50, RECTANGLE_HEIGHT_LENGTH + 50, false));
			break;
		}
		this.obstacleRoot = new Group();
		for(Obstacle obstacle: listOfObstacles){
			this.obstacleRoot.getChildren().add(obstacle.obstacleRoot);
		}
	}

	@Override
	public boolean isCollision(PlayerBall ball) {
		boolean flag = false;
		for(Obstacle obstacle: listOfObstacles){
			flag = flag | obstacle.isCollision(ball);
		}
		return flag;
	}

	@Override
	public void init() {
		for(Obstacle obstacle: listOfObstacles){
			obstacle.init();
		}
	}

	@Override
	public void render(Group root) {
		for(Obstacle obstacle: listOfObstacles){
			obstacle.render(root);
			root.getChildren().addAll(this.obstacleRoot);
		}
	}

	@Override
	public void play() {
		for(Obstacle obstacle: listOfObstacles){
			obstacle.play();
		}
	}
}
