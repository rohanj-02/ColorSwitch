package sample;

import java.util.ArrayList;

public class Player {
    private String name;
    private int highScore;
    private ArrayList<Game> savedGames;
    private int numberOfStars;
    private Game currentGame;

        public String getName() {
        return name;
}publ







                                             ic void setName(String name) {
    this.name = name;
}public int getHighScore() {
    return highScore;
}public void setHighScore(int highScore) {
    this.highScore = highScore;
}public ArrayList<Game> getSavedGame() {
    return savedGames;
}publ










                                                       ic void setSavedGame(ArrayList<Game> savedGame) {
    this.savedGames = savedGame;
}public int getNumberOfStars() {
    return numberOfStars;
}public void setNumberOfStars(int numberOfStars) {
    this.numberOfStars = numberOfStars;
}public Game getCurrentGame() {
    return currentGame;
}public void setCurrentGame(Game currentGame) {
    this.currentGame = currentGame;
}}
