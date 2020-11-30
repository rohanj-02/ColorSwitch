package main.gui;

import javafx.scene.Group;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import main.Constants;
import main.logic.Player;

import static main.Constants.SCREEN_MIDPOINT_X;

public class Star extends GameElement implements Collidable {
	// private int[] position;
	private int amount;
	public Group starRoot;
	public SVGPath svgPath;

	public Star(Point point, int amount) {
		starRoot = new Group();
		String path = "M12 17.27l4.15 2.51c.76.46 1.69-.22 1.49-1.08l-1.1-4.72 3.67-3.18c.67-.58.31-1.68-.57-1.75l-4.83-.41-1.89-4.46c-.34-.81-1.5-.81-1.84 0L9.19 8.63l-4.83.41c-.88.07-1.24 1.17-.57 1.75l3.67 3.18-1.1 4.72c-.2.86.73 1.54 1.49 1.08l4.15-2.5z";
		svgPath = new SVGPath();
		svgPath.setContent(path);
		svgPath.setScaleX(1.5);
		svgPath.setScaleY(1.5);
		svgPath.setLayoutX(point.getX() - svgPath.getLayoutBounds().getWidth() / 2);
		svgPath.setLayoutY(point.getY() - svgPath.getLayoutBounds().getHeight() / 2);
		this.amount = amount;
		if (amount >= 10) {
			svgPath.setFill(Constants.BONUS_STAR);
		} else {
			svgPath.setFill(Constants.NORMAL_STAR);
		}
	}

	public void render(Group root) {
		this.starRoot.getChildren().add(svgPath);
		root.getChildren().addAll(this.starRoot);
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

		Shape intersect = Shape.intersect(this.svgPath, ball.getBallRoot());

		if (intersect.getBoundsInLocal().getWidth() != -1) {
			collisionDetected = true;
		}

		if (collisionDetected) {
			System.out.println("Collision Detected");
		}
		return collisionDetected;
	}

	@Override
	public void setPosition() {
		Point point = new Point(SCREEN_MIDPOINT_X, this.starRoot.getTranslateY() + this.starRoot.getLayoutY());
		this.setPosition(point);
	}

	@Override
	public void setOrientation() {
		this.setOrientation(0);
	}
}
