package game;

public class Player {
	private String characterName, characterClass;
	private int potions, food, water, weapons, people, vehicles, playerX,
			playerY;

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
			break;

		case "leader": case "l":
			potions = 5;
			food = 7;
			water = 7;
			weapons = 5;
			people = 21;
			vehicles = 5;
			break;

		case "survivor": case "s":
			potions = 5;
			food = 17;
			water = 17;
			weapons = 5;
			people = 5;
			vehicles = 1;
		}
	}
	
	public void moveUp() { playerY -= 1; }

	public void moveDown() { playerY += 1; }

	public void moveLeft() { playerX -= 1; }

	public void moveRight() { playerX += 1; }
	
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
}
