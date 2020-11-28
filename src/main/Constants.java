package main;

import javafx.scene.paint.Color;

public class Constants {

	public static final Color[] COLOUR_PALETTE = {Color.rgb(250, 225, 0), Color.rgb(144, 13, 255), Color.rgb(255, 1, 129), Color.rgb(50, 219, 240)};
	public static final double SCREEN_MIDPOINT_X = 250;
	public static final double SCROLL_THRESHOLD = 250;
	public static final double SCREEN_MIDPOINT_Y = 350;
	public static final double SCREEN_SIZE_Y = 700;
	public static final double SCREEN_SIZE_X = 500;
	public static final double NEW_OBSTACLE_SCROLL_THRESHOLD = 150;
	public static final double TRIANGLE_SIDE_LENGTH = 180;
	public static final double OBSTACLE_GENERATE_START = -50;
	public static final double CIRCLE_RADIUS = 80;
	public static final double RECTANGLE_WIDTH_LENGTH = 150;
	public static final double RECTANGLE_HEIGHT_LENGTH = 150;
	public static final double PLUS_SIDE_LENGTH = 80;
	public static final double PLUS_OFFSET = 50;

	public static final Color bonusStar = Color.YELLOW;
	public static final Color normalStar = Color.WHITE;

	public enum GameStage {
		LOADING, LANDING, LOGIN, MAINMENU, SELECTSAVED, STARTGAME
	}
}
