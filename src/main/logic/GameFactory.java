package main.logic;

import main.Constants;
import main.controllers.StartGameController;

public class GameFactory {

	public Game createGame(Constants.GameMode gameMode, StartGameController gameController) {
		if(gameMode == Constants.GameMode.CLASSIC){
			return new ClassicGame(gameController);
		}
		else if(gameMode == Constants.GameMode.COMPASS){
			return new CompassGame(gameController);
		}
		else{
			return new ClassicGame(gameController);
		}
	}
}
