package main;

import javafx.scene.paint.Color;

public class Constants {

	public static final Color[] COLOUR_PALETTE = {Color.rgb(250, 225, 0), Color.rgb(144, 13, 255), Color.rgb(255, 1, 129), Color.rgb(50, 219, 240)};

	public static final Color bonusStar = Color.YELLOW;
	public static final Color normalStar = Color.WHITE;

	public enum GameStage {
		LOADING, LANDING, LOGIN, MAINMENU, SELECTSAVED, STARTGAME
	}
}
