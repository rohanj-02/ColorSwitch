package sample;

public abstract class Game {

    private Player player;

    public abstract void pauseGame();
    // pause button click handles

    public abstract void saveGame();
    // serializes game for that player

}
