package sample;

public class Bar extends Obstacle{
    @Override
    public boolean isColliding(Ball ball){
        return false;
    }   
}