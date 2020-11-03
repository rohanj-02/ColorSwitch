package sample;

public class Triangle extends Obstacle{
    @Override
    public boolean isColliding(Ball ball){
        return false;
    }
}