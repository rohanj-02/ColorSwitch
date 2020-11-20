package main.gui;

public class Point {
    private double x;
    private double y;

    public double getX(){
        return this.x;
    } 

    public double getY(){
        return this.y;
    } 

    public void setX(double _x){
        this.x = _x;
    }

    public void setY(double _y){
        this.y = _y;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}