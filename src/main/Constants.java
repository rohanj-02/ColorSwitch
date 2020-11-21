package main;

import javafx.scene.paint.Color;

public class Constants {

	public static final Color[] COLOUR_PALETTE = {Color.HOTPINK, Color.SKYBLUE, Color.YELLOW, Color.PURPLE};

	public enum GameStage {
		LOADING, LANDING, LOGIN, MAINMENU, SELECTSAVED
	}
}
