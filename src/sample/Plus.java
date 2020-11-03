package sample;

public class Plus extends Obstacle{
    @Override
    public boolean isColliding(Ball ball){
        return false;
    }
}