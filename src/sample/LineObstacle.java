package sample;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class LineObstacle extends Obstacle {

    private Group lineRoot;
    private double width;

    private TranslateTransition[] translateTransitions;
    private Line[] lineList;

    public Group getLineRoot() {
        return lineRoot;
    }

    public LineObstacle(Point start, double sceneLength) {
        double length = sceneLength / Constants.COLOUR_PALETTE.length;
        this.setPosition(start);
        this.lineList = new Line[2 * Constants.COLOUR_PALETTE.length];
        this.lineRoot = new Group();
        int noOfSegments = Constants.COLOUR_PALETTE.length;
        this.translateTransitions = new TranslateTransition[2 * Constants.COLOUR_PALETTE.length];
        for (int i = noOfSegments - 1; i >= 0; i--) {
            this.lineList[i] = new Line();
            setCoordinateOfLine(this.lineList[i], this.getPosX() + (i - noOfSegments + 1) * length, this.getPosY(), this.getPosX() + (i - noOfSegments) * length, this.getPosY());
            this.lineList[i].setStroke(Constants.COLOUR_PALETTE[i]);
            this.lineList[i].setStrokeWidth(10.0);
        }
        for (int i = noOfSegments; i < 2 * noOfSegments; i++) {
            this.lineList[i] = new Line();
            setCoordinateOfLine(this.lineList[i], this.getPosX() + (i - noOfSegments) * length, this.getPosY(), this.getPosX() + (i - noOfSegments + 1) * length, this.getPosY());
            this.lineList[i].setStroke(Constants.COLOUR_PALETTE[i - noOfSegments]);
            this.lineList[i].setStrokeWidth(10.0);
        }

        for (int i = 0; i < 2 * noOfSegments; i++) {
            this.translateTransitions[i] = new TranslateTransition();
            this.translateTransitions[i].setByX(sceneLength);
            this.translateTransitions[i].setDuration(Duration.millis(10000));
            this.translateTransitions[i].setInterpolator(Interpolator.LINEAR);
            this.translateTransitions[i].setCycleCount(500);
            this.translateTransitions[i].setNode(this.lineList[i]);
        }

    }

    public void setCoordinateOfLine(Line line, double startX, double startY, double endX, double endY) {
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
    }

    public void render(Group root) {
        this.lineRoot.getChildren().addAll(this.lineList);
        root.getChildren().addAll(this.lineRoot);
    }

    public void play() {
        for (int i = 0; i < this.lineList.length; i++) {
            this.translateTransitions[i].play();
        }
    }


    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public boolean isCollision(Ball ball) {
        return false;
    }
}