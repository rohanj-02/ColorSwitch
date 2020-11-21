package main.logic;

public class Multiplayer extends Game {
	private Player player1;
	private Player player2;
	// Create 2 games in this class and run them in parallel
	// Can be 2 classic or 2 compass or 2 ocean associated with each player

	@Override
	public void checkCollision() {

	}

	@Override
	public void pauseGame() {

	}
	// pause button click handles

	@Override
	public void saveGame() {

	}

	public void resumeGame() {

	}

	public void startGame() {

	}
	// serializes game for that player

	// public static void serialize() throws IOException{

	// }

	// public static void deserialize() throws IOException, ClassNotFoundException{

	// }
    /*
     Student std1 = new Student("Krishna", 30);
      
    FileOutputStream fos = new FileOutputStream("e:\\student.ser");
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(std1);
    
      */
}