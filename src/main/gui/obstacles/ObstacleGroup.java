package main.gui.obstacles;

import main.Constants;
import main.gui.Point;

import javax.management.ObjectName;
import java.util.ArrayList;

import static main.Constants.*;

public class ObstacleGroup {

	private ArrayList<Obstacle> listOfEasyObstacle;

	private ArrayList<ArrayList<Obstacle>> listOfDifficultObstacle;
	public ObstacleGroup(Point generatePoint, Boolean direction) {
		this.listOfDifficultObstacle = new ArrayList<>();
		this.listOfEasyObstacle = new ArrayList<>();
		listOfEasyObstacle.add(new CircleObstacle(generatePoint, CIRCLE_RADIUS, direction));
		listOfEasyObstacle.add(new TriangleObstacle(generatePoint, TRIANGLE_SIDE_LENGTH, direction));
		listOfEasyObstacle.add(new RectangleObstacle(generatePoint, RECTANGLE_WIDTH_LENGTH, RECTANGLE_HEIGHT_LENGTH,
				direction));
		listOfEasyObstacle.add(new PlusObstacle(generatePoint, PLUS_SIDE_LENGTH, direction));
		listOfEasyObstacle.add(new LineObstacle(generatePoint, SCREEN_SIZE_X, direction));
		listOfDifficultObstacle.add(concentricCircle(generatePoint));
		listOfDifficultObstacle.add(triangleAndCircle(generatePoint));
		listOfDifficultObstacle.add(rectangleAndRectangle(generatePoint));

	}


	public ArrayList<Obstacle> concentricCircle(Point generatePoint){
		ArrayList<Obstacle> obstacles = new ArrayList<>();
		obstacles.add(new CircleObstacle(generatePoint, CIRCLE_RADIUS, true));
		obstacles.add(new CircleObstacle(generatePoint, CIRCLE_RADIUS + 25, false));

		return obstacles;
	}


	public ArrayList<Obstacle> triangleAndCircle(Point generatePoint){
		TriangleObstacle triangleObstacle = new TriangleObstacle( generatePoint,TRIANGLE_SIDE_LENGTH, true);
		ArrayList<Obstacle> obstacles = new ArrayList<>();
		obstacles.add(triangleObstacle);
		obstacles.add(new CircleObstacle(generatePoint, CIRCLE_RADIUS + 30 , true));

		return obstacles;
	}

	public ArrayList<Obstacle> rectangleAndRectangle(Point generatePoint){
		ArrayList<Obstacle> obstacles = new ArrayList<>();
		obstacles.add( new RectangleObstacle(new Point(SCREEN_MIDPOINT_X, 400), RECTANGLE_WIDTH_LENGTH - 20, RECTANGLE_HEIGHT_LENGTH - 20, true));
		obstacles.add( new RectangleObstacle(new Point(SCREEN_MIDPOINT_X, 400), RECTANGLE_WIDTH_LENGTH + 50, RECTANGLE_HEIGHT_LENGTH + 50, false));

		return obstacles;
	}



	public ArrayList<Obstacle> getListOfEasyObstacle() {
		return listOfEasyObstacle;
	}

	public void setListOfEasyObstacle(ArrayList<Obstacle> listOfEasyObstacle) {
		this.listOfEasyObstacle = listOfEasyObstacle;
	}

	public ArrayList<ArrayList<Obstacle>> getListOfDifficultObstacle() {
		return listOfDifficultObstacle;
	}

	public void setListOfDifficultObstacle(ArrayList<ArrayList<Obstacle>> listOfDifficultObstacle) {
		this.listOfDifficultObstacle = listOfDifficultObstacle;
	}
}
