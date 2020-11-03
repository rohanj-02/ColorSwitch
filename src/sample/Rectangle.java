package sample;

public class Rectangle extends Obstacle {
    @Override
    public boolean isColliding(Ball ball){
        return false;
    }
}