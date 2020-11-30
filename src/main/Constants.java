package main;

import javafx.scene.paint.Color;

public class Constants {

	public static final Color[] COLOUR_PALETTE = {Color.rgb(250, 225, 0), Color.rgb(144, 13, 255), Color.rgb(255, 1, 129), Color.rgb(50, 219, 240)};
	public static final double SCREEN_MIDPOINT_X = 250;
	public static final double SCROLL_THRESHOLD = 250;
	public static final double SCREEN_MIDPOINT_Y = 350;
	public static final double SCREEN_SIZE_Y = 700;
	public static final double SCREEN_SIZE_X = 500;
	public static final double NEW_OBSTACLE_SCROLL_THRESHOLD = 300;
	public static final double TRIANGLE_SIDE_LENGTH = 180;
	public static final double CIRCLE_RADIUS = 80;
	public static final double RECTANGLE_WIDTH_LENGTH = 150;
	public static final double RECTANGLE_HEIGHT_LENGTH = 150;
	public static final double PLUS_SIDE_LENGTH = 100;
	public static final double PLUS_OFFSET = 70;
	public static final double OBSTACLE_DISTANCE = 500;
	public static final double OBSTACLE_GENERATE_START = NEW_OBSTACLE_SCROLL_THRESHOLD - OBSTACLE_DISTANCE;
	public static final int STAR_POINTS = 5;
	public static final double COLOUR_SWITCH_RADIUS = 15;
	public static final String FILENAME = "database.ser";
	public static final Color BONUS_STAR = Color.YELLOW;
	public static final Color NORMAL_STAR = Color.WHITE;

	public enum GameStage {
		LOADING, LANDING, LOGIN, MAINMENU, SELECTSAVED, STARTGAME
	}
}
