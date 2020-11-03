package sample;
import java.io.Serializable;
import java.io.IOException;
import java.io.ClassNotFoundException;

public class Ball extends GameElement implements Serializable{
	private String colour;
	private int jumpSize;
	// private int[] position;
	private int radius;

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public int getJumpSize () {
		return jumpSize;
	}

	public void setJumpSize ( int jumpSize){
		this.jumpSize = jumpSize;
	}

	public int getRadius () {
		return radius;
	}

	public void setRadius ( int radius){
		this.radius = radius;
	}

	/**
	 * Makes the ball jump according to the jumpSize of the ball
	 */
	public void jump(){
		this.setPosY(this.getPosY() + jumpSize);
	}

	/**
	 * Constantly decreases the height of the ball by some n amount
	 */
	public void decreaseHeight(int n){
		this.setPosY(this.getPosY() - n);
	}


    public static void serialize() throws IOException{}
    
    public static void deserialize() throws IOException, ClassNotFoundException{}

}
