package main;

import javafx.scene.paint.Color;

public class Constants {

	public static final Color[] COLOUR_PALETTE = {Color.rgb(250, 225, 0), Color.rgb(144, 13, 255), Color.rgb(255, 1, 129), Color.rgb(50, 219, 240)};
	public static final double SCREEN_MIDPOINT_X = 250;
	public static final double SCROLL_THRESHOLD_Y = 200;
	public static final double COMPASS_SCROLL_THRESHOLD_Y = 300;
	public static final double SCROLL_THRESHOLD_X = 0;
	public static final double SCREEN_MIDPOINT_Y = 350;

	public static final double SCREEN_SIZE_Y = 700;
	public static final double SCREEN_SIZE_X = 500;
	public static final double NEW_OBSTACLE_SCROLL_THRESHOLD = 200;
	public static final double TRIANGLE_SIDE_LENGTH = 180;
	public static final double CIRCLE_RADIUS = 80;
	public static final double RECTANGLE_WIDTH_LENGTH = 150;
	public static final double RECTANGLE_HEIGHT_LENGTH = 150;
	public static final double PLUS_SIDE_LENGTH = 100;
	public static final double PLUS_OFFSET = 70;
	public static final double OBSTACLE_DISTANCE = 400;
	public static final double OBSTACLE_GENERATE_START = NEW_OBSTACLE_SCROLL_THRESHOLD - OBSTACLE_DISTANCE;
	public static final double COLOR_SWITCH_START_Y = OBSTACLE_GENERATE_START - (OBSTACLE_DISTANCE / 2);
	public static final int STAR_POINTS = 5;
	public static final double PLAYER_RADIUS = 10;
	public static final double COLOUR_SWITCH_RADIUS = 15;
	public static final String DATABASE_FILENAME = "database.ser";
	public static final Color BONUS_STAR = Color.YELLOW;
	public static final Color NORMAL_STAR = Color.WHITE;
	public static final double PLAYER_START_Y = 600;
	public static final double PLAYER_START_X = SCREEN_MIDPOINT_X;
	public static final int REVIVE_AMOUNT = 50;
	public static final Color DIRECTION_COLOUR = Color.WHITE;
	public static final String path_1 = "M6 4.01C3.58 5.84 2 8.73 2 12c0 1.46.32 2.85.89 4.11L6 14.31V4.01zM11 11.42V2.05c-1.06.11-2.07.38-3 .79v10.32l3-1.74zM12 13.15l-8.11 4.68c.61.84 1.34 1.59 2.18 2.2L15 14.89l-3-1.74zM13 7.96v3.46l8.11 4.68c.42-.93.7-1.93.82-2.98L13 7.96zM8.07 21.2c1.21.51 2.53.8 3.93.8 3.34 0 6.29-1.65 8.11-4.16L17 16.04 8.07 21.2zM21.92 10.81c-.55-4.63-4.26-8.3-8.92-8.76v3.6l8.92 5.16z";
	public static final String path_2 = "M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 3.3l1.35-.95c1.82.56 3.37 1.76 4.38 3.34l-.39 1.34-1.35.46L13 6.7V5.3zm-3.35-.95L11 5.3v1.4L7.01 9.49l-1.35-.46-.39-1.34c1.01-1.57 2.56-2.77 4.38-3.34zM7.08 17.11l-1.14.1C4.73 15.81 4 13.99 4 12c0-.12.01-.23.02-.35l1-.73 1.38.48 1.46 4.34-.78 1.37zm7.42 2.48c-.79.26-1.63.41-2.5.41s-1.71-.15-2.5-.41l-.69-1.49.64-1.1h5.11l.64 1.11-.7 1.48zM14.27 15H9.73l-1.35-4.02L12 8.44l3.63 2.54L14.27 15zm3.79 2.21l-1.14-.1-.79-1.37 1.46-4.34 1.39-.47 1 .73c.01.11.02.22.02.34 0 1.99-.73 3.81-1.94 5.21z";
	public static final String path_3 = "M17.09 11h4.86c-.16-1.61-.71-3.11-1.54-4.4-1.73.83-2.99 2.45-3.32 4.4zM6.91 11c-.33-1.95-1.59-3.57-3.32-4.4-.83 1.29-1.38 2.79-1.54 4.4h4.86zM15.07 11c.32-2.59 1.88-4.79 4.06-6-1.6-1.63-3.74-2.71-6.13-2.95V11h2.07zM8.93 11H11V2.05C8.61 2.29 6.46 3.37 4.87 5c2.18 1.21 3.74 3.41 4.06 6zM15.07 13H13v8.95c2.39-.24 4.54-1.32 6.13-2.95-2.18-1.21-3.74-3.41-4.06-6zM3.59 17.4c1.72-.83 2.99-2.46 3.32-4.4H2.05c.16 1.61.71 3.11 1.54 4.4zM17.09 13c.33 1.95 1.59 3.57 3.32 4.4.83-1.29 1.38-2.79 1.54-4.4h-4.86zM8.93 13c-.32 2.59-1.88 4.79-4.06 6 1.6 1.63 3.74 2.71 6.13 2.95V13H8.93z";
	public static final String path_4 = "M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8z";
	public static final String path_5 = "M12 2C6.49 2 2 6.49 2 12s4.49 10 10 10 10-4.49 10-10S17.51 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8zm3-8c0 1.66-1.34 3-3 3s-3-1.34-3-3 1.34-3 3-3 3 1.34 3 3z";
	public static String[] pathList = {path_1,path_2,path_3,path_4,path_5,path_5};

	// MAX UID = 14 Game Element
	public enum GameStage {
		LOADING, LANDING, LOGIN, MAIN_MENU, GAME_MODE_SELECTION, SELECT_SAVED, START_GAME;
	}

	public enum GameMode {
		CLASSIC, COMPASS
	}
}
