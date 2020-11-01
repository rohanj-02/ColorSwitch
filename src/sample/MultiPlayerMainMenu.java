package sample;

public class MultiPlayerMainMenu extends MainMenu {
    public Game newGame() {
        return new Classic();
    }

}
