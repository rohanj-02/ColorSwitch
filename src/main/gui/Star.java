package main.gui;

import javafx.scene.Group;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import main.Constants;
import main.logic.Player;

public class Star extends GameElement implements Collidable {
	// private int[] position;
	private int amount;
	public Group root;
	public SVGPath svgPath;

	public Star(Point point, int amount) {
		root = new Group();
		String path = "M12 17.27l4.15 2.51c.76.46 1.69-.22 1.49-1.08l-1.1-4.72 3.67-3.18c.67-.58.31-1.68-.57-1.75l-4.83-.41-1.89-4.46c-.34-.81-1.5-.81-1.84 0L9.19 8.63l-4.83.41c-.88.07-1.24 1.17-.57 1.75l3.67 3.18-1.1 4.72c-.2.86.73 1.54 1.49 1.08l4.15-2.5z";
		svgPath = new SVGPath();
		svgPath.setContent(path);
		svgPath.setLayoutX(point.getX());
		svgPath.setLayoutY(point.getY());
		this.amount = amount;
		if (amount >= 10) {
			svgPath.setFill(Constants.bonusStar);
		} else {
			svgPath.setFill(Constants.normalStar);
		}
	}

	public void render(Group root) {
		this.root.getChildren().add(svgPath);
		root.getChildren().addAll(this.root);
	}


	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Returns whether the star has been collected or not
	 *
	 * @return boolean
	 */
	public boolean isCollected() {
		return false;
	}

	/**
	 * Increase the score of the game
	 */
	public void increaseScore(Player player) {
		int currentScore = player.getNumberOfStars();
		player.setNumberOfStars(currentScore + this.amount);
	}

	@Override
	public boolean isCollision(PlayerBall ball) {
		boolean collisionDetected = false;

		javafx.scene.shape.Shape intersect = Shape.intersect(this.svgPath, ball.getBallRoot());

		if (intersect.getBoundsInLocal().getWidth() != -1) {
			collisionDetected = true;
		}

		if (collisionDetected) {
			System.out.println("Collision Detected");
		}
		return collisionDetected;
	}
}
