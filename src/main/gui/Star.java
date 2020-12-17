package main.gui;

import javafx.scene.Group;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import main.Constants;
import main.logic.Game;

import static main.Constants.SCREEN_MIDPOINT_X;

public class Star extends GameElement implements Collidable {

	public static final long serialVersionUID = 13;
	public transient Group starRoot;
	transient public SVGPath svgPath;
	private int amount;
	private boolean isCollected;

	public Star(Point point, int amount) {
		this.starRoot = new Group();
		this.setPosition(point);
		String path = "M12 17.27l4.15 2.51c.76.46 1.69-.22 1.49-1.08l-1.1-4.72 3.67-3.18c.67-.58.31-1.68-.57-1.75l-4.83-.41-1.89-4.46c-.34-.81-1.5-.81-1.84 0L9.19 8.63l-4.83.41c-.88.07-1.24 1.17-.57 1.75l3.67 3.18-1.1 4.72c-.2.86.73 1.54 1.49 1.08l4.15-2.5z";
		this.svgPath = new SVGPath();
		this.svgPath.setContent(path);
		this.svgPath.setScaleX(1.5);
		this.svgPath.setScaleY(1.5);
		this.svgPath.setLayoutX(point.getX() - this.svgPath.getLayoutBounds().getWidth() / 2);
		this.svgPath.setLayoutY(point.getY() - this.svgPath.getLayoutBounds().getHeight() / 2);
		this.amount = amount;
		if (amount >= 10) {
			this.svgPath.setFill(Constants.BONUS_STAR);
		} else {
			this.svgPath.setFill(Constants.NORMAL_STAR);
		}
		this.isCollected = false;
	}

	public boolean isCollected() {
		return isCollected;
	}

	public void setCollected(boolean collected) {
		isCollected = collected;
	}

	public void render(Group root) {
		if(!this.starRoot.getChildren().contains(this.svgPath)){
			this.starRoot.getChildren().add(svgPath);
			root.getChildren().addAll(this.starRoot);
		}
	}


	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}


	/**
	 * Increase the score of the game
	 */
	public void increaseScore(Game game) {
		int currentScore = game.getCurrentScore();
		game.setCurrentScore(currentScore + this.amount);
		this.isCollected = true;
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
		Point point = new Point(this.starRoot.getTranslateX() + this.starRoot.getLayoutX() + this.getPosX(), this.starRoot.getTranslateY() + this.starRoot.getLayoutY() + this.getPosY());
		this.setPosition(point);
	}

	@Override
	public void setOrientation() {
		this.setOrientation(0);
	}

	@Override
	public void init() {
		this.starRoot = new Group();
		String path = "M12 17.27l4.15 2.51c.76.46 1.69-.22 1.49-1.08l-1.1-4.72 3.67-3.18c.67-.58.31-1.68-.57-1.75l-4.83-.41-1.89-4.46c-.34-.81-1.5-.81-1.84 0L9.19 8.63l-4.83.41c-.88.07-1.24 1.17-.57 1.75l3.67 3.18-1.1 4.72c-.2.86.73 1.54 1.49 1.08l4.15-2.5z";
		this.svgPath = new SVGPath();
		this.svgPath.setContent(path);
		this.svgPath.setScaleX(1.5);
		this.svgPath.setScaleY(1.5);
		this.svgPath.setLayoutX(this.getPosX() - this.svgPath.getLayoutBounds().getWidth() / 2);
		this.svgPath.setLayoutY(this.getPosY() - this.svgPath.getLayoutBounds().getHeight() / 2);
		if (amount >= 10) {
			this.svgPath.setFill(Constants.BONUS_STAR);
		} else {
			this.svgPath.setFill(Constants.NORMAL_STAR);
		}

	}
}
