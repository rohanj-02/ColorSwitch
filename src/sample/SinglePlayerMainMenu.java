package sample;

import java.util.ArrayList;
import java.util.List;

public class SinglePlayerMainMenu extends MainMenu {

    public Game newGame() {
        return new Classic();
    }

    public Game loadGame() {
        // Main menu screen shows a list of saved games and loads them
        // initialise player
        // will call showSavedGames -> javafx gui render-> select and give loaded game
        return new Classic();
    }

    public List<Game> showSavedGames(Player player) {
        // Returns the list of saved games of a user
        return new ArrayList<Game>();
    }

}
