package game;

public class Player {
	private String characterName, characterClass;
	private int food, water, playerX, playerY;
	private Inventory playerInventory;

	public Player(String newCharacterName, String newClassType) {
		characterName = newCharacterName;
		characterClass = newClassType;
		
		// Sets inventory based on classType
		switch (characterClass.toLowerCase()) {
		case "fighter": case "f":
			food = 7;
			water = 7;
			characterClass = "Fighter";
			break;

		case "leader": case "l":
			food = 7;
			water = 7;
			characterClass = "Leader";
			break;

		case "survivor": case "s":
			food = 17;
			water = 17;
			characterClass = "Survivor";
			break;
		}
		
		playerInventory = new Inventory(characterClass);
		
		// Starts character off in the middle of the map
		playerX = 25;
		playerY = 25;
	}
	// moves the player one space down
	// Precondition: The move is valid
	public void moveDown() { playerY += 1; }
	
	// moves the player one space up
	// Precondition: The move is valid
	public void moveUp() { playerY -= 1; }
	
	// moves the player one space right
	// Precondition: The move is valid
	public void moveRight() { playerX += 1; }
	
	// moves the player one space left
	// Precondition: The move is valid
	public void moveLeft() { playerX -= 1; }
	
	//GETTERS AND SETTERS
	public void updateCharacterName(String readObject) { characterName = readObject; }

	public void updateCharacterClass(String readObject) { characterClass = readObject; }

	public void updateFood(Integer readObject) { food = readObject; }

	public void updateWater(Integer readObject) { water = readObject; }
	
	public void updateInventory(Inventory readObject) { playerInventory = readObject; }

	public void updateX(Integer readObject) { playerX = readObject; }

	public void updateY(Integer readObject) { playerY = readObject; }
	
	public String getName() { return characterName; }
	
	public String getCharacterClass() { return characterClass; }
		
	public int getFood() { return food; }
	
	public int getWater() { return water; }
	
	public Inventory getInventory() { return playerInventory; }
	
	public int getX() { return playerX; }
	
	public int getY() { return playerY; }
	
	public void showInventory() { System.out.println(playerInventory.getInventory()); }
	
	public String toString() {
		return characterName + ", you are a " + characterClass + ".";
	}
}
