package sample;

import java.io.Serializable;

public abstract class Obstacle extends GameElement implements Collidable {

	static class SolidCircle extends GameElement {
		private float radius;
		private String colour;

		public float getRadius() {
			return radius;
		}

		public void setRadius(float radius) {
			this.radius = radius;
		}

		public String getColour() {
			return colour;
		}

		public void setColour(String colour) {
			this.colour = colour;
		}
	}

	//	protected float[] position;

	// public abstract static void serialize() throws IOException;

	// public abstract static void deserialize() throws IOException, ClassNotFoundException;


}