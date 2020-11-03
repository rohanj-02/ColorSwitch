package sample;

import java.util.ArrayList;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.io.Serializable;

public abstract class Game implements Serializable{

    private transient Player player;
    // Player Game 2 way association maybe recursion in serialization
    // When deserializing PLayer object, in savedGames() in a Game object
    // set Game.player = this to give reference of the player to Game object
    private ArrayList<Obstacle> listOfObstacle;
    private Ball ball;
    private int currentScore;

    public abstract void pauseGame();
    // pause button click handles

    public abstract void saveGame();
    // serializes game for that player

}
