package sample;

public abstract class MainMenu {

	protected String mode;

	public void setMode(String s) {
		mode = s;
	}

	public abstract Game newGame();
	// Main menu screen starts a new game
	// initialise player

}