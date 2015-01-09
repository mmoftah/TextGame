package game;

public class Player {
	private String characterName, characterClass;
	private int potions, food, water, weapons, people, vehicles, playerX,
			playerY;
	private String[] inventory;

	public Player(String newCharacterName, String newClassType) {
		characterName = newCharacterName;
		characterClass = newClassType;
		
		// Sets inventory based on classType
		switch (characterClass.toLowerCase()) {
		case "fighter": case "f":
			potions = 15;
			food = 7;
			water = 7;
			weapons = 15;
			people = 5;
			vehicles = 1;
			characterClass = "Fighter";
			break;

		case "leader": case "l":
			potions = 5;
			food = 7;
			water = 7;
			weapons = 5;
			people = 21;
			vehicles = 5;
			characterClass = "Leader";
			break;

		case "survivor": case "s":
			potions = 5;
			food = 17;
			water = 17;
			weapons = 5;
			people = 5;
			vehicles = 1;
			characterClass = "Survivor";
			break;
		}
		
		// Starts character off in the middle of the map
		playerX = 25;
		playerY = 25;
		
		inventory = new String[4];
		inventory[0] = "food (" + food + ")";
		inventory[1] = "water (" + water + ")";
		inventory[2] = "weapons (" + weapons + ")";
		inventory[3] = "potions (" + potions + ")";
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

	public void updatePotions(Integer readObject) { potions = readObject; }

	public void updateFood(Integer readObject) { food = readObject; }

	public void updateWater(Integer readObject) { water = readObject; }

	public void updateWeapons(Integer readObject) { weapons = readObject; }

	public void updatePeople(Integer readObject) { people = readObject; }

	public void updateVehicles(Integer readObject) { vehicles = readObject; }

	public void updateX(Integer readObject) { playerX = readObject; }

	public void updateY(Integer readObject) { playerY = readObject; }
	
	public String getName() { return characterName; }
	
	public String getCharacterClass() { return characterClass; }
	
	public int getPotions() { return potions; }
	
	public int getFood() { return food; }
	
	public int getWater() { return water; }
	
	public int getWeapons() {return weapons; }
	
	public int getPeople() { return people; }
	
	public int getVehicles() {return vehicles; }
	
	public int getX() { return playerX; }
	
	public int getY() { return playerY; }
	
	public void showInventory() {
		for(int i = 0; i < inventory.length; i ++)
			System.out.println(inventory[i]);
	}
	
	public String toString() {
		return "You are a " + characterClass + " with "
				+ people + " people in your party.\n"
				+ (weapons - people >= 0 ? "Everyone in your party is armed."
				: (people - weapons) + " people in your pary are not armed.")
				+ "\nYour current location is " + playerX + ", " + playerY + ".";
	}
}
