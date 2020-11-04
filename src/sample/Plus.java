package sample;

public class Plus extends Obstacle{

    private float armLength;
    private float angularVelocity;

    public float getArmLength() {
        return armLength;
    }

    public void setArmLength(float armLength) {
        this.armLength = armLength;
    }

    public float getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(float angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    @Override
    public boolean isCollision(Ball ball) {
        return false;
    }
}