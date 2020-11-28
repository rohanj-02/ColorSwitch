package main.gui.obstacles;

import javafx.scene.Group;
import main.gui.PlayerBall;

import java.util.ArrayList;

public class ObstacleGroup extends Obstacle {

	public ArrayList<? extends Obstacle> listOfObstacles;


	@Override
	public void render(Group root) {

	}

	@Override
	public void play() {

	}

	@Override
	public boolean isCollision(PlayerBall ball) {
		return false;
	}
}
