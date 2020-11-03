package sample;

import java.io.Serializable;
import java.io.IOException;
import java.lang.ClassNotFoundException;

public abstract class Obstacle extends GameElement implements Serializable{

	protected Star star;
//	protected int[] position;
	public abstract boolean isColliding(Ball ball); //returns true on collision with user ball

    // public abstract static void serialize() throws IOException;
    
    // public abstract static void deserialize() throws IOException, ClassNotFoundException;


}