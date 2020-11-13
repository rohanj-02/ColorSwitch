package sample;

public class Plus extends Obstacle{

    private double armLength;


    public double getArmLength() {
        return armLength;
    }

    public void setArmLength(double armLength) {
        this.armLength = armLength;
    }

    @Override
    public boolean isCollision(Ball ball) {
        return false;
    }
}