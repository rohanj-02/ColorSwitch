package sample;

public abstract class GameElement {
    private Point position;
    
    public int getPosX(){
        return this.position.getX();
    }

    public int getPosY(){
        return this.position.getY();
    }

    public void setPosX(int _x){
        this.position.setX(_x);
    }

    public void setPosY(int _y){
        this.position.setY(_y);
    }
}