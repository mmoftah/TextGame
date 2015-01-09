package game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Game {
	private String characterName, characterClass;
	private int potions, food, water, weapons, people, vehicles, playerX,
			playerY;
	private static String[] commands = { "help: brings up this menu",
			"inventory: shows you all the items you have",
			"save: saves the game", "map: shows you the map",
			"move right: moves you right on the map",
			"move left: moves you left on the map",
			"move up: moves you up on the map",
			"move down: moves you down on the map" };
	
	public Game(String characterName, String classType,
			int potions, int food, int water, int weapons, int people,
			int vehicles, int playerX, int playerY) {
		this.characterName = characterName;
		this.characterClass = classType;
		this.potions = potions;
		this.food = food;
		this.water = water;
		this.weapons = weapons;
		this.people = people;
		this.vehicles = vehicles;
		this.playerX = playerX;
		this.playerY = playerY;
		
		this.saveGame();
	}
	
	public void updateSave(String characterName, String classType,
			int potions, int food, int water, int weapons, int people,
			int vehicles, int playerX, int playerY) {
		this.characterName = characterName;
		this.characterClass = classType;
		this.potions = potions;
		this.food = food;
		this.water = water;
		this.weapons = weapons;
		this.people = people;
		this.vehicles = vehicles;
		this.playerX = playerX;
		this.playerY = playerY;
		
		this.saveGame();
	}
	
	private void saveGame() {
		try { // Catch errors in I/O if necessary.
			// Open a file to write to, named SaveGame.sav.
			FileOutputStream saveFile = new FileOutputStream("SaveGame.sav");

			// Create an ObjectOutputStream to put objects into save file.
			ObjectOutputStream save = new ObjectOutputStream(saveFile);

			// Now we do the save.
			save.writeObject(characterName);
			save.writeObject(characterClass);
			save.writeObject(potions);
			save.writeObject(food);
			save.writeObject(water);
			save.writeObject(weapons);
			save.writeObject(people);
			save.writeObject(vehicles);
			save.writeObject(playerX);
			save.writeObject(playerY);
			// Close the file.
			save.close(); // This also closes saveFile.
			System.out.print("(Game Saved!) ");
		} catch (Exception exc) {
			exc.printStackTrace(); // If there was an error, print the info.
		}
	}

	public void loadGame() {
		try {
			// Open file to read from, named SaveGame.sav.
			FileInputStream saveFile = new FileInputStream("SaveGame.sav");

			// Create an ObjectInputStream to get objects from save file.
			ObjectInputStream save = new ObjectInputStream(saveFile);

			// Now we do the restore.
			// readObject() returns a generic Object, we cast those back
			// into their original class type.
			// For primitive types, use the corresponding reference class.
			characterName = (String) save.readObject();
			characterClass = (String) save.readObject();
			potions = (Integer) save.readObject();
			food = (Integer) save.readObject();
			water = (Integer) save.readObject();
			weapons = (Integer) save.readObject();
			people = (Integer) save.readObject();
			vehicles = (Integer) save.readObject();
			playerX = (Integer) save.readObject();
			playerY = (Integer) save.readObject();

			// Close the file.
			save.close(); // This also closes saveFile.
			System.out.println("Game Loaded!");
		} catch (Exception exc) {
			exc.printStackTrace(); // If there was an error, print the info.
		}
	}
	
	public static void showHelp() {
		for(int i = 0; i < commands.length; i ++)
			System.out.println(commands[i]);
	}
}
