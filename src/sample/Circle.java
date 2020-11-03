package sample;

public class Circle extends Obstacle{
    @Override
    public boolean isColliding(Ball ball){
        return false;
    }
}