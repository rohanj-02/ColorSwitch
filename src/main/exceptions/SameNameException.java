package main.exceptions;

public class SameNameException extends Exception{
    public SameNameException(String message){
        super(message);
    }
}