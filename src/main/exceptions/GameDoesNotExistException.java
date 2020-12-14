package main.exceptions;

public class GameDoesNotExistException extends Exception {
	public GameDoesNotExistException(String message) {
		super(message);
	}
}